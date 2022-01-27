<jsp:useBean id="users" scope="request" type="java.util.List"/>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: zhaox
  Date: 2022/1/27
  Time: 23:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>JSP&Servlet</title>
</head>
<body>

<%--
    notice EL表达式
        - 主要功能：获取数据
        - 语法：${expression}  获取域中存储的key为expression的数据
            - 四大域对象
                1. page 当前页面有效
                2. request 当前请求有效
                3. session 当前会话有效
                4. application 当前应用有效
            - EL表达式获取数据，依次从四个域中寻找至找到
--%>
${users[0].password}

<%--
    notice JSTL标签
        - 用于取代JSP页面的Java代码
        - 步骤
            1. 导入Maven坐标
            2. JSP页面引入JSTL标签库
            3. 使用
--%>
<c:if test="${users[0].password == 123456}">
    密码正确
</c:if>
<br>
<c:forEach items="${users}" var="user">
    ${user.money}<br>
</c:forEach>

</body>
</html>
