<jsp:useBean id="user" scope="session" type="pojo.User"/>
<%@ page import="org.apache.ibatis.session.SqlSession" %>
<%@ page import="mapper.UserMapper" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="pojo.User" %>
<%@ page import="util.SqlSessionFactoryUtils" %>
<%--
  Created by IntelliJ IDEA.
  User: zhaox
  Date: 2022/1/29
  Time: 16:42
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>show</title>
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
<table>
    <tr>
        <th>id</th>
        <th>money</th>
        <th>password</th>
    </tr>
    <tr>
        <td>${user.id}</td>
        <td>${user.money}</td>
        <td>${user.password}</td>
    </tr>
</table>

</body>
</html>
