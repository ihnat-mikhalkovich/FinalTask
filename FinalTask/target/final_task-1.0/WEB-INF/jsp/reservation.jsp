<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
    <fmt:setLocale value="${ sessionScope.language }" />
    <fmt:setBundle basename="data" var="bundle"/>
    <title><fmt:message key="reservation" bundle="${ bundle }" /></title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" type="text/css" href="css/style.css">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

    <link rel="stylesheet" href="https://formden.com/static/cdn/font-awesome/4.4.0/css/font-awesome.min.css" />

    <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" />

</head>
<body>

<c:set var="arrivalDate" value="${ requestScope.arrival }" scope="page" />
<c:set var="departureDate" value="${ requestScope.departure }" scope="page" />

<nav class="navbar navbar-inverse">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="../../index.jsp">MyHotel</a>
        </div>
        <ul class="nav navbar-nav">
            <li><a href="index.jsp"><fmt:message key="home" bundle="${ bundle }" /></a></li>
            <li class="active"><a href="#reserving" data-toggle="modal"><fmt:message key="reservation" bundle="${ bundle }" /></a></li>
            <li><a href="/FrontController?commandType=offers"><fmt:message key="rooms_and_prices" bundle="${ bundle }" /></a></li>
            <li><a href="../../jsp/contacts.jsp"><fmt:message key="contacts" bundle="${ bundle }" /></a></li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <c:set var="currentPage" value="/FrontController?commandType=reservation&dateRange=${ requestScope.dateRange }" scope="page" />
            <c:set var="currentPageUrl" value="%2FFrontController%3FcommandType%3Dreservation%26dateRange%3D${ requestScope.arrivalDate }%2B-%2B${ requestScope.departureDate }" scope="page" />
            <c:choose>
                <c:when test="${ sessionScope.userId != null }">
                    <li><a href="/FrontController?commandType=profile"><span class="glyphicon glyphicon-bookmark"></span> <fmt:message key="profile" bundle="${ bundle }" /></a></li>
                    <li><a href="/FrontController?commandType=logout&currentPage=${ currentPageUrl }">
                        <span class="glyphicon glyphicon-log-out"></span>
                        <fmt:message key="sign_out" bundle="${ bundle }" /></a>
                    </li>
            </c:when>
            <c:otherwise>
                <li><a href="#signUp" data-toggle="modal"><span class="glyphicon glyphicon-user"></span> <fmt:message key="sign_up" bundle="${ bundle }" /></a></li>
                <li><a href="#signIn" data-toggle="modal"><span class="glyphicon glyphicon-log-in"></span> <fmt:message key="sign_in" bundle="${ bundle }" /></a></li>
            </c:otherwise>
        </c:choose>
        <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#"><fmt:message key="language" bundle="${ bundle }" />
                <span class="caret"></span></a>
            <ul class="dropdown-menu">
                <li><a href="/FrontController?commandType=language&language=ru&currentPage=${ currentPageUrl }">Русский</a></li>
                <li><a href="/FrontController?commandType=language&language=en&currentPage=${ currentPageUrl }">English</a></li>
            </ul>
        </li>
    </ul>
</div>
</nav>

<div id="signUp" class="modal fade" role="dialog">
<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"><span class="cross">&times;</span></button>
            <h4 class="modal-title"><fmt:message key="sign_up" bundle="${ bundle }" /></h4>
        </div>
        <form action="/FrontController" method="post" name="signUpForm" onsubmit="return validateSignUp()">
            <div class="modal-body">
                <input type="hidden" name="commandType" value="registration" />
                <input type="hidden" name="currentPage" value="${ currentPage }" />

                <label for="firstName" id="firstNameLabel"><fmt:message key="first_name" bundle="${ bundle }" />:</label>
                <input type="text" name="firstName" id="firstName" class="form-control">

                <label for="lastName" id="lastNameLabel"><fmt:message key="last_name" bundle="${ bundle }" />:</label>
                <input type="text" name="lastName" id="lastName" class="form-control">

                <label for="password" id="passwordLabel"><fmt:message key="password" bundle="${ bundle }" />:</label>
                <input type="password" name="password" id="password" class="form-control">
                <p class="help-block"><small class="text-info"><fmt:message key="password_description" bundle="${ bundle }" /></small></p>

                <label for="repeatedPassword" id="repeatedPasswordLabel"><fmt:message key="password_verification" bundle="${ bundle }" />:</label>
                <input type="password" id="repeatedPassword" class="form-control">

                <label for="tel" id="telLabel"><fmt:message key="phone_number" bundle="${ bundle }" />:</label>
                <input type="tel" name="tel" pattern="+[0-9]{12}" id="tel" class="form-control">
                <p class="help-block"><small class="text-info">+375291234567</small></p>


                <label for="email" id="emailLabel"><fmt:message key="email" bundle="${ bundle }" />:</label>
                <input type="email" name="email" id="email" class="form-control">

                <label for="agreement" id="agreementLabel"><fmt:message key="agreement" bundle="${ bundle }" />:</label>
                <input type="checkbox" id="agreement">
            </div>
            <div class="modal-footer">
                <input type="submit" value="<fmt:message key="sign_up" bundle="${ bundle }" />" class="btn btn-primary" />
            </div>
        </form>
    </div>
</div>
</div>

<div id="signIn" class="modal fade" role="dialog">
<div class="modal-dialog modal-sm">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"><span class="cross">&times;</span></button>
            <h4 class="modal-title"><fmt:message key="sign_in" bundle="${ bundle }" /></h4>
        </div>
        <div class="modal-body">
            <form action="/FrontController" method="post" name="signInForm" onsubmit="return validateSignIn()">
                <input type="hidden" name="commandType" value="login" />
                <input type="hidden" name="currentPage" value="${ currentPage }" />
                <label for="email_signIn" id="email_signIn_label"><fmt:message key="email" bundle="${ bundle }" />:</label>
                <input type="email" name="email" id="email_signIn" class="form-control">
                <label for="password_signIn" id="password_signIn_label"><fmt:message key="password" bundle="${ bundle }" />:</label>
                <input type="password" name="password" id="password_signIn" class="form-control">
                <div class="modal-footer">
                    <input type="submit" value="<fmt:message key="sign_in" bundle="${ bundle }" />" class="btn btn-primary" />
                </div>
            </form>
        </div>
    </div>
</div>
</div>

<c:choose>
    <c:when test="${ sessionScope.discount eq null }">
        <c:set var="pageDiscount" value="${ 0 }" scope="page" />
    </c:when>
    <c:otherwise>
        <c:set var="pageDiscount" value="${ sessionScope.discount }" scope="page" />
    </c:otherwise>
</c:choose>

<div class="container">
<div class="jumbotron">
    <p class="text-center"><fmt:message key="reservation" bundle="${ bundle }" /></p>
</div>
<div class="offers">
    <table>
        <c:forEach var="elem" items="${ requestScope.rooms }" varStatus="status">
            <tr>
                <td>
                    <a href="/FrontController?commandType=room&roomId=${ elem.id }" title="click"><img src="../${ elem.image }/1.jpg" alt="room" /></a>
                </td>
                <td>
                    <ul class="list-group">
                        <li class="list-group-item"><h4><c:out value="${ elem.name }" /></h4></li>
                        <li class="list-group-item"><p><c:out value="${ elem.description }" /></p></li>
                        <li class="list-group-item"><fmt:message key="maximum_number_of_tenants" bundle="${ bundle }" />: ${ elem.numberOfPersons }</li>
                        <li class="list-group-item">
                            <p><fmt:message key="tariffs" bundle="${ bundle }" /></p>
                            <ul class="list-group">
                                <c:forEach var="tariff" items="${ requestScope.tariffs }" varStatus="status">
                                    <li class="list-group-item">
                                        <p>${ tariff.name }</p>
                                        <p>${ tariff.description }</p>
                                        <p><fmt:message key="tariff_cost" bundle="${ bundle }" />: ${ tariff.cost }</p>
                                        <form action="/FrontController" method="post">
                                            <input type="hidden" name="commandType" value="take_room" />
                                            <input type="hidden" value="${ elem.id }" name="roomId" />
                                            <input type="hidden" value="${ tariff.id }" name="tariffId" />
                                            <input type="hidden" value="${ requestScope.arrivalDate }" name="arrivalDate" />
                                            <input type="hidden" value="${ requestScope.departureDate }" name="departureDate" />
                                            <input type="range" min="1" max="${ elem.roomsAmount }" value="1" class="slider" id="roomsAmountSlide-${ elem.id }-${ tariff.id }" name="roomsAmount">
                                            <p><fmt:message key="number_of_rooms" bundle="${ bundle }" />: <span id="roomsAmount-${ elem.id }-${ tariff.id }"></span></p>
                                            <p><fmt:message key="total_cost" bundle="${ bundle }" />: <span id="cost-${ elem.id }-${ tariff.id }"></span></p>
                                            <p><fmt:message key="total_cost_with_discount" bundle="${ bundle }" /> - ${ pageDiscount }%: <span id="discount-${ elem.id }-${ tariff.id }"></span></p>
                                            <c:if test="${ sessionScope.user_role != 'banned' }">
                                                <c:choose>
                                                    <c:when test="${ sessionScope.user_role != 'quest' and not empty sessionScope.user_role }">
                                                        <input type="submit" value="<fmt:message key="make_a_reservation" bundle="${ bundle }" />" class="btn btn-warning" />
                                                    </c:when>
                                                    <c:otherwise>
                                                        <a href="#signIn" data-toggle="modal" class="btn btn-warning"><fmt:message key="make_a_reservation" bundle="${ bundle }" /></a>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:if>
                                        </form>
                                    </li>
                                </c:forEach>
                            </ul>
                        </li>
                    </ul>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</div>

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

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/js/bootstrap-datepicker.min.js"></script>
<script type="text/javascript" src="https://formden.com/static/cdn/formden.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>
<script type="text/javascript" src="../../js/functions.js"></script>

<c:forEach var="elem" items="${ requestScope.rooms }" varStatus="status">
    <c:forEach var="tariff" items="${ requestScope.tariffs }" varStatus="status">
<script type="text/javascript">
    var arrivalDate = new Date('${ arrivalDate }');
    var departureDate = new Date('${ departureDate }');
    var difference = Math.ceil(Math.abs(departureDate.getTime() - arrivalDate.getTime()) / (1000 * 3600 * 24));

    var slider${ elem.id }${ tariff.id } = document.getElementById("roomsAmountSlide-${ elem.id }-${ tariff.id }");
    var outputRoomsAmount${ elem.id }${ tariff.id } = document.getElementById("roomsAmount-${ elem.id }-${ tariff.id }");
    outputRoomsAmount${ elem.id }${ tariff.id }.innerHTML = slider${ elem.id }${ tariff.id }.value;
    var outputCost${ elem.id }${ tariff.id } = document.getElementById("cost-${ elem.id }-${ tariff.id }");
    outputCost${ elem.id }${ tariff.id }.innerHTML = Math.round((slider${ elem.id }${ tariff.id }.value * (${ elem.cost } + ${ tariff.cost }))*100 * difference)/100;
    var outputCostWithDiscount${ elem.id }${ tariff.id } = document.getElementById("discount-${ elem.id }-${ tariff.id }");
    outputCostWithDiscount${ elem.id }${ tariff.id }.innerHTML = Math.round((slider${ elem.id }${ tariff.id }.value * (${ elem.cost } + ${ tariff.cost }))*100*(1 - ${ pageDiscount }/100) * difference)/100;

    slider${ elem.id }${ tariff.id }.oninput = function() {
        outputRoomsAmount${ elem.id }${ tariff.id }.innerHTML = this.value;
        outputCost${ elem.id }${ tariff.id }.innerHTML = Math.round((this.value * (${ elem.cost } + ${ tariff.cost }))*100 * difference)/100;
        outputCostWithDiscount${ elem.id }${ tariff.id }.innerHTML = Math.round((this.value * (${ elem.cost } + ${ tariff.cost }))*100*(1 - ${ pageDiscount }/100) * difference)/100;
    }
</script>
    </c:forEach>
</c:forEach>

<script type="text/javascript">
function validateSignUp() {
    var emptyFieldString = "<fmt:message key="empty_field_answer" bundle="${ bundle }" />";
    var shortPasswordString = "<fmt:message key="password_invalid" bundle="${ bundle }" />";
    var wrongRepeatedPasswordString = "<fmt:message key="repeated_password_invalid" bundle="${ bundle }" />";
    var wrongTelString = "<fmt:message key="telephone_number_invalid" bundle="${ bundle }" />";
    var wrongEmailString = "<fmt:message key="email_invalid" bundle="${ bundle }" />";
    var formValid = validateSignUpForm(emptyFieldString, shortPasswordString, wrongRepeatedPasswordString, wrongTelString, wrongEmailString);
    if (formValid) {
        document.getElementById("password").value = MD5(document.forms["signUpForm"]["password"].value);
    } else {
        document.getElementById("password").value = "";
        document.getElementById("repeatedPassword").value = "";
    }
    return formValid;
};
function validateSignIn() {
    var wrongEmailString = "<fmt:message key="email_invalid" bundle="${ bundle }" />";
    var shortPasswordString = "<fmt:message key="password_invalid" bundle="${ bundle }" />";
    var formValid = validateSignInForm(wrongEmailString, shortPasswordString);
    if (formValid) {
        document.getElementById("password_signIn").value = MD5(document.forms["signInForm"]["password"].value);
    } else {
        document.getElementById("password_signIn").value = "";
    }
    return formValid;
};
</script>

</body>
</html>
