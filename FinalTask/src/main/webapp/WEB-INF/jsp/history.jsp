<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
    <fmt:setLocale value="${ sessionScope.language }" />
    <fmt:setBundle basename="data" var="bundle"/>
    <title><fmt:message key="history" bundle="${ bundle }" /></title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" type="text/css" href="../../css/style.css">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

    <link rel="stylesheet" href="https://formden.com/static/cdn/font-awesome/4.4.0/css/font-awesome.min.css" />

    <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" />

</head>
<body>

<c:set var="pageNumber" value="${ requestScope.history.currentPage }" scope="page" />
<c:set var="currentPage" value="/FrontController?commandType=history&page=${ pageNumber }" />
<nav class="navbar navbar-inverse">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="../index.jsp">MyHotel</a>
        </div>
        <ul class="nav navbar-nav">
            <li><a href="../index.jsp"><fmt:message key="home" bundle="${ bundle }" /></a></li>
            <li><a href="#reserving" data-toggle="modal"><fmt:message key="reservation" bundle="${ bundle }" /></a></li>
            <li><a href="/FrontController?commandType=offers"><fmt:message key="rooms_and_prices" bundle="${ bundle }" /></a></li>
            <li><a href="jsp/contacts.jsp"><fmt:message key="contacts" bundle="${ bundle }" /></a></li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <li class="active"><a href="/FrontController?commandType=profile"><span class="glyphicon glyphicon-bookmark"></span> <fmt:message key="profile" bundle="${ bundle }" /></a></li>
            <li><a href="/FrontController?commandType=logout&currentPage=/index.jsp"><span class="glyphicon glyphicon-log-out"></span> <fmt:message key="sign_out" bundle="${ bundle }" /></a></li>
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#"><fmt:message key="language" bundle="${ bundle }" />
                    <span class="caret"></span></a>
                <ul class="dropdown-menu">
                    <li><a href="/FrontController?commandType=language&language=ru&currentPage=${ currentPage }">Русский</a></li>
                    <li><a href="/FrontController?commandType=language&language=en&currentPage=${ currentPage }">English</a></li>
                </ul>
            </li>
        </ul>
    </div>
</nav>

<div id="reserving" class="modal fade" role="dialog">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span class="cross">&times;</span></button>
                <h4 class="modal-title"><fmt:message key="reservation" bundle="${ bundle }" /></h4>
            </div>
            <div class="modal-body">
                <form action="/FrontController" class="form-horizontal" method="get">
                    <input type="hidden" name="commandType" value="reservation" />
                    <div class="form-group ">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <div class="input-group-addon">
                                    <i class="fa fa-calendar">
                                    </i>
                                </div>
                                <input type="text" name="dateRange" class="form-control" />
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-10">
                            <input class="btn btn-primary" type="submit" value="<fmt:message key="search" bundle="${ bundle }" />">
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<c:set var="pageAmount" value="${ requestScope.history.pageAmount }" scope="page" />
<c:set var="historyItems" value="${ requestScope.history.roomHistory }" scope="page" />

<div class="container">
    <div class="jumbotron">
        <h2 class="text-center"><fmt:message key="history" bundle="${ bundle }" /></h2>
    </div>
    <div class="offers">
        <table>
            <c:forEach var="elem" items="${ historyItems }" varStatus="status">
                <c:set var="tariff" value="${ elem.tariff }" />
                <c:set var="room" value="${ elem.room }" />
                <c:set var="reservedRoom" value="${ elem.reservedRoom }" />
                <c:set var="dateRange" value="${ reservedRoom.range }" />
                <fmt:formatDate value="${ dateRange.arrival }" pattern="yyyy-MM-dd" var="arrivalDate" />
                <fmt:formatDate value="${ dateRange.departure }" pattern="yyyy-MM-dd" var="departureDate" />
                <jsp:useBean id="now" class="java.util.Date" />
                <fmt:formatDate var="currentDate" value="${ now }" pattern="yyyy-MM-dd" />
                <c:choose>
                    <c:when test="${ reservedRoom.approval eq 'true' }">
                        <c:set value="approved" var="approval" />
                    </c:when>
                    <c:otherwise>
                        <c:set value="not_approved" var="approval" />
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${ reservedRoom.paid eq 'true' }">
                        <c:set value="paid" var="paid" />
                    </c:when>
                    <c:otherwise>
                        <c:set value="not_paid" var="paid" />
                    </c:otherwise>
                </c:choose>
                <tr>
                    <td>
                        <a href="/FrontController?commandType=room&roomId=${ room.id }" title="click"><img src="../${ room.image }/1.jpg" alt="room"></a>
                    </td>
                    <td>
                        <ul class="list-group">
                            <li class="list-group-item"><fmt:message key="transaction_id" bundle="${ bundle }" />: ${ reservedRoom.reservedRoomId }</li>
                            <li class="list-group-item"><h2>${ room.name }</h2></li>
                            <li class="list-group-item"><p>${ room.description }</p></li>
                            <li class="list-group-item"><fmt:message key="rooms_amount" bundle="${ bundle }" />: ${ room.roomsAmount }</li>
                            <li class="list-group-item"><fmt:message key="maximum_number_of_tenants" bundle="${ bundle }" />: ${ room.numberOfPersons }</li>
                            <li class="list-group-item"><fmt:message key="arrival_date" bundle="${ bundle }" />: ${ arrivalDate }</li>
                            <li class="list-group-item"><fmt:message key="date_of_departure" bundle="${ bundle }" />: ${ departureDate }</li>
                            <li class="list-group-item">
                                <p><fmt:message key="selected_tariff" bundle="${ bundle }" /></p>
                                <ul class="list-group">
                                    <li class="list-group-item"><fmt:message key="tariff_name" bundle="${ bundle }" />: ${ tariff.name }</li>
                                    <li class="list-group-item"><fmt:message key="tariff_description" bundle="${ bundle }" />: ${ tariff.description }</li>
                                    <li class="list-group-item"><fmt:message key="tariff_cost" bundle="${ bundle }" />: ${ tariff.cost }</li>
                                </ul>
                            </li>
                            <li class="list-group-item"><fmt:message key="total_cost" bundle="${ bundle }" />: ${ reservedRoom.totalCost }</li>
                            <li class="list-group-item"><fmt:message key="administrator_check" bundle="${ bundle }" />: <fmt:message key="${ approval }" bundle="${ bundle }" /> </li>
                            <li class="list-group-item"><fmt:message key="payment_state" bundle="${ bundle }" />: <fmt:message key="${ paid }" bundle="${ bundle }" /></li>
                            <c:if test="${ reservedRoom.canceled eq 'true' }">
                                <li class="list-group-item danger"><fmt:message key="canceled" bundle="${ bundle }" /></li>
                            </c:if>
                            <c:if test="${ currentDate lt departureDate and reservedRoom.canceled eq 'false' }">
                                <li class="list-group-item pull-right">
                                    <form action="/FrontController" method="post">
                                        <input type="hidden" name="commandType" value="return_the_room" />
                                        <input type="hidden" name="currentPage" value="${ currentPage }" />
                                        <input type="hidden" name="transaction_id" value="${ reservedRoom.reservedRoomId }" />
                                        <input type="submit" value="<fmt:message key="return" bundle="${ bundle }" />" class="btn btn-warning" />
                                    </form>
                                </li>
                                <c:if test="${ reservedRoom.paid eq 'false' }">
                                    <li class="list-group-item pull-right">
                                        <form action="/FrontController" method="post">
                                            <input type="hidden" name="commandType" value="pay_room" />
                                            <input type="hidden" name="transaction_id" value="${ reservedRoom.reservedRoomId }" />
                                            <input type="hidden" name="currentPage" value="${ currentPage }" />
                                            <input type="submit" value="<fmt:message key="pay" bundle="${ bundle }" />" class="btn btn-warning" />
                                        </form>
                                    </li>
                                </c:if>
                            </c:if>
                        </ul>
                    </td>
                </tr>
            </c:forEach>
            <tfoot>
            <tr align="center">
                <td colspan="2">
                    <ul class="pagination" id="pagination">

                    </ul>
                </td>
            </tr>
            </tfoot>
        </table>
    </div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/js/bootstrap-datepicker.min.js"></script>
<script type="text/javascript" src="https://formden.com/static/cdn/formden.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>
<script type="text/javascript" src="../../js/functions.js"></script>

<script type="text/javascript">
    executeHistoryPagination(${ pageNumber }, ${ pageAmount });
</script>

</body>
</html>
