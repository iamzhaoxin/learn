package servlet.test.request;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

/**
 * @Author: 赵鑫
 * @Date: 2022/1/27 9:07
 */

//notice 可以设置IDEA的模板文件，直接创建Servlet
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
    notice loadOnStartup:
        - 负整数：第一次被访问时创建servlet对象（默认）
        - 0或正整数：服务器启动时创建servlet对象，数字越小优先级越高
 */
@WebServlet(name = "ServletParameter", urlPatterns = {"/ServletParameter", "/ServletParameter2"}, loadOnStartup = 1)
public class ServletParameter extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        /*
            notice 获得参数
                - POST方法
                    - 底层是用getReader()从请求体中获得字符流
                    - request.getParameter("username") 需要提前用 request.setCharacterEncoding("UTF-8")设置接收字符流的编码格式
                - GET方法
                    - 底层用request.getQueryString()从URL中（POST无法使用）获得参数（URL编码格式,需要解码）
                        - Tomcat8之前，默认用ISO_8859_1解码URL，需要转成字节再转回UTF-8
                            String username = request.getParameter("username");
                            byte[] bytes = username.getBytes(StandardCharsets.ISO_8859_1);
                            username = new String(bytes, StandardCharsets.UTF_8);
                        - URL编码：将字符串按照编码方式转为二进制，每个字节转为2个16进制数并在前面加上 %
                        - 解码方法：URLDecoder.decode(str, StandardCharsets.UTF_8)
                    - request.getParameter("username") 直接得到字符串
         */

        String username1 = request.getParameter("username");
        String username2 = (String) request.getAttribute("username");
        System.out.println(username1 + "，new attribute username:" + username2);
        //删除Attribute参数
        request.removeAttribute("username");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        System.out.println("post method");
        doGet(request, response);
    }
}
