<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib prefix="custom" uri="https://myfp.com/jsp/tlds/mytags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : 'ua'}"
       scope="session"/>
<fmt:setLocale value="${language}"/>

<fmt:setBundle basename="messages" var="lang"/>
<fmt:message var="title" key="cabinet.title"/>
<u:html title="${title}">

    <div class="w3-container">
        <h2>${title}</h2>
        <p class="w3-text-brown"><fmt:message key="cabinet.hello"
                                              bundle="${lang}"/> ${sessionScope.currentUser.firstname}!</p>
        <%--<p class="w3-text-brown"><fmt:message key="cabinet.bank"
                                              bundle="${lang}"/> ${sessionScope.currentUser.userBalance}</p>--%>
        <p class="w3-text-brown"><fmt:message key="cabinet.bank"
                                              bundle="${lang}"/>
            <custom:numberFormat lang="${language}" number="${sessionScope.currentUser.userBalance}"/> </p>
        <p class="w3-text-brown"><fmt:message key="cabinet.status"
                                              bundle="${lang}"/>
            <c:choose>
                <c:when test="${sessionScope.currentUser.userStatus.name == 'subscribed'}">
                    <fmt:message key="status.type.subscribed" bundle="${lang}"/>
                </c:when>
                <c:otherwise>
                    <fmt:message key="status.type.blocked" bundle="${lang}"/>
                </c:otherwise>
            </c:choose>
        </p>
        <button class="w3-button w3-round" onclick="location.href='/cabinet/replenish'"><fmt:message
                key="cabinet.breplenishbalance" bundle="${lang}"/></button>
            <%--<button class="w3-button w3-round" onclick="location.href='/cabinet/change_password'">TODO: CHANGE PASSWORD</button>--%>
        <button class="w3-button w3-round" onclick="location.href='/cabinet/history'"><fmt:message
                key="cabinet.bcheckbalancehistory" bundle="${lang}"/></button>
        <c:if test="${requestScope.userTariffs != null}">
            <p><fmt:message key="cabinet.tariffs" bundle="${lang}"/></p>
            <table class="w3-table w3-bordered">
                <tr>
                    <th><fmt:message key="cabinet.tname" bundle="${lang}"/></th>
                    <th><fmt:message key="cabinet.tservice" bundle="${lang}"/></th>
                    <th><fmt:message key="cabinet.tdate" bundle="${lang}"/></th>
                </tr>
                <c:forEach items="${requestScope.userTariffs}" var="ut">
                    <tr>
                        <td><c:choose>
                            <c:when test="${language eq 'en'}">
                                ${ut.tariff.name[1]}
                            </c:when>
                            <c:when test="${language eq 'ua'}">
                                ${ut.tariff.name[0]}
                            </c:when>
                        </c:choose></td>
                        <td><c:out value="${ut.tariff.service.serviceType}"/></td>
                        <td>
                            <c:choose>
                                <c:when test="${ut.dateOfLastPayment == null}">
                                    -
                                </c:when>
                                <c:otherwise>
                                    <fmt:formatDate value="${ut.dateOfLastPayment}"/>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
    </div>
</u:html>