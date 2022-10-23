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
<fmt:message var="title" key="admin.requests.title"/>
<u:html title="title">
    <h2>${title}</h2>
<table>
    <tr>
        <th>id</th>
        <th>subscriber_id</th>
        <th>tariff</th>
        <th>date of change</th>
        <th>status</th>
    </tr>
    <c:forEach items="${requestScope.requests}" var="r">
        <tr onclick="location.href='/admin/requests/update?id=${r.id}'">
            <th><c:out value="${r.id}"/></th>
            <th><c:out value="${r.subscriber.id}"/></th>
            <th><c:out value="${r.tariff.name}"/></th>
            <th><fmt:formatDate pattern="dd MM yyyy" value="${r.dateOfChange}"/></th>
            <th><c:out value="${r.status.value}"/></th>
        </tr>

    </c:forEach>
</table>
</u:html>
