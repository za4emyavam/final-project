<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>

<c:choose>
  <c:when test="${not empty cookie['lang'].value}">
    <fmt:setLocale value="${cookie['lang'].value}"/>
  </c:when>
  <c:otherwise>
    <fmt:setLocale value="en"/>
  </c:otherwise>
</c:choose>

<fmt:setBundle basename="messages" var="lang"/>
<fmt:message var="title" key="admin.users.title"/>
<u:html title="${title}">
  <h2>${title}</h2>
  <table class="w3-table w3-bordered">
    <tr>
      <th>id</th>
      <th>email</th>
      <th>firstname</th>
      <th>surname</th>
      <th>user role</th>
      <th>registration date</th>
      <th>user status</th>
    </tr>
    <c:forEach items="${requestScope.users}" var="u">
      <tr onclick="location.href='/admin/users/user_info?id=${u.id}'">
        <td>${u.id}</td>
        <td>${u.email}</td>
        <td>${u.firstname}</td>
        <td>${u.surname}</td>
        <td>${u.userRole.name}</td>
        <td><fmt:formatDate pattern="dd MM yyyy" value="${u.registrationDate}"/></td>
        <td>${u.userStatus}</td>
      </tr>
    </c:forEach>
  </table>
</u:html>
