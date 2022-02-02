package web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/1 22:39
 */
public class BaseServlet extends HttpServlet {
    /*重写service方法，根据请求的最后一段路径进行转发*/
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 获取请求路径(URI:段路径)
        String uri = req.getRequestURI();   //  /OptimizeServlet/user/login
        // 2. 获取最后一段路径(方法名)
        int index = uri.lastIndexOf('/');
        String methodName = uri.substring(index + 1);

        // 3. 执行方法
        // 3.1 获取UserServlet字节码对象
        // 谁调用我‘this所在方法’，我‘this’代表谁
        // 由UserServlet继承BaseServlet并调用重写后的service
        Class<? extends BaseServlet> cls = this.getClass();

        // 3.2 获取方法 Method对象
        try {
            Method method = cls.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            // 3.3 执行方法
            method.invoke(this, req, resp);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
