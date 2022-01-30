package web.servlet;

import com.alibaba.fastjson.JSON;
import pojo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * @Author: 赵鑫
 * @Date: 2022/1/30 15:45
 */

@WebServlet(name = "AxiosServlet", urlPatterns = "/AxiosServlet")
public class AxiosServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //从请求体中获取JSON数据
        BufferedReader bufferedReader = request.getReader();
        String params = bufferedReader.readLine();
        System.out.println(params);
        //notice 将JSON字符串转为Java对象
        User user = JSON.parseObject(params, User.class);
        System.out.println(user);

        // notice 转换为JSON数据
        String json = JSON.toJSONString(user);

        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        doGet(request, response);
    }
}
