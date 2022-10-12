<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page isELIgnored="false" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
    <c:if test="${sessionScope.currentUser.role == 'ADMINISTRATOR'}">
        <button onclick="location.href='/tariffs/add.html'">Add new tariff</button>
    </c:if>
    <table>
        <tr>
            <th>id</th>
            <th>name</th>
            <th>description</th>
            <th>cost</th>
            <th>frequency</th>
            <th>t.type</th>
            <th>s.type</th>
        </tr>
        <c:forEach items="${requestScope.tariffs}" var="t">
            <tr>
                <td><c:out value="${t.id}"/></td>
                <td><c:out value="${t.name}"/></td>
                <td><c:out value="${t.description}"/></td>
                <td><c:out value="${t.cost}"/></td>
                <td><c:out value="${t.frequencyOfPayment}"/></td>
                <td><c:out value="${t.type.id}"/></td>
                <td><c:out value="${t.type.type}"/></td>
                <c:if test="${sessionScope.currentUser.role == 'ADMINISTRATOR'}">
                    <td><button onclick="location.href='/tariffs/update.html?id=${t.id}'">Change data</button></td>
                    <td><button onclick="location.href='/tariffs/delete.html?id=${t.id}'">Delete</button></td>
                </c:if>
            </tr>
        </c:forEach>
    </table>
    <button onclick="location.href='/index.html'">To main</button>
</body>
</html>
