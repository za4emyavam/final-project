<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:choose>
    <c:when test="${not empty cookie['lang'].value}">
        <fmt:setLocale value="${cookie['lang'].value}"/>
    </c:when>
    <c:otherwise>
        <fmt:setLocale value="en"/>
    </c:otherwise>
</c:choose>

<fmt:setBundle basename="messages" var="lang"/>
<fmt:message var="title" key="admin.requests.update.title"/>
<u:html title="title">
    <h2>${title}</h2>
    <p>connectionRequest.id: ${requestScope.connectionRequest.id}</p>
    <p>User id: ${requestScope.connectionRequest.subscriber.id}</p>
    <p>Email: ${requestScope.connectionRequest.subscriber.email}</p>
    <p>User
        FIO: ${requestScope.connectionRequest.subscriber.firstname} ${requestScope.connectionRequest.subscriber.middleName} ${requestScope.connectionRequest.subscriber.surname}</p>
    <p>User status: ${requestScope.connectionRequest.subscriber.userStatus.name}</p>
    <p>User balance: ${requestScope.connectionRequest.subscriber.userBalance}</p>
    <p>User telephone number: ${requestScope.connectionRequest.subscriber.telephoneNumber}</p>
    <p>User registration date: <fmt:formatDate
            value="${requestScope.connectionRequest.subscriber.registrationDate}"/></p>
    <p>City: ${requestScope.connectionRequest.city}</p>
    <p>Address: ${requestScope.connectionRequest.address}</p>
    <p>Tariff ${requestScope.connectionRequest.tariff.name}</p>
    <p>Date of request change: <fmt:formatDate value="${requestScope.connectionRequest.dateOfChange}"/></p>
    <p>Request status: ${requestScope.connectionRequest.status.value}</p>
    <form name="/admin/requests/update?${requestScope.id}" method="post">
        <div class="w3-container w3-inline-input-group">
            <select class="w3-select w3-border w3-col w3-input-group-addon" style="width:150px" id="status" name="status">
                <option value="approved">approved</option>
                <option value="reject">reject</option>
            </select>
            <button class="w3-btn w3-teal w3-input-group-addon" type="submit">verdict</button>
        </div>
    </form><%--
    <button onclick="location.href='/admin/requests/update?id=${requestScope.id}&status=approved'">APPROVE</button>
    <button onclick="location.href='/admin/requests/update?id=${requestScope.id}&status=reject'">REJECT</button>--%>
</u:html>