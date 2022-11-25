<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : 'ua'}"
       scope="session"/>
<fmt:setLocale value="${language}"/>

<fmt:setBundle basename="messages" var="lang"/>
<fmt:message var="title" key="tariffs.update.title"/>
<u:html title="${title}">
    <div class="w3-container">
        <h2>${title}</h2>
        <div class="w3-center">
            <form action="/tariffs/update?id=${requestScope.id}" method="post"
                  style="margin-left: auto; margin-right: auto; width: 300px">
                <input class="w3-input w3-border w3-input-group-addon w3-col" style="width:300px" id="nameUA"
                       name="nameUA"
                       value="${requestScope.currentTariff.name[0]}">
                <br><br>
                <input class="w3-input w3-border w3-input-group-addon w3-col" style="width:300px" id="nameEN"
                       name="nameEN"
                       value="${requestScope.currentTariff.name[1]}">
                <br><br>
                <input class="w3-input w3-border w3-input-group-addon w3-col" style="width: 300px" id="descriptionUA"
                       name="descriptionUA"
                       value="${requestScope.currentTariff.description[0]}">
                <br><br>
                <input class="w3-input w3-border w3-input-group-addon w3-col" style="width: 300px" id="descriptionEN"
                       name="descriptionEN"
                       value="${requestScope.currentTariff.description[1]}">
                <br><br>
                <input class="w3-input w3-border w3-input-group-addon w3-col" style="width:300px" id="cost"
                       name="cost"
                       value="${requestScope.currentTariff.cost}">
                <br><br>
                <input class="w3-input w3-border w3-input-group-addon w3-col" style="width:300px"
                       id="frequency_of_payment" name="frequency_of_payment"
                       value="${requestScope.currentTariff.frequencyOfPayment}">
                <br><br>
                <select class="w3-select w3-border w3-col w3-input-group-addon" style="width:300px" id="service"
                        name="service">
                    <option value="" disabled selected>
                        <fmt:message key="tariffs.update.was"/>: ${requestScope.currentTariff.service.serviceType}
                    </option>
                    <c:forEach items="${requestScope.services}" var="s">
                        <option value="${s.serviceType}">${s.serviceType}</option>
                    </c:forEach>
                </select>
                <br><br>
                    <%--<select class="w3-select w3-border w3-input-group-addon" id="status" name="status">
                        <option value="" disabled selected>Was: ${requestScope.currentTariff.tariffStatus.name} </option>
                        <option value="disabled">disabled</option>
                        <option value="active">active</option>
                    </select>--%>
                <button class="w3-btn w3-teal w3-input-group-addon" style="width:300px" type="submit"><fmt:message
                        key="tariffs.update.button.change"/>
                </button>
            </form>
        </div>
    </div>
</u:html>
