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

    <%--<div class="w3-container w3-blue-grey w3-opacity w3-right-align">
        <h1>"Hello World!"</h1>
    </div>--%>

    <div class="w3-container">
        <h2>${title}</h2>
        <div class="w3-center">
            <c:if test="${sessionScope.currentUser != null}">
                <%--<p class="w3-text-brown">Name of user: ${sessionScope.currentUser.email}</p>--%>
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
            <h3>Про нас</h3>
            <p>
                Компанія Fastest розпочала свою історію в 2006 році. Перший наш крок – надання послуг доступу до глобальної мережі за технологією Ethernet в одному лише районі міста Одеси, Малиновському.

                Вже через 2 роки ми змогли, побудувавши оптичну магістраль, запропонувати нашим клієнтам високошвидкісний Інтернет з безперервним з’єднанням. Ми почали розширювати межі і охопили інші райони міста, підключаючи наших абонентів за технологією FTTH.

                Ще через 2 роки ми змонтували і запустили в експлуатацію власну серверну технічний майданчик. У нас з’явилися нові можливості, і ми оновили обладнання, вибравши продукти відомого американського бренду, компанії Juniper Networks.

                Тоді і з’явилася вперше в Одесі технологія xPON, оптична мережа, яка дозволила нам забезпечити наших абонентів якісним Інтернетом на ще більш високих швидкостях. У 2015 році ми знову оновили обладнання, вибравши на цей раз найбільший бренд на ринку телекомунікацій, компанію Huawei Technologies.

                У своїй роботі ми використовуємо тільки сертифіковане обладнання та комплектуючі від світових брендів. Це дає нам можливість надавати своїм клієнтам цілодобовий доступ до мережі Інтернет на високих швидкостях. І ми не зупиняємося на досягнутому і продовжуємо розвиватися.
            </p>
        </div>
    </div>
</u:html>
