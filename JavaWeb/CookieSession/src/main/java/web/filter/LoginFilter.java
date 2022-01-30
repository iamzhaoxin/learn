package web.filter;

import jdk.jfr.Name;
import pojo.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Author: 赵鑫
 * @Date: 2022/1/30 11:36
 */

/**
 * 原理：登陆成功后，将user对象放入session中
 */
// notice 为避免干扰其他应用，以后还是要把路径 在根目录加上应用名比较好
@WebFilter(urlPatterns = "/*", filterName = "LoginFilter")
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 1. 放行前，对request数据进行处理
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        // notice 判断访问资源路径是否和登录注册有关
        String[] urls = {"/login.jsp", "/imgs/", "/css", "/Login"};
        //获取当前访问的资源路径
        String url = request.getRequestURI().toString();
        // 循环判断
        for (String u : urls) {
            if (url.contains(u)) {
                //找到了，放行
                filterChain.doFilter(servletRequest, servletResponse);
                //notice 用return跳过后面的代码（break只能跳出循环，不能跳过后面所有代码）
                return;
            }
        }

        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");
        if (user != null) {
            // 已经登陆，通行
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            //未登录
            request.setAttribute("login_msg", "尚未登录");
            request.getRequestDispatcher("/login.jsp").forward(request, servletResponse);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
