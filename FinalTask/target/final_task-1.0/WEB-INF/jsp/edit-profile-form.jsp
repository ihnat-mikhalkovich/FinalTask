<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
    <fmt:setLocale value="${ sessionScope.language }" />
    <fmt:setBundle basename="data" var="bundle"/>
  <title><fmt:message key="editing_a_profile" bundle="${ bundle }" /></title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" type="text/css" href="../../css/style.css">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

    <link rel="stylesheet" href="https://formden.com/static/cdn/font-awesome/4.4.0/css/font-awesome.min.css" />

    <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" />

</head>
<body>

<c:set var="currentPage" value="/jsp/moderator/edit-profile-form.jsp" />
<nav class="navbar navbar-inverse">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="../index.jsp">MyHotel</a>
        </div>
        <ul class="nav navbar-nav">
            <li><a href="../../index.jsp"><fmt:message key="home" bundle="${ bundle }" /></a></li>
            <li><a href="#reserving" data-toggle="modal"><fmt:message key="reservation" bundle="${ bundle }" /></a></li>
            <li><a href="/FrontController?commandType=offers"><fmt:message key="rooms_and_prices" bundle="${ bundle }" /></a></li>
            <li><a href="../../jsp/contacts.jsp"><fmt:message key="contacts" bundle="${ bundle }" /></a></li>
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

<c:set var="user" value="${ requestScope.user }" />
<fmt:formatDate value="${ user.registrationDate }" pattern="yyyy-MM-dd" var="registrationDate" />
<div class="container">
  <div class="jumbotron">
    <p class="text-center"><fmt:message key="editing_a_profile" bundle="${ bundle }" /></p>
  </div>
  <div class="jumbotron">
  <table class="table table-condensed table-hover">
    <form action="/FrontController" method="post" name="editProfileForm" onsubmit="return validateEditProfileForm()">
      <tbody>
        <input type="hidden" name="commandType" value="edit_profile_save" />
        <input type="hidden" name="discount" value="${ user.discount }" />
        <input type="hidden" name="balance" value="${ user.balance }" />
        <input type="hidden" name="registration_date" value="${ registrationDate }" />

        <tr>
            <td class="col-sm-3"><label for="firstName" id="firstNameLabel"><fmt:message key="first_name" bundle="${ bundle }" />:</label>
        </td>
          <td>
            <input type="text" value="${ user.firstName }" class="form-control" name="firstName" id="firstName" />
          </td>
        </tr>
        <tr>
          <td><label for="lastName" id="lastNameLabel"><fmt:message key="last_name" bundle="${ bundle }" />:</label></td>
          <td>
            <input type="text" value="${ user.lastName }" class="form-control" name="lastName" id="lastName" />
          </td>
        </tr>
        <tr>
            <td><label for="tel" id="telLabel"><fmt:message key="phone_number" bundle="${ bundle }" />:</label></td>
          <td>
            <input type="text" value="${ user.telephoneNumber }" class="form-control" name="tel" id="tel" />
          </td>
        </tr>
        <tr>
            <td><label for="email" id="emailLabel"><fmt:message key="email" bundle="${ bundle }" />:</label></td>
          <td>
            <input type="text" value="${ user.email }" class="form-control" name="email" id="email" />
          </td>
        </tr>
      </tbody>
    <tfoot>
      <tr>
        <td colspan="2">
          <div class="pull-right">
            <input type="submit" value="<fmt:message key="save" bundle="${ bundle }" />" class="btn btn-success" />
          </div>
        </td>
      </tr>
    </tfoot>
    </form>
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
    function validateEditProfileForm() {
        var emptyFieldString = "<fmt:message key="empty_field_answer" bundle="${ bundle }" />";
        var wrongTelString = "<fmt:message key="telephone_number_invalid" bundle="${ bundle }" />";
        var wrongEmailString = "<fmt:message key="email_invalid" bundle="${ bundle }" />";
        return validateEditProfile(emptyFieldString, wrongTelString, wrongEmailString);
    };
</script>

</body>
</html>
