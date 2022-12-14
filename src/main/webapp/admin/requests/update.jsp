<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib prefix="custom" uri="https://myfp.com/jsp/tlds/mytags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : 'ua'}"
       scope="session"/>
<fmt:setLocale value="${language}"/>

<fmt:setBundle basename="messages" var="lang"/>
<fmt:message var="title" key="admin.requests.update.title"/>
<u:html title="title">
    <div class="w3-container">
        <h2>${title}</h2>
        <div style="margin-left: auto; margin-right: auto; width: 400px">
            <p><fmt:message key="admin.requests.update.requestid"/>: ${requestScope.connectionRequest.id}</p>
            <p><fmt:message
                    key="admin.requests.update.subscriberid"/>: ${requestScope.connectionRequest.subscriber.id}</p>
            <p><fmt:message key="admin.requests.update.email"/>: ${requestScope.connectionRequest.subscriber.email}</p>
            <p><fmt:message
                    key="admin.requests.update.fio"/>:
                    ${requestScope.connectionRequest.subscriber.firstname}
                    ${requestScope.connectionRequest.subscriber.middleName}
                    ${requestScope.connectionRequest.subscriber.surname}</p>
            <p><fmt:message
                    key="admin.requests.update.userstatus"/>:
                <c:choose>
                    <c:when test="${requestScope.connectionRequest.subscriber.userStatus.name == 'subscribed'}">
                        <fmt:message key="status.type.subscribed" bundle="${lang}"/>
                    </c:when>
                    <c:otherwise>
                        <fmt:message key="status.type.blocked" bundle="${lang}"/>
                    </c:otherwise>
                </c:choose>
            </p>
            <p><fmt:message
                    key="admin.requests.update.balance"/>:
                <custom:numberFormat lang="${language}"
                                     number="${requestScope.connectionRequest.subscriber.userBalance}"/></p>
            <p><fmt:message
                    key="admin.requests.update.phonenumber"/>:
                    ${requestScope.connectionRequest.subscriber.telephoneNumber}</p>
            <p><fmt:message key="admin.requests.update.regdate"/>: <fmt:formatDate
                    value="${requestScope.connectionRequest.subscriber.registrationDate}"/></p>
            <p><fmt:message key="admin.requests.update.city"/>: ${requestScope.connectionRequest.city}</p>
            <p><fmt:message key="admin.requests.update.address"/>: ${requestScope.connectionRequest.address}</p>
            <p><fmt:message key="admin.requests.update.tariff"/>:
                <c:choose>
                    <c:when test="${language eq 'en'}">
                        ${requestScope.connectionRequest.tariff.name[1]}
                    </c:when>
                    <c:when test="${language eq 'ua'}">
                        ${requestScope.connectionRequest.tariff.name[0]}
                    </c:when>
                </c:choose>
            </p>
            <p><fmt:message key="admin.requests.update.reqdate"/>: <fmt:formatDate
                    value="${requestScope.connectionRequest.dateOfChange}"/></p>
            <p><fmt:message key="admin.requests.update.reqstatus"/>:
                <c:choose>
                    <c:when test="${requestScope.connectionRequest.status.value == 'in processing'}">
                        <fmt:message key="requeststatus.type.inprocessing"/>
                    </c:when>
                    <c:when test="${requestScope.connectionRequest.status.value == 'rejected'}">
                        <fmt:message key="requeststatus.type.rejected"/>
                    </c:when>
                    <c:otherwise>
                        <fmt:message key="requeststatus.type.approved"/>
                    </c:otherwise>
                </c:choose>

            </p>
            <c:if test="${requestScope.connectionRequest.status.value == 'in processing'}">
                <form name="/admin/requests/update?${requestScope.id}" method="post">
                    <div class="w3-container w3-inline-input-group">
                        <select class="w3-select w3-border w3-col w3-input-group-addon" style="width:300px" id="status"
                                name="status">
                            <option value="approved"><fmt:message key="requeststatus.type.approved"/></option>
                            <option value="reject"><fmt:message key="requeststatus.type.rejected"/></option>
                        </select>
                        <br><br>
                        <button class="w3-btn w3-teal w3-input-group-addon" style="width:300px" type="submit">
                            <fmt:message
                                    key="admin.requests.update.button.verdict"/></button>
                    </div>
                </form>
            </c:if>
        </div>
    </div>
</u:html>