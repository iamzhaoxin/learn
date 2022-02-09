package interceptor;

import dao.UserDao;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/8 18:35
 */
public class MyInterceptor implements HandlerInterceptor {
    @Override
    /* 在目标方法之前执行*/
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        UserDao userDao = (UserDao) session.getAttribute("user");
        if (userDao != null) {
            System.out.println("已登录");
        } else {
            System.out.println("未登录");
        }
        /* 如果return false,不放行*/
        return true;
    }

    @Override
    /* 在目标方法之后,试图返回之前执行*/
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    /*整个流程执行完成后执行*/
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
