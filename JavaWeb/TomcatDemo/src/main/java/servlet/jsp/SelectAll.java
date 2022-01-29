package servlet.jsp;

import mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;
import pojo.User;
import utils.SqlSessionFactoryUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

/**
 * @Author: 赵鑫
 * @Date: 2022/1/27 23:22
 */

@WebServlet(name = "SelectAll", urlPatterns = "/SelectAll")
public class SelectAll extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SqlSession sqlSession = SqlSessionFactoryUtils.getSqlSessionFactory().openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = userMapper.selectAll();
        request.setAttribute("users", users);
        request.getRequestDispatcher("/SelectAll.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        doGet(request, response);
    }
}
