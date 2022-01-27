package servlet.test.response;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

/**
 * @Author: 赵鑫
 * @Date: 2022/1/27 17:03
 */

@WebServlet(name = "Redirect", urlPatterns = "/Redirect")
public class Redirect extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("first servlet");
        /*
        重定向
         1.设置响应状态码302
        response.setStatus(302);
         2.设置响应头
        response.setHeader("location", "/RedirectCopy");
         */
        /*
            notice 简化重定向
                - 路径问题：
                    - 浏览器使用路径（重定向）：需要加上虚拟目录（项目访问路径）
                        - 动态获取虚拟目录：getContextPath()
                    - 服务端使用（转发）：不需要加虚拟目录
         */
        String contextPath = request.getContextPath();
        response.sendRedirect(contextPath + "/RedirectCopy");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        doGet(request, response);
    }
}
