package web.servlet;


import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import service.UserService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/5 9:57
 */

@WebServlet(name = "springWeb", urlPatterns = "/springWeb")
public class SpringWeb extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        //获取ServletContext域
        ServletContext servletContext = this.getServletContext();
        //使用spring-web中集成的工具获得上下文对象
        ApplicationContext app = WebApplicationContextUtils.getWebApplicationContext(servletContext);

        assert app != null;
        UserService userService = (UserService) app.getBean("userService");
        userService.serviceSave();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        doGet(request, response);
    }
}
