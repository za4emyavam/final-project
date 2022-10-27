<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<c:choose>
    <c:when test="${not empty cookie['lang'].value}">
        <fmt:setLocale value="${cookie['lang'].value}"/>
    </c:when>
    <c:otherwise>
        <fmt:setLocale value="en"/>
    </c:otherwise>
</c:choose>

<fmt:setBundle basename="messages" var="lang"/>
<fmt:message var="title" key="tariffs.request.title"/>
<u:html title="${title}">
    <h2>${title}</h2>
    <form action="/tariffs/request?id=${requestScope.id}" method="post">
        <div class="w3-inline-input-group">
            <input class="w3-input w3-border w3-input-group-addon w3-col" style="width:250px" id="city" name="city"
                   placeholder="City">
            <input class="w3-input w3-border w3-input-group-addon w3-col" style="width:500px" id="address"
                   name="address" placeholder="Address">
            <button class="w3-btn w3-teal w3-input-group-addon w3-col" style="width:250px" type="submit">Request connect tariff
            </button>
        </div>
    </form>
</u:html>
