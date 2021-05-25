<%--
  Created by IntelliJ IDEA.
  User: Vadzim Vincho
  Date: 20.05.2021
  Time: 1:57
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Log in with your account</title>
</head>

<body>
<sec:authorize access="isAuthenticated()">
    <% response.sendRedirect("/"); %>
</sec:authorize>
<div>
    <form:form method="POST" modelAttribute="authDto">
        <h2>Вход в систему</h2>

            <div>
                <form:input type="text" path="login" placeholder="Login"
                            autofocus="true"></form:input>
                <form:errors path="login"></form:errors>
                    ${loginError}
            </div>
            <div>
                <form:input type="password" path="password" placeholder="Password"></form:input>
            </div>
            <button type="submit">Log In</button>
            <h4><a href="/register-user">Registration</a></h4>

    </form:form>
</div>
</body>
</html>