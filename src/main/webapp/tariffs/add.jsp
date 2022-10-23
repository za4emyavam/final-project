<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
<fmt:message var="title" key="tariffs.add.title"/>
<u:html title="${title}">
    <h2>${title}</h2>

    <%--<c:if test="${sessionScope.services != null}">
        <table>
            <tr>
                <th>Service id</th>
                <th>Service type</th>
            </tr>
        <c:forEach items="${services}" var="service">
            <tr><c:out value="${service.id}"/> </tr>
            <tr><c:out value="${service.type}"/> </tr>
        </c:forEach>
        </table>
    </c:if>--%>
    <form action="/tariffs/add" method="post">
        <input id="name" name="name">
        <input id="description" name="description">
        <input id="cost" name="cost">
        <input id="frequency_of_payment" name="frequency_of_payment">
        <select id="service" name="service">
            <c:forEach items="${requestScope.services}" var="s">
                <option value="${s.serviceType}">${s.serviceType}</option>
            </c:forEach>
        </select>
        <button class="add-button">Add tariff.</button>
    </form>
</u:html>
