<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : 'ua'}"
       scope="session"/>
<fmt:setLocale value="${language}"/>

<fmt:setBundle basename="messages" var="lang"/>
<fmt:message var="title" key="tariffs.add.title"/>
<u:html title="${title}">
    <div class="w3-container">
        <h2>${title}</h2>
        <form action="/tariffs/add" method="post" style="margin-left: auto; margin-right: auto; width: 300px">
            <div class="w3-inline-input-group">
                <fmt:message key="tariffs.add.name.ua" var="nameUA"/>
                <input class="w3-input w3-border w3-input-group-addon w3-col" style="width: 300px" id="nameUA" name="nameUA"
                       placeholder="${nameUA}">
                <br><br>
                <fmt:message key="tariffs.add.name.en" var="nameEN"/>
                <input class="w3-input w3-border w3-input-group-addon w3-col" style="width: 300px" id="nameEN" name="nameEN"
                       placeholder="${nameEN}">
                <br><br>
                <fmt:message key="tariffs.add.description.ua" var="descriptionUA"/>
                <input class="w3-input w3-border w3-input-group-addon w3-col" style="width: 300px" id="descriptionUA"
                       name="descriptionUA" placeholder="${descriptionUA}">
                <br><br>
                <fmt:message key="tariffs.add.description.en" var="descriptionEN"/>
                <input class="w3-input w3-border w3-input-group-addon w3-col" style="width: 300px" id="descriptionEN"
                       name="descriptionEN" placeholder="${descriptionEN}">
                <br><br>
                <fmt:message key="tariffs.add.cost" var="cost"/>
                <input class="w3-input w3-border w3-input-group-addon w3-col" style="width: 300px" id="cost" name="cost"
                       placeholder="${cost}">
                <br><br>
                <fmt:message key="tariffs.add.frequency" var="frequency"/>
                <input class="w3-input w3-border w3-input-group-addon w3-col" style="width: 300px"
                       id="frequency_of_payment"
                       name="frequency_of_payment" placeholder="${frequency}">
                <br><br>
                <select class="w3-select w3-border w3-col w3-input-group-addon" style="width: 300px" id="service"
                        name="service">
                    <c:forEach items="${requestScope.services}" var="s">
                        <option value="${s.serviceType}">${s.serviceType}</option>
                    </c:forEach>
                </select>
                <br><br>
                <button class="w3-btn w3-teal w3-input-group-addon" style="width: 300px" type="submit"><fmt:message
                        key="tariffs.add.button.add"/>
                </button>
            </div>
        </form>
    </div>
</u:html>
