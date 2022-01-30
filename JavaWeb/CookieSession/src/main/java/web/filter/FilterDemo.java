package web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @Author: 赵鑫
 * @Date: 2022/1/30 11:36
 */

/*
    notice 拦截路径配置
        - 拦截具体  /login.jsp
        - 目录拦截  /user/*
        - 后缀名拦截 *.jsp
        - 拦截所有  /*
 */
@WebFilter(urlPatterns = "/*", filterName = "FilterDemo")
public class FilterDemo implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 1. 放行前，对request数据进行处理
        System.out.println("demo handling request ");
        // 2. 通行
        filterChain.doFilter(servletRequest, servletResponse);
        // 3. 放行后，对返回的response处理
        System.out.println("demo handling response");
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
