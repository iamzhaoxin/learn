package servlet.test.response;

import org.apache.commons.io.IOUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Writer;

/**
 * @Author: 赵鑫
 * @Date: 2022/1/27 17:03
 */

@WebServlet(name = "RedirectCopy", urlPatterns = "/RedirectCopy")
public class RedirectCopy extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("servlet copy");
        /*
            notice 字符输出流
                - 流不需要关闭，随着响应结束，response销毁，由服务器关闭
                - 要先设置编码，再获取输出流
         */
        response.setContentType("text/html;charset=utf-8");
        Writer writer = response.getWriter();
        writer.write("<h1>嗨</h1>");


        /*
            notice 字节输出流
                - 使用IOUtils工具类简化代码
                    - IOUtils.copy(输入流，输出流)
         */
        FileInputStream fileInputStream = new FileInputStream("D:\\OneDrive\\图片\\本机照片\\壁纸-天空.jpg");
//        byte[] bytes = fileInputStream.readAllBytes();
        ServletOutputStream outputStream = response.getOutputStream();
//        outputStream.write(bytes);
        IOUtils.copy(fileInputStream, outputStream);

        fileInputStream.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        doGet(request, response);
    }
}
