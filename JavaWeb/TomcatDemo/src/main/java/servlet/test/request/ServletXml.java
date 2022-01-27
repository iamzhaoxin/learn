package servlet.test.request;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: 赵鑫
 * @Date: 2022/1/26 23:09
 */

/*
    notice 在web.xml中配置访问路径(不推荐)
 */
public class ServletXml extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
    }
}
