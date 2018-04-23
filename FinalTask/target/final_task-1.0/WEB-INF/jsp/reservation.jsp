<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Reservation</title>
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

<c:choose>
    <c:when test="${ sessionScope.userId != null }">
        <a href="/FontController?commandType=logout&currentPage=${ pageContext.request.requestURI }"><fmt:message key="sing_out" bundle="${ bundle }" /></a><br/>
    </c:when>
    <c:otherwise>
        <a href="login.jsp?currentPage=${ pageContext.request.requestURI }"><fmt:message key="sing_in" bundle="${ bundle }" /></a><br/>
    </c:otherwise>
</c:choose>

    <form action="/FontController" method="get">
        <input type="hidden" name="commandType" value="reservation" />
        <fmt:message key="arrival_date" bundle="${ bundle }" />:&nbsp <input type="date" name="firstDate"><br>
        <fmt:message key="date_of_departure" bundle="${ bundle }" />: <input type="date" name="lastDate"><br>
        <fmt:message key="number_of_rooms" bundle="${ bundle }" />: <input type="number" name="name" value="1"><br>
        <input type="submit" value="<fmt:message key="search" bundle="${ bundle }" />">
    </form>

    <a href="../../index.jsp"><fmt:message key="home" bundle="${ bundle }" /></a><br/>

</body>
</html>
