package web.servlet;

import com.alibaba.fastjson.JSON;
import pojo.User;
import service.UserService;
import service.impl.UserServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/1 22:40
 */
@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    // fixme 为什么要这样写 多态？实际应用场景？
    private UserService userService = new UserServiceImpl();

    public void verifierById(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 获取参数
        int id = Integer.parseInt(request.getParameter("id"));
        // 调用service查询
        User user = userService.getUserById(id);
        String status = user != null ? "当前id存在" : "当前id不存在";
        // 转换为数据
        String jsonStr = JSON.toJSONString(status);
        // 写数据
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonStr);
    }

    public void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("或许登陆成功了");
    }
}
