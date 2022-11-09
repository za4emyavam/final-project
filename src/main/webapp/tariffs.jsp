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
<fmt:message var="title" key="tariffs.title"/>
<u:html title="${title}">
    <h2>${title}</h2>
    <c:if test="${sessionScope.currentUser.userRole == 'MAIN_ADMIN'}">
        <button class="w3-btn w3-teal" onclick="location.href='/tariffs/add'">Add new tariff</button>
    </c:if>
    <%--<form name="tariffs" method="get">
        <div class="w3-container w3-inline-input-group">
            <p>Change order by</p>
            <select class="w3-select w3-border w3-col w3-input-group-addon" style="width:150px" id="order" name="order">
                <option value="t.id">id</option>
                <option value="cost">cost</option>
                <option value="name">name</option>
            </select>
            <button class="w3-btn w3-teal w3-input-group-addon" style="width:150px" type="submit">verdict</button>
        </div>
    </form>--%>
    <table class="w3-table w3-bordered">
        <tr>
            <fmt:message var="id" key="tariffs.id"/>
            <th>
                <c:choose>
                    <c:when test="${requestScope.orderBy eq 'tariff_id'}">
                        <c:choose>
                            <c:when test="${requestScope.order eq 'desc'}">
                                <a href="/tariffs?orderBy=tariff_id&order=asc">${id}</a>
                            </c:when>
                            <c:otherwise>
                                <a href="/tariffs?orderBy=tariff_id&order=desc">${id}</a>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        <a href="/tariffs?orderBy=tariff_id&order=asc">${id}</a>
                    </c:otherwise>
                </c:choose>
            </th>
            <fmt:message var="name" key="tariffs.name"/>

            <th>
                <c:choose>
                    <c:when test="${requestScope.orderBy eq 'name'}">
                        <c:choose>
                            <c:when test="${requestScope.order eq 'desc'}">
                                <a href="/tariffs?orderBy=name&order=asc">${name}</a>
                            </c:when>
                            <c:otherwise>
                                <a href="/tariffs?orderBy=name&order=desc">${name}</a>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        <a href="/tariffs?orderBy=name&order=asc">${name}</a>
                    </c:otherwise>
                </c:choose>
            </th>
            <fmt:message var="descr" key="tariffs.description"/>
            <th>${descr}</th>
            <fmt:message var="cost" key="tariffs.cost"/>
            <th>
                <c:choose>
                    <c:when test="${requestScope.orderBy eq 'cost'}">
                        <c:choose>
                            <c:when test="${requestScope.order eq 'desc'}">
                                <a href="/tariffs?orderBy=cost&order=asc">${cost}</a>
                            </c:when>
                            <c:otherwise>
                                <a href="/tariffs?orderBy=cost&order=desc">${cost}</a>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        <a href="/tariffs?orderBy=cost&order=asc">${cost}</a>
                    </c:otherwise>
                </c:choose>
            </th>
            <fmt:message var="fr" key="tariffs.frequency"/>
            <th>${fr}</th>
            <fmt:message var="st" key="tariffs.service_type"/>
            <th>
                <c:choose>
                    <c:when test="${requestScope.orderBy eq 'service_type'}">
                        <c:choose>
                            <c:when test="${requestScope.order eq 'desc'}">
                                <a href="/tariffs?orderBy=service_type&order=asc">${st}</a>
                            </c:when>
                            <c:otherwise>
                                <a href="/tariffs?orderBy=service_type&order=desc">${st}</a>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        <a href="/tariffs?orderBy=service_type&order=asc">${st}</a>
                    </c:otherwise>
                </c:choose>
            </th>
        </tr>
        <c:forEach items="${requestScope.tariffs}" var="t">
            <tr>
                <td><c:out value="${t.id}"/></td>
                <td><a href="tariffs/request?id=${t.id}"><c:out value="${t.name}"/></a></td>
                <td><c:out value="${t.description}"/></td>
                <td><c:out value="${t.cost}"/></td>
                <td><c:out value="${t.frequencyOfPayment}"/></td>
                <td><c:out value="${t.service.serviceType}"/></td>
                <c:if test="${sessionScope.currentUser.userRole == 'MAIN_ADMIN'}">
                    <td>
                        <button class="w3-btn w3-teal" onclick="location.href='/tariffs/update?id=${t.id}'">Change
                            data
                        </button>
                    </td>
                    <td>
                        <button class="w3-btn w3-teal" onclick="location.href='/tariffs/delete?id=${t.id}'">Delete
                        </button>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
    </table>
    <div class="w3-bar">
        <c:forEach begin="1" end="${noOfPages}" var="i">
            <a href="/tariffs?page=${i}&orderBy=${requestScope.orderBy}&order=${requestScope.order}"
               class="w3-button">${i}</a>
        </c:forEach>
    </div>

    <button onclick="location.href='/tariffs/download'">Download PDF</button>

</u:html>
