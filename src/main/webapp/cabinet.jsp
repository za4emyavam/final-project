<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib tagdir="/WEB-INF/tags" prefix="u"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:choose>
    <c:when test="${not empty cookie['lang'].value}">
        <fmt:setLocale value="${cookie['lang'].value}"/>
    </c:when>
    <c:otherwise>
        <fmt:setLocale value="en"/>
    </c:otherwise>
</c:choose>

<fmt:setBundle basename="messages" var="lang"/>
<fmt:message var="title" key="cabinet.title"/>
<u:html title="${title}">
    <h2>${title}</h2>
    <p class="w3-text-brown">Hello, ${sessionScope.currentUser.firstname}!</p>
    <p class="w3-text-brown">Bank: ${sessionScope.currentUser.userBalance}</p>
    <p class="w3-text-brown">Your user status: ${sessionScope.currentUser.userStatus.name}</p>
    <button class="w3-button w3-round" onclick="location.href='/cabinet/replenish'">REPLENISH BALANCE</button>
    <%--<button class="w3-button w3-round" onclick="location.href='/cabinet/change_password'">TODO: CHANGE PASSWORD</button>--%>
    <button class="w3-button w3-round" onclick="location.href='/cabinet/history'">CHECK BALANCE HISTORY</button>
    <c:if test="${requestScope.userTariffs != null}">
        <p>Your tariffs:</p>
        <table class="w3-table w3-bordered">
            <tr>
                <th>tariff name</th>
                <th>tariff service</th>
                <th>tariff date of last payment</th>
            </tr>
            <c:forEach items="${requestScope.userTariffs}" var="ut">
                <tr>
                    <td><c:out value="${ut.tariff.name}"/></td>
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

</u:html>