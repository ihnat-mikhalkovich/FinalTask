<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
    <fmt:setLocale value="${ sessionScope.language }" />
    <fmt:setBundle basename="data" var="bundle"/>
  <title><fmt:message key="home" bundle="${ bundle }" /></title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="css/style.css">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/css/bootstrap-datepicker3.css"/>

<link rel="stylesheet" href="https://formden.com/static/cdn/bootstrap-iso.css" />

<link rel="stylesheet" href="https://formden.com/static/cdn/font-awesome/4.4.0/css/font-awesome.min.css" />

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/css/bootstrap-datepicker3.css"/>

<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/js/bootstrap-datepicker.min.js"></script>
<script type="text/javascript" src="https://formden.com/static/cdn/formden.js"></script>

</head>
<body>

<nav class="navbar navbar-inverse">
  <div class="container">
    <div class="navbar-header">
      <a class="navbar-brand" href="index.jsp">MyHotel</a>
    </div>
    <ul class="nav navbar-nav">
      <li class="active"><a href="index.jsp"><fmt:message key="home" bundle="${ bundle }" /></a></li>
      <li><a href="#reserving" data-toggle="modal"><fmt:message key="reservation" bundle="${ bundle }" /></a></li>
      <li><a href="/FontController?commandType=offers"><fmt:message key="rooms_and_prices" bundle="${ bundle }" /></a></li>
      <li><a href="jsp/contacts.jsp"><fmt:message key="contacts" bundle="${ bundle }" /></a></li>
    </ul>
    <ul class="nav navbar-nav navbar-right">
        <c:choose>
            <c:when test="${ sessionScope.userId != null }">
                <li><a href="jsp/profile.jsp"><span class="glyphicon glyphicon-bookmark"></span><fmt:message key="profile" bundle="${ bundle }" /></a></li>
                <li><a href="/FontController?commandType=logout&currentPage=${ pageContext.request.requestURI }">
                    <span class="glyphicon glyphicon-log-out"></span>
                    <fmt:message key="sing_out" bundle="${ bundle }" /></a>
                </li>
            </c:when>
            <c:otherwise>
                <li><a href="#singUp" data-toggle="modal"><span class="glyphicon glyphicon-user"></span><fmt:message key="sing_up" bundle="${ bundle }" /></a></li>
                <li><a href="#singIn" data-toggle="modal"><span class="glyphicon glyphicon-log-in"></span><fmt:message key="sing_in" bundle="${ bundle }" /></a></li>
            </c:otherwise>
        </c:choose>
        <li class="dropdown">
        <a class="dropdown-toggle" data-toggle="dropdown" href="#"><fmt:message key="language" bundle="${ bundle }" />
        <span class="caret"></span></a>
        <ul class="dropdown-menu">
          <li><a href="/FontController?commandType=language&language=ru&currentPage=${ pageContext.request.requestURI }">Русский</a></li>
          <li><a href="/FontController?commandType=language&language=en&currentPage=${ pageContext.request.requestURI }">English</a></li>
        </ul>
      </li>
    </ul>
  </div>
</nav>

<div id="singUp" class="modal fade" role="dialog">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span class="cross">&times;</span></button>
        <h4 class="modal-title"><fmt:message key="sing_up" bundle="${ bundle }" /></h4>
      </div>
      <div class="modal-body lead">
        <form action="/FontController" method="post">
          <input type="hidden" name="commandType" value="registration" />
          <div class="input-group sing-up-text">
            <lable for="firstName"><fmt:message key="first_name" bundle="${ bundle }" />:</lable>
            <input type="text" name="firstName" id="firstName" class="form-control">
          </div>
          <div class="input-group sing-up-text">
            <lable for="lastName"><fmt:message key="last_name" bundle="${ bundle }" />:</lable>
            <input type="text" name="lastName" id="lastName" class="form-control">
          </div>
          <div class="input-group sing-up-text">
            <lable for="password"><fmt:message key="password" bundle="${ bundle }" />:</lable>
            <input type="password" name="password" id="password" class="form-control">
          </div>
          <div class="input-group sing-up-text">
            <lable for="repeatedPassword"><fmt:message key="password_verification" bundle="${ bundle }" />:</lable>
            <input type="password" id="repeatedPassword" class="form-control">
          </div>
          <div class="input-group sing-up-text">
            <lable for="tel"><fmt:message key="phone_number" bundle="${ bundle }" />:</lable>
            <input type="tel" name="tel" pattern="+[0-9]{12}" id="tel" class="form-control">
            <p class="help-block"><small class="text-info">+375291234567</small></p>
          </div>
          <div class="input-group sing-up-text">
            <lable for="tel"><fmt:message key="email" bundle="${ bundle }" />:</lable>
            <input type="email" name="email" id="email" class="form-control">
          </div>
          <div class="input-group sing-up-text">
            <label for="agreement"><fmt:message key="i_agree_with_the_terms_of_the_license_agreement" bundle="${ bundle }" />:</label>
            <input type="checkbox" id="agreement">
          </div>
          <div>
            <input type="submit" value="<fmt:message key="sing_up" bundle="${ bundle }" />" class="btn btn-primary" />
          </div>
        </form>
      </div>
    </div>
  </div>
</div>

<div id="singIn" class="modal fade" role="dialog">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span class="cross">&times;</span></button>
        <h4 class="modal-title"><fmt:message key="sing_in" bundle="${ bundle }" /></h4>
      </div>
      <div class="modal-body lead">
        <form action="/FontController" method="post">
          <input type="hidden" name="commandType" value="login" />
          <input type="hidden" name="currentPage" value="/${ requestScope.currentPage }" />
          <div class="input-group sing-up-text">
            <lable for="tel"><fmt:message key="email" bundle="${ bundle }" />:</lable>
            <input type="email" name="email" id="email" class="form-control">
          </div>
          <div class="input-group sing-up-text">
            <lable for="password"><fmt:message key="password" bundle="${ bundle }" />:</lable>
            <input type="password" name="password" id="password" class="form-control">
          </div>
          <br>
          <div class="input-group">
            <input type="submit" value="<fmt:message key="sing_in" bundle="${ bundle }" />" class="btn btn-primary" />
          </div>
        </form>
      </div>
    </div>
  </div>
</div>

<div id="reserving" class="modal fade" role="dialog">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span class="cross">&times;</span></button>
        <h4 class="modal-title"><fmt:message key="reservation" bundle="${ bundle }" /></h4>
      </div>
      <div class="modal-body lead">
        <div class="bootstrap-iso">
         <div class="container">
          <div class="row">
           <div class="col-md-5 col-sm-8 col-xs-12">
            <form action="/FontController" class="form-horizontal" method="post">
             <div class="form-group ">
              <label class="control-label col-sm-2 requiredField" for="arrivalDate">
                  <fmt:message key="arrival_date" bundle="${ bundle }" />:
               <span class="asteriskField">
                *
               </span>
              </label>
              <br>
              <div class="col-sm-10">
               <div class="input-group">
                <div class="input-group-addon">
                 <i class="fa fa-calendar">
                 </i>
                </div>
                <input class="form-control" id="arrivalDate" name="arrivalDate" placeholder="MM/DD/YYYY" type="text"/>
               </div>
              </div>
             </div>
             <div class="form-group ">
              <label class="control-label col-sm-2 requiredField" for="departureDate">
                  <fmt:message key="date_of_departure" bundle="${ bundle }" />:
               <span class="asteriskField">
                *
               </span>
              </label>
              <div class="col-sm-10">
               <div class="input-group">
                <div class="input-group-addon">
                 <i class="fa fa-calendar">
                 </i>
                </div>
                <input class="form-control" id="departureDate" name="departureDate" placeholder="MM/DD/YYYY" type="text"/>
               </div>
              </div>
             </div>
             <div class="form-group">
              <div class="col-sm-10 col-sm-offset-2">
               <!-- <input name="_honey" style="display:none" type="text"/> -->
               <input class="btn btn-primary " name="submit" type="submit" value="<fmt:message key="search" bundle="${ bundle }" />">
              </div>
             </div>
            </form>
           </div>
          </div>
         </div>
        </div>
      </div>
    </div>
  </div>
</div>

<div class="container">
  <img src="image/dom.jpg" alt="MyHotel" class="img-rounded img-responsive">
</div>

<div class="container">
  <div class="row">
    <div class="col-sm-4">
      <h3>Column 1</h3>
      <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit...</p>
      <p>Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris...</p>
    </div>
    <div class="col-sm-4">
      <h3>Column 2</h3>
      <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit...</p>
      <p>Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris...</p>
    </div>
    <div class="col-sm-4">
      <h3>Column 3</h3>
      <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit...</p>
      <p>Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris...</p>
    </div>
  </div>
</div>

<script>
  $(document).ready(function(){
    var arrival_date_input=$('input[name="arrivalDate"]');
    var departure_date_input=$('input[name="departureDate"]');
    var container=$('.bootstrap-iso form').length>0 ? $('.bootstrap-iso form').parent() : "body";
    arrival_date_input.datepicker({
      format: 'mm/dd/yyyy',
      container: container,
      todayHighlight: true,
      autoclose: true,
    })
    departure_date_input.datepicker({
      format: 'mm/dd/yyyy',
      container: container,
      todayHighlight: true,
      autoclose: true,
    })
  })
</script>


</body>
</html>
