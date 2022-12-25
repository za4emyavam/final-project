<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : 'ua'}"
       scope="session"/>
<fmt:setLocale value="${language}"/>

<fmt:setBundle basename="messages" var="lang"/>
<fmt:message var="title" key="main.title"/>
<u:html title="${title}">
    <div class="w3-container">
        <h2>${title}</h2>
        <div class="w3-center">
            <c:if test="${sessionScope.currentUser != null}">
                <button class="w3-button w3-round" onclick="location.href='logout'"><fmt:message key="main.blogout"
                                                                                                 bundle="${lang}"/></button>
                <button class="w3-button w3-round" onclick="location.href='cabinet'"><fmt:message key="main.bcabinet"
                                                                                                  bundle="${lang}"/></button>
                <c:if test="${sessionScope.currentUser.userRole.name eq 'admin' ||
            sessionScope.currentUser.userRole.name eq 'main_admin'}">
                    <button class="w3-button w3-round" onclick="location.href='admin'"><fmt:message key="main.badmin"
                                                                                                    bundle="${lang}"/></button>
                </c:if>
            </c:if>
            <c:if test="${sessionScope.currentUser == null}">
                <button class="w3-button w3-round" onclick="location.href='login'"><fmt:message key="main.blogin"
                                                                                                bundle="${lang}"/></button>
            </c:if>
            <button class="w3-button w3-round" onclick="location.href='tariffs'"><fmt:message key="main.btariffs"
                                                                                              bundle="${lang}"/></button>
        </div>
        <div class="w3-container">
            <div style="display: flex">
                <div style="width: 50%;">
                    <img src="img/index_photo_1.jpg" style="position: relative; top: 30px; width: 90%; height: 90%; object-fit: cover;
                        box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);">
                </div>
                <div style="width: 50%;">
                    <h3><fmt:message key="main.about" bundle="${lang}"/></h3>
                    <p style="text-align: justify">
                        <fmt:message key="main.text.about" bundle="${lang}"/>
                    </p>
                </div>
            </div>
        </div>

    </div>
</u:html>
