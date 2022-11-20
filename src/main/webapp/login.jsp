<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : 'ua'}"
       scope="session"/>
<fmt:setLocale value="${language}"/>

<fmt:setBundle basename="messages" var="lang"/>
<fmt:message var="title" key="login.title"/>
<u:html title="${title}">
    <div class="w3-container">
        <h2>${title}</h2>
        <c:if test="${not empty param.message}">
            <p class="error"><fmt:message key="${param.message}"/></p>
        </c:if>
        <form action="/login" <%--action="/login.html"--%> method="post">
            <input id="login" name="login">
            <input id="password" name="password" type="password">
            <button class="w3-btn w3-hover-light-blue w3-round-large"><fmt:message key="login.button.login"
                                                                                   bundle="${lang}"/></button>
        </form>
            <%--<form action="controller" method="post">
              <input name="command" value="login" type="hidden">
              <input name="login"><br>
              <input name="password" type="password"><br>
              <input value="Login" type="submit">
            </form>--%>
    </div>
</u:html>
