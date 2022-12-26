<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : 'ua'}"
       scope="session"/>
<fmt:setLocale value="${language}"/>

<fmt:setBundle basename="messages" var="lang"/>
<fmt:message var="title" key="cabinet.history.title"/>
<u:html title="${title}">
    <div class="w3-container">
        <h2>${title}</h2>
        <table class="w3-table w3-bordered">
            <tr>
                <th><fmt:message key="cabinet.history.id" bundle="${lang}"/></th>
                <th><fmt:message key="cabinet.history.type" bundle="${lang}"/></th>
                <th><fmt:message key="cabinet.history.amount" bundle="${lang}"/></th>
                <th><fmt:message key="cabinet.history.date" bundle="${lang}"/></th>
                <th><fmt:message key="cabinet.history.status" bundle="${lang}"/></th>
            </tr>
            <c:forEach items="${requestScope.transactions}" var="t">
                <tr>
                    <td><c:out value="${t.id}"/></td>
                    <td>
                        <c:choose>
                            <c:when test="${t.type.value == 'refill'}">
                                <fmt:message key="transaction.type.refill" bundle="${lang}"/>
                            </c:when>
                            <c:otherwise>
                                <fmt:message key="transaction.type.debit" bundle="${lang}"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td><c:out value="${t.transactionAmount}"/></td>
                    <td><fmt:formatDate value="${t.transactionDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    <td>
                        <c:choose>
                            <c:when test="${t.status.value == 'successful'}">
                                <fmt:message key="transaction.status.type.successful" bundle="${lang}"/>
                            </c:when>
                            <c:otherwise>
                                <fmt:message key="transaction.status.type.denied" bundle="${lang}"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</u:html>
