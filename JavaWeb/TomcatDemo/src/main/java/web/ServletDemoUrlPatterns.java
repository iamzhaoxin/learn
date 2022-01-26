package web;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

/**
 * @Author: 赵鑫
 * @Date: 2022/1/26 22:14
 */

/*
    notice loadOnStartup:
        - 负整数：第一次被访问时创建servlet对象（默认）
        - 0或正整数：服务器启动时创建servlet对象，数字越小优先级越高
 */
@WebServlet(urlPatterns = "/ServletDemo", loadOnStartup = 1)
public class ServletDemoUrlPatterns implements Servlet {
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        System.out.println("Servlet hello world");
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    //内存释放 或 服务器关闭 时，Servlet对象被销毁，调用1次
    @Override
    public void destroy() {

    }
}
