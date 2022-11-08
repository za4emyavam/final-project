<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:choose>
    <c:when test="${not empty cookie['lang'].value}">
        <fmt:setLocale value="${cookie['lang'].value}"/>
    </c:when>
    <c:otherwise>
        <fmt:setLocale value="en"/>
    </c:otherwise>
</c:choose>

<fmt:setBundle basename="messages" var="lang"/>
<fmt:message var="title" key="admin.registration.title"/>
<u:html title="title">
    <h2>${title}</h2>
    <form action="/admin/registration" method="post">
        <div class="w3-container w3-inline-input-group">

            <input class="w3-input w3-border w3-input-group-addon w3-col" style="width: 300px" id="email" name="email"
                   type="email" placeholder="Email" required="required">
            <input class="w3-input w3-border w3-input-group-addon w3-col" style="width: 150px" id="firstname" type="text"
                   name="firstname" placeholder="Firstname" required="required">
            <input class="w3-input w3-border w3-input-group-addon w3-col" style="width: 200px" id="middle_name" type="text"
                   name="middle_name" placeholder="Middle name" required="required">
            <input class="w3-input w3-border w3-input-group-addon w3-col" style="width: 200px" id="surname" type="text"
                   name="surname" placeholder="Surname" required="required">
            <input class="w3-input w3-border w3-input-group-addon w3-col" style="width: 200px" id="telephone_number"
                   name="telephone_number" placeholder="Telephone number" required="required" pattern="^\+38[0-9\-\+]{9,15}$">
            <input class="w3-input w3-border w3-input-group-addon w3-col" style="width: 200px" id="password" minlength="5" maxlength="32"
                   type="password"
                   name="password" type="password" placeholder="Password" required="required">
            <button class="w3-btn w3-teal w3-input-group-addon w3-col" style="width: 150px" type="submit">Registr
            </button>
        </div>
            <%--<button class="registration">Registr</button>--%>
    </form>
</u:html>
