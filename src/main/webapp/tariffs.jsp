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
        <button class="w3-btn w3-teal" onclick="location.href='/tariffs/add'">Add new tariff</button>
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
                        <button class="w3-btn w3-teal" onclick="location.href='/tariffs/update?id=${t.id}'">Change data</button>
                    </td>
                    <td>
                        <button class="w3-btn w3-teal" onclick="location.href='/tariffs/delete?id=${t.id}'">Delete</button>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
    </table>
    <div class="w3-bar">
        <c:forEach begin="1" end="${noOfPages}" var="i">
            <%--<c:when test="${currentPage==i}">
                <a href="/tariffs?page=${i}" class="w3-button">${i}</a>
            </c:when>
            <c:otherwise>
                <a href="/tariffs?page=${i}" class="w3-button">${i}</a>
            </c:otherwise>--%>
            <a href="/tariffs?page=${i}" class="w3-button">${i}</a>
        </c:forEach>
        <%--<a href="/tariffs?page=" class="w3-button">&laquo;</a>
        <a href="#" class="w3-button">1</a>
        <a href="#" class="w3-button">2</a>
        <a href="#" class="w3-button">3</a>
        <a href="#" class="w3-button">4</a>
        <a href="#" class="w3-button">5</a>
        <a href="#" class="w3-button">&raquo;</a>--%>
    </div>
</u:html>
