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
        <div class="w3-center">
            <c:if test="${not empty param.message}">
                <p class="error"><fmt:message key="${param.message}"/></p>
            </c:if>
            <form action="/login" <%--action="/login.html"--%> method="post"
                  style="margin-left: auto; margin-right: auto; width: 300px">
                <input class="w3-input w3-border w3-input-group-addon w3-col" style="width:300px" id="login"
                       name="login">
                <br><br>
                <input class="w3-input w3-border w3-input-group-addon w3-col" style="width:300px" id="password"
                       name="password" type="password">
                <br><br>
                <button class="w3-btn w3-teal w3-input-group-addon" style="width:300px"><fmt:message key="login.button.login"
                                                                                       bundle="${lang}"/></button>
            </form>
                <%--<form action="controller" method="post">
                  <input name="command" value="login" type="hidden">
                  <input name="login"><br>
                  <input name="password" type="password"><br>
                  <input value="Login" type="submit">
                </form>--%>
        </div>
    </div>
</u:html>
