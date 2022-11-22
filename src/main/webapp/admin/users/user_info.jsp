<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : 'ua'}"
       scope="session"/>
<fmt:setLocale value="${language}"/>

<fmt:setBundle basename="messages" var="lang"/>
<fmt:message var="title" key="admin.users.user_info.title"/>
<u:html title="${title}">
    <div class="w3-container">
        <h2>${title}</h2>
        <p><fmt:message key="admin.users.user_info.id"/>: ${requestScope.user.id}</p>
        <p><fmt:message key="admin.users.user_info.firstname"/>: ${requestScope.user.firstname}</p>
        <p><fmt:message key="admin.users.user_info.middlename"/>: ${requestScope.user.middleName}</p>
        <p><fmt:message key="admin.users.user_info.surname"/>: ${requestScope.user.surname}</p>
        <p><fmt:message key="admin.users.user_info.email"/>: ${requestScope.user.email}</p>
        <p><fmt:message key="admin.users.user_info.telephone"/>: ${requestScope.user.telephoneNumber}</p>
        <p><fmt:message key="admin.users.user_info.balance"/>: ${requestScope.user.userBalance}</p>
        <p><fmt:message key="admin.users.user_info.date"/>: <fmt:formatDate
                value="${requestScope.user.registrationDate}"/></p>
        <p><fmt:message key="admin.users.user_info.role"/>:
            <c:choose>
                <c:when test="${requestScope.user.userRole.name == 'main_admin'}">
                    <fmt:message key="role.main_admin"/>
                </c:when>
                <c:when test="${requestScope.user.userRole.name == 'admin'}">
                    <fmt:message key="role.admin"/>
                </c:when>
                <c:otherwise>
                    <fmt:message key="role.user"/>
                </c:otherwise>
            </c:choose>
        </p>
        <c:if test="${sessionScope.currentUser.userRole.name == 'main_admin'}">
            <form name="change_role" method="post">
                <input type="hidden" name="command" value="change_role">
                <div class="w3-container w3-inline-input-group">
                    <select class="w3-select w3-border w3-col w3-input-group-addon" style="width:150px" id="role"
                            name="role">
                        <option value="user"><fmt:message key="role.user"/></option>
                        <option value="admin"><fmt:message key="role.admin"/></option>
                    </select>
                    <button class="w3-btn w3-teal w3-input-group-addon" style="width:150px" type="submit"><fmt:message
                            key="admin.users.user_info.button.changerole"/>
                    </button>
                </div>
            </form>
        </c:if>
        <p><fmt:message key="admin.users.user_info.status"/>:
            <c:choose>
                <c:when test="${requestScope.user.userStatus.name == 'subscribed'}">
                    <fmt:message key="status.type.subscribed"/>
                </c:when>
                <c:otherwise>
                    <fmt:message key="status.type.blocked"/>
                </c:otherwise>
            </c:choose>
        </p>
        <form name="change_status" method="post">
            <input type="hidden" name="command" value="change_status">
            <div class="w3-container w3-inline-input-group">
                <select class="w3-select w3-border w3-col w3-input-group-addon" style="width:150px" id="status"
                        name="status">
                    <option value="blocked"><fmt:message key="status.type.blocked"/></option>
                    <option value="subscribed"><fmt:message key="status.type.subscribed"/></option>
                </select>
                <button class="w3-btn w3-teal w3-input-group-addon" style="width:150px" type="submit"><fmt:message
                        key="admin.users.user_info.button.changestatus"/>
                </button>
            </div>
        </form>
        <c:if test="${requestScope.userTariffs != null}">
            <p><fmt:message key="admin.users.user_info.tariffs"/>: </p>
            <table class="w3-table w3-bordered">
                <tr>
                    <th><fmt:message key="admin.users.user_info.tariffs.name"/></th>
                    <th><fmt:message key="admin.users.user_info.tariffs.service"/></th>
                    <th><fmt:message key="admin.users.user_info.tariffs.cost"/></th>
                    <th><fmt:message key="admin.users.user_info.tariffs.frequency"/></th>
                    <th><fmt:message key="admin.users.user_info.tariffs.startdate"/></th>
                    <th><fmt:message key="admin.users.user_info.tariffs.paymentdate"/></th>
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
                                <button class="w3-btn w3-teal" style="width:150px" type="submit"><fmt:message
                                        key="admin.users.user_info.tariffs.button.unsubscribe"/>
                                </button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
        <c:if test="${requestScope.userTariffs == null}">
            <p><fmt:message key="admin.users.user_info.notariffs"/></p>
        </c:if>
    </div>
</u:html>
