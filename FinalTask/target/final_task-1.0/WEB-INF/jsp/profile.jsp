<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
    <fmt:setLocale value="${ sessionScope.language }" />
    <fmt:setBundle basename="data" var="bundle"/>
  <title><fmt:message key="profile" bundle="${ bundle }" /></title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" type="text/css" href="../../css/style.css">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

    <link rel="stylesheet" href="https://formden.com/static/cdn/font-awesome/4.4.0/css/font-awesome.min.css" />

    <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" />

</head>
<body>

<c:set var="currentPage" value="/FrontController?commandType=profile" />
<nav class="navbar navbar-inverse">
  <div class="container">
    <div class="navbar-header">
      <a class="navbar-brand" href="../../index.jsp">MyHotel</a>
    </div>
    <ul class="nav navbar-nav">
      <li><a href="../../index.jsp"><fmt:message key="home" bundle="${ bundle }" /></a></li>
      <li><a href="#reserving" data-toggle="modal"><fmt:message key="reservation" bundle="${ bundle }" /></a></li>
      <li><a href="/FrontController?commandType=offers"><fmt:message key="rooms_and_prices" bundle="${ bundle }" /></a></li>
      <li><a href="../../jsp/contacts.jsp"><fmt:message key="contacts" bundle="${ bundle }" /></a></li>
    </ul>
    <ul class="nav navbar-nav navbar-right">
      <li class="active"><a href="${ currentPage }"><span class="glyphicon glyphicon-bookmark"></span> <fmt:message key="profile" bundle="${ bundle }" /></a></li>
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

<div class="container">
    <ul>
      <li class="profile-item">
        <form action="/FrontController" method="get">
          <input type="hidden" name="commandType" value="history" />
          <input type="hidden" name="page" value="1" />
          <input type="submit" value="<fmt:message key="history" bundle="${ bundle }" />" class="btn btn-primary" />
        </form>
      </li>
        <li class="profile-item">
            <a href="../../jsp/user/change-password-form.jsp" class="btn btn-primary"><fmt:message key="change_password" bundle="${ bundle }" /></a>
        </li>
      <c:if test="${ sessionScope.user_role == 'administrator' }">
          <li class="profile-item">
            <form action="/FrontController" method="get">
              <input type="hidden" name="commandType" value="users" />
              <input type="hidden" name="page" value="1" />
              <input type="hidden" name="orderBy" value="id" />
              <input type="hidden" name="direction" value="asc" />
              <input type="submit" value="<fmt:message key="users" bundle="${ bundle }" />" class="btn btn-primary" />
            </form>
          </li>
          <li class="profile-item">
              <form action="/FrontController" method="get">
                  <input type="hidden" name="commandType" value="orders" />
                  <input type="hidden" name="page" value="1" />
                  <input type="submit" value="<fmt:message key="orders" bundle="${ bundle }" />" class="btn btn-primary" />
              </form>
          </li>
      </c:if>
        <c:if test="${ sessionScope.user_role == 'administrator' or sessionScope.user_role == 'moderator' }">
            <li class="profile-item">
                <form action="/FrontController" method="get">
                    <input type="hidden" name="commandType" value="tariffs" />
                    <input type="submit" value="<fmt:message key="tariffs" bundle="${ bundle }" />" class="btn btn-primary" />
                </form>
            </li>
        </c:if>
    </ul>
  <c:set var="user" value="${ requestScope.user }" />
  <table class="table table-condensed table-hover">
      <fmt:formatDate value="${ user.registrationDate }" pattern="yyyy-MM-dd" var="registrationDate" />
    <tbody>
        <tr>
            <td class="col-sm-3 info"><fmt:message key="registration_date" bundle="${ bundle }" /></td>
            <td class="info">${ registrationDate }</td>
        </tr>
        <tr>
            <td class="info"><fmt:message key="balance" bundle="${ bundle }" /></td>
            <td class="info">${ user.balance }</td>
        </tr>
        <tr>
            <td colspan="2">
                <div class="pull-right">
                <a href="../../jsp/user/raise-balance.jsp" class="btn btn-success"><fmt:message key="replenish_balance" bundle="${ bundle }" /></a>
                </div>
            </td>
        </tr>
        </tr>
        <tr>
            <td class="info"><fmt:message key="discount" bundle="${ bundle }" /></td>
            <td class="info">${ user.discount }%</td>
        </tr>
      <tr>
        <td><fmt:message key="first_name" bundle="${ bundle }" /></td>
        <td>${ user.firstName }</td>
      </tr>
      <tr>
        <td><fmt:message key="last_name" bundle="${ bundle }" /></td>
        <td>${ user.lastName }</td>
      </tr>
      <tr>
        <td><fmt:message key="phone_number" bundle="${ bundle }" /></td>
        <td>${ user.telephoneNumber }</td>
      </tr>
      <tr>
        <td><fmt:message key="email" bundle="${ bundle }" /></td>
        <td>${ user.email }</td>
      </tr>
      <tr>
          <td><fmt:message key="role" bundle="${ bundle }" /></td>
          <td>${ user.role }</td>
      </tr>
    </tbody>
    <tfoot>
      <tr>
        <td colspan="2">
          <div class="pull-right">
              <form action="/FrontController" method="post">
                  <input type="hidden" name="commandType" value="edit_profile_form" />
                  <input type="submit" value="<fmt:message key="edit" bundle="${ bundle }" />" class="btn btn-warning" />
              </form>
          </div>
        </td>
      </tr>
    </tfoot>
  </table>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/js/bootstrap-datepicker.min.js"></script>
<script type="text/javascript" src="https://formden.com/static/cdn/formden.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>
<script type="text/javascript" src="../../js/functions.js"></script>

</body>
</html>
