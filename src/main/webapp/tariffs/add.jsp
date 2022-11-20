<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : 'ua'}" scope="session" />
<fmt:setLocale value="${language}"/>

<fmt:setBundle basename="messages" var="lang"/>
<fmt:message var="title" key="tariffs.add.title"/>
<u:html title="${title}">
    <h2>${title}</h2>
    <form action="/tariffs/add" method="post">
        <div class="w3-inline-input-group">
            <input class="w3-input w3-border w3-input-group-addon w3-col" style="width: 150px" id="name" name="name"
                   placeholder="Name">
            <input class="w3-input w3-border w3-input-group-addon w3-col" style="width: 300px" id="description"
                   name="description" placeholder="Description">
            <input class="w3-input w3-border w3-input-group-addon w3-col" style="width: 150px" id="cost" name="cost"
                   placeholder="Cost">
            <input class="w3-input w3-border w3-input-group-addon w3-col" style="width: 150px" id="frequency_of_payment"
                   name="frequency_of_payment" placeholder="Frequency">
            <select class="w3-select w3-border w3-col w3-input-group-addon" style="width:150px" id="service"
                    name="service">
                <c:forEach items="${requestScope.services}" var="s">
                    <option value="${s.serviceType}">${s.serviceType}</option>
                </c:forEach>
            </select>
            <button class="w3-btn w3-teal w3-input-group-addon" style="width:150px" type="submit">Add tariff.</button>
        </div>
    </form>
</u:html>
