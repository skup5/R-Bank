<%--
  Created by IntelliJ IDEA.
  User: Jakub Danek
  Date: 26.11.15
  Time: 16:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>

<c:if test="${not empty requestScope.err}">
     Error: ${requestScope.err}
</c:if>

<h1>Registration</h1>
<form action="register" method="post">
    <sec:csrfInput />
    <sec:csrfMetaTags/>
    <label for="username">Username:&nbsp;</label>
    <input type="text" id="username" name="username">
    <label for="pwd">Password:&nbsp;</label>
    <input type="password" id="pwd" name="password">
    <label for="confirmpwd">Confirm Password:&nbsp;</label>
    <input type="password" id="confirmpwd" name="confirmPwd">
    <input type="submit" value="Register">
</form>

</body>
</html>
