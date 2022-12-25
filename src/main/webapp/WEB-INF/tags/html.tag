<%@tag language="java" pageEncoding="UTF-8" %>

<%@attribute name="title" required="true" rtexprvalue="true" type="java.lang.String" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : 'ua'}"
       scope="session"/>
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
    <header>
        <div class="w3-container" style="display: inline-block">
            <h1><a class="w3-hover-none w3-hover-text-white" href="${pageContext.request.contextPath}/"><fmt:message
                    key="application.title"/></a></h1>
            <c:if test="${not empty currentUser}">
                <c:url var="urlLogout" value="/logout"/>
                <p>
                    <fmt:message key="application.welcome"/> ${currentUser.firstname}
                    (<fmt:message key="role.${currentUser.userRole.name}"/>).
                    <a href="${urlLogout}"><fmt:message key="application.button.logout"/></a>.
                </p>
            </c:if>
        </div>
        <div class="w3-container" style="display: inline-block; position: absolute; top: 20px; right: 0">
            <script>
                function changeLang(lang) {
                    let currentUrl = new URL(location.href);
                    let searchParams = new URLSearchParams(location.search);
                    let language = searchParams.get('language');
                    if (language) {
                        searchParams.set('language', lang);
                    } else {
                        searchParams.append('language', lang);
                    }
                    currentUrl.search = searchParams.toString();
                    location.replace(currentUrl.href);
                }
            </script>
            <button class="w3-button" onclick="changeLang('ua')">UA</button>
            <button class="w3-button" onclick="changeLang('en')">EN</button>
        </div>
    </header>
</div>

<jsp:doBody/>
</body>
</html>