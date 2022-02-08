package resolver;

import exception.MyException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/8 19:27
 */
public class MyExceptionResolver implements HandlerExceptionResolver {
    @Override
    /*
        notice
         - 参数Exception: 异常对象
         - 返回值ModelAndView:跳转到错误视图信息
     */
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ModelAndView modelAndView = new ModelAndView();

        //instanceof is a binary operator used to test if an object is of a given type.
        if (ex instanceof MyException) {
            modelAndView.addObject("info", "自定义异常");
        } else if (ex instanceof ClassNotFoundException) {
            modelAndView.addObject("info", "ClassNotFound异常");
        }
        modelAndView.setViewName("error.html");

        return null;
    }
}
