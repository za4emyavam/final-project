<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
        <div class="w3-inline-input-group">
            <input class="w3-input w3-border w3-input-group-addon w3-col" style="width: 150px" id="name"
                   name="name"
                   value="${requestScope.currentTariff.name}">
            <input class="w3-input w3-border w3-input-group-addon w3-col" style="width: 300px" id="description"
                   name="description"
                   value="${requestScope.currentTariff.description}">
            <input class="w3-input w3-border w3-input-group-addon w3-col" style="width: 150px" id="cost"
                   name="cost"
                   value="${requestScope.currentTariff.cost}">
            <input class="w3-input w3-border w3-input-group-addon w3-col" style="width: 150px"
                   id="frequency_of_payment" name="frequency_of_payment"
                   value="${requestScope.currentTariff.frequencyOfPayment}">
            <select class="w3-select w3-border w3-col w3-input-group-addon" style="width:150px" id="service"
                    name="service">
                <option value="" disabled selected>Was: ${requestScope.currentTariff.service.serviceType}</option>
                <c:forEach items="${requestScope.services}" var="s">
                    <option value="${s.serviceType}">${s.serviceType}</option>
                </c:forEach>
            </select>
                <%--<select class="w3-select w3-border w3-input-group-addon" id="status" name="status">
                    <option value="" disabled selected>Was: ${requestScope.currentTariff.tariffStatus.name} </option>
                    <option value="disabled">disabled</option>
                    <option value="active">active</option>
                </select>--%>
            <button class="w3-btn w3-teal w3-input-group-addon" style="width:150px" type="submit">Change tariff.</button>
        </div>
    </form>
</u:html>
