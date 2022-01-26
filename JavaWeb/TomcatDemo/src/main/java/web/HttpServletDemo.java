package web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: 赵鑫
 * @Date: 2022/1/26 22:45
 */

/*
    notice urlPatterns配置规则
        - 可以匹配多个url
        - 配置规则（优先级：精确 > 目录 > 扩展名 > /* > /）
            - 精确匹配      /user/select
            - 目录匹配      /user/*
            - 扩展名匹配     *.do（不能加 / ）
            - 任意匹配
                - /       会覆盖DefaultServlet
                - /*      优先级高于/
 */
@WebServlet(urlPatterns = {"/HttpServletDemo", "/HttpServletDemo2"})
public class HttpServletDemo extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {

    }
}
