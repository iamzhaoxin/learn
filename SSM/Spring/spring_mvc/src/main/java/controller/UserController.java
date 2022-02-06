package controller;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import java.util.List;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/5 13:48
 */

@Controller
@RequestMapping("/user")
public class UserController {
    private final ServletContext servletContext;

    public UserController(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    //    @RequestMapping(value = "/saveTest", method = RequestMethod.GET, params = {"accountName", "money!100"})
    @RequestMapping(value = "/saveTest")
    public String save() {
        System.out.println("Controller save running");
        return "/success.jsp";
    }

    @RequestMapping(value = "/modelAndView", method = RequestMethod.GET)
    public ModelAndView save2() {
        System.out.println("save2 running");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/success.html");
        return modelAndView;
    }

    @RequestMapping("/string")
    @ResponseBody   //不进行视图跳转,直接数据响应
    public String save3() {
        return "ok";
    }

    @RequestMapping("/object")
    @ResponseBody
    public UserDao save4() {
        ApplicationContext app = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        assert app != null;
        UserDao userDao = (UserDao) app.getBean("userDao");
        userDao.save();
        return userDao;
    }

    @RequestMapping("/paramsBasicType")
    @ResponseBody
    /*
        notice 接收参数最好用"引用类型",如果参数不存在,接收为null
            如果不用Integer用int,缺少参数时会报错:
                Optional int parameter 'age' is present but cannot be translated into a null value due to being declared as a primitive type.
     */
    public String receive1(String username, Integer age) {
        if (username != null || age != null) {
            System.out.println(username + " " + age);
            return username + " " + age;
        }
        return "没有收到任何参数";
    }


    @RequestMapping("/pojo")
    @ResponseBody
    public void receivePOJO(UserDaoImpl userDao) {
        System.out.println(userDao);
    }

    // 前端发送JSON:[{"username": "Google"},{"username": "Baidu"},{"username": "SoSo"}]
    @RequestMapping("/set")
    @ResponseBody
    public void receiveSet(@RequestBody List<UserDaoImpl> userDaoList) {
        System.out.println(userDaoList);
    }

}
