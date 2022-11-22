<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : 'ua'}"
       scope="session"/>
<fmt:setLocale value="${language}"/>

<fmt:setBundle basename="messages" var="lang"/>
<fmt:message var="title" key="admin.users.title"/>
<u:html title="${title}">
    <div class="w3-container">
        <h2>${title}</h2>
        <table class="w3-table w3-bordered">
            <tr>
                <th><fmt:message key="admin.users.id"/></th>
                <th><fmt:message key="admin.users.email"/></th>
                <th><fmt:message key="admin.users.firstname"/></th>
                <th><fmt:message key="admin.users.surname"/></th>
                <th><fmt:message key="admin.users.role"/></th>
                <th><fmt:message key="admin.users.date"/></th>
                <th><fmt:message key="admin.users.status"/></th>
            </tr>
            <c:forEach items="${requestScope.users}" var="u">
                <tr onclick="location.href='/admin/users/user_info?id=${u.id}'">
                    <td>${u.id}</td>
                    <td>${u.email}</td>
                    <td>${u.firstname}</td>
                    <td>${u.surname}</td>
                    <td>
                        <c:choose>
                            <c:when test="${u.userRole.name == 'main_admin'}">
                                <fmt:message key="role.main_admin"/>
                            </c:when>
                            <c:when test="${u.userRole.name == 'admin'}">
                                <fmt:message key="role.admin"/>
                            </c:when>
                            <c:otherwise>
                                <fmt:message key="role.user"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td><fmt:formatDate pattern="dd.MM.yyyy" value="${u.registrationDate}"/></td>
                    <td>
                        <c:choose>
                            <c:when test="${u.userStatus.name == 'subscribed'}">
                                <fmt:message key="status.type.subscribed"/>
                            </c:when>
                            <c:otherwise>
                                <fmt:message key="status.type.blocked"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <div class="w3-bar">
            <c:forEach begin="1" end="${noOfPages}" var="i">
                <a href="/admin/users?page=${i}" class="w3-button">${i}</a>
            </c:forEach>
        </div>
    </div>
</u:html>
