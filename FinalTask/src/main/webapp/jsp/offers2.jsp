<%--
  Created by IntelliJ IDEA.
  User: HOME
  Date: 11.04.2018
  Time: 16:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Offers</title>
</head>
<body>

<c:forEach var="elem" items="${ requestScope.rooms }" varStatus="status">
    <c:out value="${ elem.name }" /><br/>
</c:forEach>

</body>
</html>
