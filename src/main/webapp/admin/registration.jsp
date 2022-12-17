<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : 'ua'}" scope="session" />
<fmt:setLocale value="${language}"/>

<fmt:setBundle basename="messages" var="lang"/>
<fmt:message var="title" key="admin.registration.title"/>
<u:html title="title">
    <div class="w3-container">
    <h2>${title}</h2>
    <form action="${pageContext.request.contextPath}/admin/registration" method="post" style="margin-left: auto; margin-right: auto; width: 300px">
        <c:if test="${not empty param.message}">
            <p class="error"><fmt:message key="${param.message}"/></p>
        </c:if>
        <div class="w3-container w3-inline-input-group">
            <fmt:message key="admin.registration.email" var="email"/>
            <input class="w3-input w3-border w3-input-group-addon w3-col" style="width: 300px" id="email" name="email"
                   type="email" placeholder="${email}" required="required">
            <br><br>
            <fmt:message key="admin.registration.firstname" var="firstname"/>
            <input class="w3-input w3-border w3-input-group-addon w3-col" style="width: 300px" id="firstname" type="text"
                   name="firstname" placeholder="${firstname}" required="required">
            <br><br>
            <fmt:message key="admin.registration.middlename" var="middle_name"/>
            <input class="w3-input w3-border w3-input-group-addon w3-col" style="width: 300px" id="middle_name" type="text"
                   name="middle_name" placeholder="${middle_name}" required="required">
            <br><br>
            <fmt:message key="admin.registration.surname" var="surname"/>
            <input class="w3-input w3-border w3-input-group-addon w3-col" style="width: 300px" id="surname" type="text"
                   name="surname" placeholder="${surname}" required="required">
            <br><br>
            <fmt:message key="admin.registration.telephone" var="telephone"/>
            <input class="w3-input w3-border w3-input-group-addon w3-col" style="width: 300px" id="telephone_number"
                   name="telephone_number" placeholder="${telephone}" required="required" pattern="^\+38[0-9\-\+]{9,15}$">
            <br><br>
            <fmt:message key="admin.registration.password" var="password"/>
            <input class="w3-input w3-border w3-input-group-addon w3-col" style="width: 300px" id="password" minlength="5" maxlength="32"
                   type="password"
                   name="password" placeholder="${password}" required="required">
            <br><br>
            <button class="w3-btn w3-teal w3-input-group-addon w3-col" style="width: 300px" type="submit"><fmt:message key="admin.registration.button.register"/>
            </button>
        </div>
            <%--<button class="registration">Registr</button>--%>
    </form>
    </div>
</u:html>
