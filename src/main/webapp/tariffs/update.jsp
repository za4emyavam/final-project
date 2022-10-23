<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<fmt:message var="title" key="tariffs.update.title"/>
<u:html title="${title}">
    <h2>${title}</h2>

    <form action="/tariffs/update?id=${requestScope.id}" method="post">
        <input id="name" name="name" value="${requestScope.currentTariff.name}">
        <input id="description" name="description" value="${requestScope.currentTariff.description}">
        <input id="cost" name="cost" value="${requestScope.currentTariff.cost}">
        <input id="frequency_of_payment" name="frequency_of_payment"
               value="${requestScope.currentTariff.frequencyOfPayment}">
        <select id="service" name="service">
            <c:forEach items="${requestScope.services}" var="s">
                <option value="${s.serviceType}">${s.serviceType}</option>
            </c:forEach>
        </select>
        <select id="status" name="status">
            <option value="disabled">disabled</option>
            <option value="active">active</option>
        </select>
        <button class="add-button">Change tariff.</button>
    </form>
</u:html>
