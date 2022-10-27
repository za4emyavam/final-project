<%@tag language="java" pageEncoding="UTF-8"%>

<%@attribute name="title" required="true" rtexprvalue="true" type="java.lang.String"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:choose>
    <c:when test="${not empty cookie['lang'].value}">
        <fmt:setLocale value="${cookie['lang'].value}"/>
    </c:when>
    <c:otherwise>
        <fmt:setLocale value="en"/>
    </c:otherwise>
</c:choose>

<%--<fmt:setBundle basename="localization.messages.utf8" var="lang"/>--%>
<fmt:setBundle basename="messages"/>
<!DOCTYPE html>
<html lang="">
<head>
    <%--<c:out value="${cookie['lang'].value}"/>--%>
    <meta charset="UTF-8">
    <title>${title}</title>
    <c:url var="urlCss" value="/styles/w3.css"/>
    <link href="${urlCss}" rel="stylesheet">
</head>
<body>
<div class="w3-container w3-teal" >
    <div class="w3-container" style="display: inline-block">
        <h1><a class="w3-hover-none w3-hover-text-white" href="/index"><fmt:message key="application.title"/></a></h1>
        <c:if test="${not empty currentUser}">
            <c:url var="urlLogout" value="/logout"/>
            <p>
                <fmt:message key="application.welcome"/> ${currentUser.firstname}
                (<fmt:message key="role.${currentUser.userRole.name}"/>).
                <a href="${urlLogout}"><fmt:message key="application.button.logout"/></a>.
            </p>
        </c:if>
    </div>
    <div class="w3-container" style="display: inline-block; position: fixed; right: 0">
        <button class="w3-button" onclick="location.href='?cookieLocale=ua'">UA</button>
        <button class="w3-button" onclick="location.href='?cookieLocale=en'">EN</button>
    </div>
</div>

<jsp:doBody/>
</body>
</html>