<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE>
<html>
  <head>
    <c:url var="urlCss" value="/styles/w3.css"/>
    <link href="${urlCss}" rel="stylesheet">
  </head>
  <body>
    <h2>Exception!</h2>
    <p class="w3-center">${pageContext.getException().getClass().toString()}</p>
    <br>
    <p>${pageContext.getException().getMessage()}</p>
  </body>
</html>