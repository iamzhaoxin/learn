package web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @Author: 赵鑫
 * @Date: 2022/1/30 11:36
 */

/*
    notice 过滤器链
        - 配置注解的Filter，优先级按照过滤器类名（字符串）的自然排序

 */
@WebFilter("/*")
public class FilterDemo2 implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 1. 放行前，对request数据进行处理
        System.out.println("demo2 handling request ");
        // 2. 通行
        filterChain.doFilter(servletRequest, servletResponse);
        // 3. 放行后，对返回的response处理
        System.out.println("demo2 handling response");
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
