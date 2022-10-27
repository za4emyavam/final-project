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
    <div class="w3-container">
        <p>User id: ${requestScope.user.id}</p>
        <p>firstname: ${requestScope.user.firstname}</p>
        <p>middle name: ${requestScope.user.middleName}</p>
        <p>surname: ${requestScope.user.surname}</p>
        <p>email: ${requestScope.user.email}</p>
        <p>telephone number: ${requestScope.user.telephoneNumber}</p>
        <p>balance: ${requestScope.user.userBalance}</p>
        <p>registration date: <fmt:formatDate value="${requestScope.user.registrationDate}"/></p>
        <p>role: ${requestScope.user.userRole.name}</p>
        <c:if test="${sessionScope.currentUser.userRole.name == 'main_admin'}">
            <form name="change_role" method="post">
                <input type="hidden" name="command" value="change_role">
                <div class="w3-container w3-inline-input-group">
                    <select class="w3-select w3-border w3-col w3-input-group-addon" style="width:150px" id="role" name="role">
                        <option value="user">user</option>
                        <option value="admin">admin</option>
                    </select>
                    <button class="w3-btn w3-teal w3-input-group-addon" style="width:150px" type="submit">Change status</button>
                </div>
            </form>
        </c:if>
        <p>status: ${requestScope.user.userStatus.name}</p>
        <form name="change_status" method="post">
            <input type="hidden" name="command" value="change_status">
            <div class="w3-container w3-inline-input-group">
                <select class="w3-select w3-border w3-col w3-input-group-addon" style="width:150px" id="status" name="status">
                    <option value="blocked">blocked</option>
                    <option value="subscribed">subscribed</option>
                </select>
                <button class="w3-btn w3-teal w3-input-group-addon" style="width:150px" type="submit">Change status</button>
            </div>
        </form>
        <c:if test="${requestScope.userTariffs != null}">
            <p>tariffs: </p>
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
                        <td>
                            <form name="unsubscribe" method="post">
                                <input type="hidden" name="command" value="unsubscribe">
                                <input type="hidden" name="tariff_id" value="${ut.tariff.id}">
                                <button class="w3-btn w3-teal" style="width:150px" type="submit">Unsubscribe</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
        <c:if test="${requestScope.userTariffs == null}">
            <p>User don't have tariffs</p>
        </c:if>
    </div>

</u:html>
