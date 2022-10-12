<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <button onclick="location.href='/'">To main</button>
    <button onclick="location.href='/tariffs.html'">Back</button>
    <form action="/tariffs/update.html?id=${requestScope.id}" method="post">
        <input id="name" name="name">
        <input id="description" name="description">
        <input id="cost" name="cost">
        <input id="frequency_of_payment" name="frequency_of_payment">
        <%--<input id="type" name="type">--%>
        <button class="add-button">Change tariff.</button>
    </form>
</body>
</html>
