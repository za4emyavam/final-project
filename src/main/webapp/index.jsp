<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@taglib tagdir="/WEB-INF/tags" prefix="u"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%--<fmt:setLocale value="ua"/>--%>
<c:choose>
    <c:when test="${not empty cookie['lang'].value}">
        <fmt:setLocale value="${cookie['lang'].value}"/>
    </c:when>
    <c:otherwise>
        <fmt:setLocale value="en"/>
    </c:otherwise>
</c:choose>

<fmt:setBundle basename="messages" var="lang"/>
<fmt:message var="title" key="main.title"/>
<u:html title="title">
    <h2>${title}</h2>
    <div class="w3-container w3-blue-grey w3-opacity w3-right-align">
        <h1>"Hello World!"</h1>
    </div>

    <div class="w3-container w3-center">
        <c:if test="${sessionScope.currentUser != null}">
            <p class="w3-text-brown">Name of user: ${sessionScope.currentUser.email}</p>
            <button class="w3-button w3-round" onclick="location.href='logout'">LOGOUT</button>
            <button class="w3-button w3-round" onclick="location.href='cabinet'">CABINET</button>
            <c:if test="${sessionScope.currentUser.userRole.name eq 'admin' ||
            sessionScope.currentUser.userRole.name eq 'main_admin'}">
                <button class="w3-button w3-round" onclick="location.href='admin'">ADMIN</button>
            </c:if>
        </c:if>
        <c:if test="${sessionScope.currentUser == null}">
            <button class="w3-button w3-round" onclick="location.href='registration.html'">REGISTER</button>
            <button class="w3-button w3-round" onclick="location.href='login'">LOGIN</button>
        </c:if>
        <button class="w3-button w3-round" onclick="location.href='tariffs'">TARIFFS</button>
    </div>
</u:html>
<%--
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
    &lt;%&ndash;<meta name="viewport" content="width=device-width, initial-scale=1">&ndash;%&gt;
    <link rel="stylesheet" href="../../w3.css">
</head>
<body class="w3-light-grey">
    <div class="w3-container w3-blue-grey w3-opacity w3-right-align">
        <h1>"Hello World!"</h1>
    </div>

    <div class="w3-container w3-center">
        <c:if test="${sessionScope.currentUser != null}">
            <p class="w3-text-brown">Name of user: ${sessionScope.currentUser.email}</p>
            <button class="w3-button w3-round" onclick="location.href='logout.html'">LOGOUT</button>
            <button class="w3-button w3-round" onclick="location.href='cabinet.html'">CABINET</button>
            <c:if test="${sessionScope.currentUser.userRole.name eq 'admin' ||
            sessionScope.currentUser.userRole.name eq 'main_admin'}">
                <button class="w3-button w3-round" onclick="location.href='admin.html'">ADMIN</button>
            </c:if>
        </c:if>
        <c:if test="${sessionScope.currentUser == null}">
            <button class="w3-button w3-round" onclick="location.href='registration.html'">REGISTER</button>
            <button class="w3-button w3-round" onclick="location.href='login.html'">LOGIN</button>
        </c:if>
        <button class="w3-button w3-round" onclick="location.href='tariffs.html'">TARIFFS</button>


        <a href="index.html">Hello Servlet</a>
    </div>
</body>
</html>--%>
