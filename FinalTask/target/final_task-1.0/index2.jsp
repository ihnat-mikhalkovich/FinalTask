<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>MyHotel</title>
</head>
<body>

<fmt:setLocale value="${ sessionScope.language }" />
<fmt:setBundle basename="data" var="bundle"/>

<form action="/FontController" method="post">
    <input type="hidden" name="commandType" value="language" />
    <input type="hidden" name="currentPage" value="${ pageContext.request.requestURI }" />
    <input type="submit" name="language" value="en"/>
    <br/>
    <input type="submit" name="language" value="ru"/>
    <br/>
</form>

<a href="WEB-INF/jsp/reservation.jsp"><fmt:message key="reservation" bundle="${ bundle }" /></a><br/>

<a href="FontController?commandType=offers"><fmt:message key="rooms_and_prices" bundle="${ bundle }" /></a><br/>

<c:choose>
    <c:when test="${ sessionScope.userId != null }">
        <a href="/FontController?commandType=logout&currentPage=${ pageContext.request.requestURI }"><fmt:message key="sing_out" bundle="${ bundle }" /></a><br/>
    </c:when>
    <c:otherwise>
        <a href="jsp/login.jsp?currentPage=${ pageContext.request.requestURI }"><fmt:message key="sing_in" bundle="${ bundle }" /></a><br/>
        <a href="jsp/registration.jsp"><fmt:message key="sing_up" bundle="${ bundle }" /></a><br/>
    </c:otherwise>
</c:choose>


<br/> <c:out value="${ sessionScope.userId }" />
<br/> <c:out value="${ sessionScope.userRole }" />

</body>
</html>
