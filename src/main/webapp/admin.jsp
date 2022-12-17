<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : 'ua'}"
       scope="session"/>
<fmt:setLocale value="${language}"/>

<fmt:setBundle basename="messages" var="lang"/>
<fmt:message var="title" key="admin.title"/>
<u:html title="${title}">
    <div class="w3-container">
        <h2>${title}</h2>
        <div class="w3-center">
            <button class="w3-button w3-round" onclick="location.href='/admin/requests'"><fmt:message
                    key="admin.button.checkrequests"/></button>
            <br>
            <button class="w3-button w3-round" onclick="location.href='/admin/users'"><fmt:message
                    key="admin.button.allusers"/></button>
            <br>
            <button class="w3-button w3-round" onclick="location.href='/admin/registration'"><fmt:message
                    key="admin.button.registeruser"/></button>
            <br>
            <form action="/admin/check_payment" method="post">
                <button class="w3-btn w3-teal" type="submit"><fmt:message key="admin.button.checkpayment"/></button>
            </form>
        </div>
    </div>

</u:html>
