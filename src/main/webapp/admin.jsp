<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:choose>
    <c:when test="${not empty cookie['lang'].value}">
        <fmt:setLocale value="${cookie['lang'].value}"/>
    </c:when>
    <c:otherwise>
        <fmt:setLocale value="en"/>
    </c:otherwise>
</c:choose>

<fmt:setBundle basename="messages" var="lang"/>
<fmt:message var="title" key="admin.title"/>
<u:html title="title">
    <h2>${title}</h2>
    <button onclick="location.href='/admin/requests'">CHECK REQUESTS</button>
    <form action="/admin/check_payment" method="post">
        <button type="submit">CHECK PAYMENT</button>
    </form>

</u:html>
