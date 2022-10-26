<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>

<c:choose>
  <c:when test="${not empty cookie['lang'].value}">
    <fmt:setLocale value="${cookie['lang'].value}"/>
  </c:when>
  <c:otherwise>
    <fmt:setLocale value="en"/>
  </c:otherwise>
</c:choose>

<fmt:setBundle basename="messages" var="lang"/>
<fmt:message var="title" key="admin.users.user_info.title"/>
<u:html title="${title}">
  <h2>${title}</h2>
  <p>${requestScope.user.id}</p>
  <p>${requestScope.user.firstname}</p>
  <p>${requestScope.user.middleName}</p>
  <p>${requestScope.user.surname}</p>
  <p>${requestScope.user.email}</p>
  <p>${requestScope.user.telephoneNumber}</p>
  <p>${requestScope.user.userBalance}</p>
  <p><fmt:formatDate value="${requestScope.user.registrationDate}"/></p>
  <p>${requestScope.user.userRole.name}</p>
  <p>${requestScope.user.userStatus.name}</p>
  <c:if test="${requestScope.userTariffs != null}">
    <table class="w3-table w3-bordered">
      <tr>
        <th>tariff name</th>
        <th>tariff service</th>
        <th>tariff cost</th>
        <th>tariff frequency of payment</th>
        <th>tariff date of start</th>
        <th>tariff date of last payment</th>
      </tr>
      <c:forEach items="${requestScope.userTariffs}" var="ut">
        <tr>
          <td><c:out value="${ut.tariff.name}"/></td>
          <td><c:out value="${ut.tariff.service.serviceType}"/></td>
          <td>${ut.tariff.cost}</td>
          <td>${ut.tariff.frequencyOfPayment}</td>
          <td><fmt:formatDate value="${ut.dateOfStart}"/></td>
          <td>
            <c:choose>
              <c:when test="${ut.dateOfLastPayment == null}">
                -
              </c:when>
              <c:otherwise>
                <fmt:formatDate value="${ut.dateOfLastPayment}"/>
              </c:otherwise>
            </c:choose>
          </td>
        </tr>
      </c:forEach>
    </table>
  </c:if>
  <c:if test="${requestScope.userTariffs == null}">
    <p>User don't have tariffs</p>
  </c:if>
</u:html>
