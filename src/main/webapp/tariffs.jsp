<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>

<c:choose>
    <c:when test="${not empty cookie['lang'].value}">
        <fmt:setLocale value="${cookie['lang'].value}"/>
    </c:when>
    <c:otherwise>
        <fmt:setLocale value="en"/>
    </c:otherwise>
</c:choose>

<fmt:setBundle basename="messages" var="lang"/>
<fmt:message var="title" key="tariffs.title"/>
<u:html title="${title}">
    <h2>${title}</h2>
    <c:if test="${sessionScope.currentUser.userRole == 'MAIN_ADMIN'}">
        <button onclick="location.href='/tariffs/add'">Add new tariff</button>
    </c:if>
    <table class="w3-table w3-bordered">
        <tr>
            <fmt:message var="id" key="tariffs.id"/>
            <th>${id}</th>
            <fmt:message var="name" key="tariffs.name"/>
            <th>${name}</th>
            <fmt:message var="descr" key="tariffs.description"/>
            <th>${descr}</th>
            <fmt:message var="cost" key="tariffs.cost"/>
            <th>${cost}</th>
            <fmt:message var="fr" key="tariffs.frequency"/>
            <th>${fr}</th>
            <fmt:message var="st" key="tariffs.service_type"/>
            <th>${st}</th>
        </tr>
        <c:forEach items="${requestScope.tariffs}" var="t">
            <tr>
                <td><c:out value="${t.id}"/></td>
                <td><a href="tariffs/request?id=${t.id}"><c:out value="${t.name}"/></a></td>
                <td><c:out value="${t.description}"/></td>
                <td><c:out value="${t.cost}"/></td>
                <td><c:out value="${t.frequencyOfPayment}"/></td>
                <td><c:out value="${t.service.serviceType}"/></td>
                <c:if test="${sessionScope.currentUser.userRole == 'MAIN_ADMIN'}">
                    <td>
                        <button onclick="location.href='/tariffs/update?id=${t.id}'">Change data</button>
                    </td>
                    <td>
                        <button onclick="location.href='/tariffs/delete?id=${t.id}'">Delete</button>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
    </table>
</u:html>
