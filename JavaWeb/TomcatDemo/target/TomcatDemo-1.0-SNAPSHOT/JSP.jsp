<%@ page import="org.apache.ibatis.session.SqlSession" %>
<%@ page import="utils.SqlSessionFactoryUtils" %>
<%@ page import="org.apache.ibatis.annotations.Mapper" %>
<%@ page import="mapper.UserMapper" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="pojo.User" %>
<%--
  Created by IntelliJ IDEA.
  User: zhaox
  Date: 2022/1/27
  Time: 21:07
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>JSP-learn</title>
    <style>
        table {
            text-align: center;
            width: auto;
            border-collapse: collapse;
        }

        th, td {
            border: 1px solid;
            padding: 8px;
        }
    </style>
</head>
<body>
<%--
    notice JSP脚本分类
        - <%...%>       内容放到_jspService()中执行
        - <%=%>...%>    内容放到out.print()作为参数
        - <%!...%>      内容放到_jspService()方法外，作为类的成员
--%>
<%!
    void show() {
        System.out.println("Have not seen you for a long time, jsp!");
    }

    String name;
%>
<%
    show();
    name = "JSP-only";
%>
<%=
"学习内容：" + name
%>
<%-------------------------------------------------------------------------------------------------------------------%>
<%
    SqlSession sqlSession = SqlSessionFactoryUtils.getSqlSessionFactory().openSession();
    UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
    List<User> users = userMapper.selectAll();
%>

<table>
    <tr>
        <th>id</th>
        <th>money</th>
        <th>password</th>
    </tr>
    <%
        for (User user : users) {
    %>
    <tr>
        <td><%=user.getId()%>
        </td>
        <td><%=user.getMoney()%>
        </td>
        <td><%=user.getPassword()%>
        </td>
    </tr>
    <%
        }
    %>
</table>

</body>
</html>
