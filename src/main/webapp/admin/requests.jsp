<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : 'ua'}"
       scope="session"/>
<fmt:setLocale value="${language}"/>

<fmt:setBundle basename="messages" var="lang"/>
<fmt:message var="title" key="admin.requests.title"/>
<u:html title="title">
    <div class="w3-container">
        <h2>${title}</h2>
        <table class="w3-table w3-bordered">
            <tr>
                <th><fmt:message key="admin.requests.id"/></th>
                <th><fmt:message key="admin.requests.subscriberid"/></th>
                <th><fmt:message key="admin.requests.tariff"/></th>
                <th><fmt:message key="admin.requests.date"/></th>
                <th><fmt:message key="admin.requests.status"/></th>
            </tr>
            <c:forEach items="${requestScope.requests}" var="r">
                <tr onclick="location.href='/admin/requests/update?id=${r.id}'">
                    <td>${r.id}</td>
                    <td>${r.subscriber.id}</td>
                    <td>
                        <c:choose>
                            <c:when test="${language eq 'en'}">
                                ${r.tariff.name[1]}
                            </c:when>
                            <c:when test="${language eq 'ua'}">
                                ${r.tariff.name[0]}
                            </c:when>
                        </c:choose>
                    </td>
                    <td><fmt:formatDate pattern="dd MM yyyy" value="${r.dateOfChange}"/></td>
                    <td>
                        <c:choose>
                            <c:when test="${r.status.value == 'in processing'}">
                                <fmt:message key="requeststatus.type.inprocessing"/>
                            </c:when>
                            <c:when test="${r.status.value == 'rejected'}">
                                <fmt:message key="requeststatus.type.rejected"/>
                            </c:when>
                            <c:otherwise>
                                <fmt:message key="requeststatus.type.approved"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <div class="w3-bar">
            <c:forEach begin="1" end="${requestScope.noOfPages}" var="i">
                <a href="${pageContext.request.contextPath}/admin/requests?page=${i}"
                   class="w3-button">${i}</a>
            </c:forEach>
        </div>
    </div>
</u:html>
