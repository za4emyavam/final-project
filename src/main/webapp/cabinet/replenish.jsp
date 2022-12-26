<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : 'ua'}"
       scope="session"/>
<fmt:setLocale value="${language}"/>

<fmt:setBundle basename="messages" var="lang"/>
<fmt:message var="title" key="cabinet.replenish.title"/>
<u:html title="${title}">
    <div class="w3-container">
        <h2>${title}</h2>
        <form action="replenish" method="post">
            <input id="amount" name="amount" type="number" required="required">
            <button class="w3-btn w3-teal w3-input-group-addon" style="width:150px" type="submit"><fmt:message
                    key="cabinet.replenish.breplenish" bundle="${lang}"/></button>
        </form>
    </div>
</u:html>
