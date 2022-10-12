<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
    <h1><%= "Hello World!" %></h1>
    <c:if test="${sessionScope.currentUser != null}">
        <p>Name of user: ${sessionScope.currentUser.login}</p>
        <button onclick="location.href='logout.html'">LOGOUT</button>
    </c:if>
    <c:if test="${sessionScope.currentUser == null}">
        <button onclick="location.href='registration.html'">REGISTR</button>
        <button onclick="location.href='login.html'">LOGIN</button>
    </c:if>
    <button onclick="location.href='tariffs.html'">TARIFFS</button>


    <a href="index.html">Hello Servlet</a>
</body>
</html>