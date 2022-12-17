<%@tag language="java" pageEncoding="UTF-8"%>

<%@attribute name="title" required="true" rtexprvalue="true" type="java.lang.String"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : 'ua'}" scope="session" />
<fmt:setLocale value="${language}"/>


<fmt:setBundle basename="messages" var="lang"/>
<!DOCTYPE html>
<html lang="${language}">
<head>
    <meta charset="UTF-8">
    <title>${title}</title>
    <c:url var="urlCss" value="/styles/w3.css"/>
    <link href="${urlCss}" rel="stylesheet">
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/img/icon.png"/>
</head>
<body>
<div class="w3-container w3-teal">
    <div class="w3-container" style="display: inline-block">
        <h1><a class="w3-hover-none w3-hover-text-white" href="${pageContext.request.contextPath}/"><fmt:message key="application.title"/></a></h1>
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
        <button class="w3-button" onclick="location.href='?language=ua&${pageContext.request.queryString}'">UA</button>
        <button class="w3-button" onclick="location.href='?language=en&${pageContext.request.queryString}'">EN</button>
    </div>
    <%--<span class="w3-container" style="display: inline-block; position: fixed; right: 0; top: 20px">
        <form action="&lt;%&ndash;${pageContext.request.requestURL}&ndash;%&gt;?" method="get">
            <input type="hidden" id="params" name="params" value="${pageContext.request.queryString}">
            <select class="w3-select w3-border w3-col w3-input-group-addon" style="width:50px" name="language" onchange="submit()">
                <option value="en" ${language == 'en' ? 'selected' : ''}>EN</option>
                <option value="ua" ${language == 'ua' ? 'selected' : ''}>UA</option>
            </select>
        </form>
    </span>--%>
</div>

<jsp:doBody/>
</body>
</html>