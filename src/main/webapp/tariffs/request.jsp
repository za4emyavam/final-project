<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : 'ua'}"
       scope="session"/>
<fmt:setLocale value="${language}"/>

<fmt:setBundle basename="messages" var="lang"/>
<fmt:message var="title" key="tariffs.request.title"/>
<u:html title="${title}">
    <div class="w3-container">
        <h2>${title}</h2>
        <form action="/tariffs/request?id=${requestScope.id}" method="post"
              style="margin-left: auto; margin-right: auto; width: 300px">
            <div class="w3-inline-input-group">
                <fmt:message key="tariffs.request.city" var="city"/>
                <input class="w3-input w3-border w3-input-group-addon w3-col" style="width:300px" id="city" name="city"
                       placeholder="${city}">
                <br><br>
                <fmt:message key="tariffs.request.address" var="address"/>
                <input class="w3-input w3-border w3-input-group-addon w3-col" style="width:300px" id="address"
                       name="address" placeholder="${address}">
                <br><br>
                <button class="w3-btn w3-teal w3-input-group-addon w3-col" style="width:300px" type="submit">
                    <fmt:message key="tariffs.request.button.request"/>
                </button>
            </div>
        </form>
    </div>
</u:html>
