<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
    <fmt:setLocale value="${ sessionScope.language }" />
    <fmt:setBundle basename="data" var="bundle"/>
    <title><fmt:message key="view_the_room" bundle="${ bundle }" /></title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" type="text/css" href="css/style.css">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

    <link rel="stylesheet" href="https://formden.com/static/cdn/font-awesome/4.4.0/css/font-awesome.min.css" />

    <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" />

</head>
<body>

<nav class="navbar navbar-inverse">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="../../index.jsp">MyHotel</a>
        </div>
        <ul class="nav navbar-nav">
            <li><a href="index.jsp"><fmt:message key="home" bundle="${ bundle }" /></a></li>
            <li><a href="#reserving" data-toggle="modal"><fmt:message key="reservation" bundle="${ bundle }" /></a></li>
            <li><a href="/FrontController?commandType=offers"><fmt:message key="rooms_and_prices" bundle="${ bundle }" /></a></li>
            <li><a href="../../jsp/contacts.jsp"><fmt:message key="contacts" bundle="${ bundle }" /></a></li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <c:set var="currentPage" value="/FrontController?commandType=room&roomId=${ requestScope.room.id }" scope="page" />
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
                    <li><a href="/FrontController?commandType=language&language=ru&currentPage=${ currentPage }">Русский</a></li>
                    <li><a href="/FrontController?commandType=language&language=en&currentPage=${ currentPage }">English</a></li>
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

<c:set var="room" value="${ requestScope.room }" scope="page" />
<c:set var="image" value="../${ room.image }/1.jpg" scope="page" />

<div class="container center-button">
    <div id="my_slider">
        <img src="${ image }" alt="room" id="image">
    </div>
    <button id="previous-button" type="button" class="btn" onclick="slidePrevious('../${ room.image }/')" style="display:none;"><fmt:message key="previous" bundle="${ bundle }" /></button>
    <button id="next-button" type="button" class="btn" onclick="slideNext('../${ room.image }/')"><fmt:message key="next" bundle="${ bundle }" /></button>
    <div>
        <h2>${ room.name }</h2>
        <p>${ room.description }</p>
        <ul class="list-group">
            <li class="list-group-item"><fmt:message key="cost" bundle="${ bundle }" />: ${ room.cost }</li>
            <li class="list-group-item"><fmt:message key="maximum_number_of_tenants" bundle="${ bundle }" />: ${ room.numberOfPersons }</li>
        </ul>
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

<p id="imgsrc" style="display: none;">1</p>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/js/bootstrap-datepicker.min.js"></script>
<script type="text/javascript" src="https://formden.com/static/cdn/formden.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>
<script type="text/javascript" src="../../js/functions.js"></script>

</body>
</html>

