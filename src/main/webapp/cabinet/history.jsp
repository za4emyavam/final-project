<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
<fmt:message var="title" key="cabinet.history.title"/>
<u:html title="${title}">
    <h2>${title}</h2>
    <table>
        <tr>
            <th>id</th>
            <th>type</th>
            <th>amount</th>
            <th>date</th>
        </tr>
        <c:forEach items="${requestScope.transactions}" var="t">
            <tr>
                <th><c:out value="${t.id}"/></th>
                <th><c:out value="${t.type.value}"/></th>
                <th><c:out value="${t.transactionAmount}"/></th>
                <th><c:out value="${t.transactionDate.toString}"/></th>
            </tr>

        </c:forEach>
    </table>
</u:html>
