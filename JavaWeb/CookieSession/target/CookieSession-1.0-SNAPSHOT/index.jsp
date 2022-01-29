<%@ page import="util.CookieUtils" %><%--
  Created by IntelliJ IDEA.
  User: zhaox
  Date: 2022/1/28
  Time: 22:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册登录-使用Cookie&Session</title>
    <style>
        body {
            text-align: center;
            padding-top: 10%;
        }

        a {
            width: 100px;
            text-align: right;
            display: inline-block;
        }
    </style>
</head>
<body>

<form action="${pageContext.request.contextPath}/Login" method="post">
    <div>
        <label>
            <a>输入id：</a>
            <%--调用工具类遍历cookie获取值--%>
            <input type="text" name="id" value="<%=CookieUtils.getValueByName(request.getCookies(), "id")%>"/>
        </label>
    </div>
    <div>
        <label>
            <a>输入密码：</a>
            <%--根据key获取session的值--%>
            <input type="password" name="password" value="<%
            String password=(String)request.getSession().getAttribute("password");
            if(password!=null){%><%=password%><%}%>"/>
        </label>
    </div>
    <div>
        <label>
            <input type="checkbox" name="remember" value="account" id="account"
                <%if(!CookieUtils.getValueByName(request.getCookies(), "id").equals("")){%> checked="checked" <%}%>
            >
            记住账号
        </label>
        <label>
            <input type="checkbox" name="remember" value="password" id="password"
                   <%if(request.getSession().getAttribute("password")!=null){%>checked="checked"<%}%>
            >
            记住密码
        </label>
        <input type="hidden" name="remember" value="notNull">
    </div>
    <br>
    <button type="submit">登陆</button>
</form>

<script>
    let account = document.getElementById("account")
    let password = document.getElementById("password")
    account.onclick = function () {
        if (account.checked === false) {
            password.checked = false;
        }
    }
    password.onclick = function () {
        if (password.checked === true) {
            account.checked = true;
        }
    }
</script>

</body>
</html>
