<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 09.10.2022
  Time: 17:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add tariff</title>
</head>
<body>
    <%--<c:if test="${sessionScope.services != null}">
        <table>
            <tr>
                <th>Service id</th>
                <th>Service type</th>
            </tr>
        <c:forEach items="${services}" var="service">
            <tr><c:out value="${service.id}"/> </tr>
            <tr><c:out value="${service.type}"/> </tr>
        </c:forEach>
        </table>
    </c:if>--%>
    <button onclick="location.href='/'">To main</button>
    <button onclick="location.href='/tariffs.html'">Back</button>
    <form action="/tariffs/add.html" method="post">
        <input id="name" name="name">
        <input id="description" name="description">
        <input id="cost" name="cost">
        <input id="frequency_of_payment" name="frequency_of_payment">
        <%--<input id="type" name="type">--%>
        <select id="type" name="type">
            <c:forEach items="${requestScope.services}" var="service">
                <option value="${service.type}">${service.type}</option>
            </c:forEach>
        </select>
        <button class="add-button">Add tariff.</button>
    </form>
</body>
</html>
