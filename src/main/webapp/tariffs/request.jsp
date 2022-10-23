<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<c:choose>
    <c:when test="${not empty cookie['lang'].value}">
        <fmt:setLocale value="${cookie['lang'].value}"/>
    </c:when>
    <c:otherwise>
        <fmt:setLocale value="en"/>
    </c:otherwise>
</c:choose>

<fmt:setBundle basename="messages" var="lang"/>
<fmt:message var="title" key="tariffs.request.title"/>
<u:html title="${title}">
    <h2>${title}</h2>
    <form action="/tariffs/request?id=${requestScope.id}" method="post">
        <input id="city" name="city">
        <input id="address" name="address">
        <button class="add-button">Request connect tariff</button>
    </form>
</u:html>
