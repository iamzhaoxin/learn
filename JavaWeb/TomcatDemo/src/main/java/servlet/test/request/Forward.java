package servlet.test.request;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

/**
 * @Author: 赵鑫
 * @Date: 2022/1/27 16:04
 */

@WebServlet(name = "Forward", urlPatterns = "/Forward")
public class Forward extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
            notice 请求转发 由转发到的servlet返回response
                - URL路径不变
                - 只能转发到当前服务器的内部资源
                - 通过request的Attribute参数共享数据
                    - getAttribute("username");得到key对应的值
                    - removeAttribute("username");根据key删除键值对
                    - setAttribute("username","new_name");添加键值对
                - 转发的request的Attribute，和转发request的内层的coyoteRequest的parameters是两个不同的参数
         */
        String username = request.getParameter("username");
        System.out.println("get username: " + username);
        //添加Attribute参数
        request.setAttribute("username", "new_name");
        //转发
        request.getRequestDispatcher("/ServletParameter").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        doGet(request, response);
    }
}
