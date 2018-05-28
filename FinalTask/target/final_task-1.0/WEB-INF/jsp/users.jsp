<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
    <fmt:setLocale value="${ sessionScope.language }" />
    <fmt:setBundle basename="data" var="bundle"/>
  <title><fmt:message key="viewing_users" bundle="${ bundle }" /></title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" type="text/css" href="../../css/style.css">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

    <link rel="stylesheet" href="https://formden.com/static/cdn/font-awesome/4.4.0/css/font-awesome.min.css" />

    <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" />

</head>
<body>


<c:set var="pageNumber" value="${ requestScope.userPage.currentPage }" scope="page" />
<c:set var="orderBy" value="${ requestScope.userPage.orderBy.orderBy }" scope="page" />
<c:set var="direction" value="${ requestScope.userPage.orderBy.direction }" scope="page" />
<c:set var="currentPage" value="/FrontController?commandType=users&page=${ pageNumber }&orderBy=${ orderBy }&direction=${ direction }" />

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

<div class="container">
  <div class="jumbotron">
    <h2 class="text-center"><fmt:message key="viewing_users" bundle="${ bundle }" /></h2>
  </div>
    <table class="table table-condensed table-hover">
      <thead>
        <tr>
          <th>
              <form action="/FrontController" method="get">
                  <input type="hidden" name="commandType" value="users" />
                  <input type="hidden" name="page" value="${ pageNumber }" />
                  <input type="hidden" name="orderBy" value="id" />
                  <c:choose>
                      <c:when test="${ orderBy.name == 'id' }">
                          <c:choose>
                              <c:when test="${ direction.name == 'asc'}">
                                  <input type="hidden" name="direction" value="desc" />
                              </c:when>
                              <c:otherwise>
                                  <input type="hidden" name="direction" value="asc" />
                              </c:otherwise>
                          </c:choose>
                      </c:when>
                      <c:otherwise>
                          <input type="hidden" name="direction" value="${ direction }" />
                      </c:otherwise>
                  </c:choose>
                  <input type="submit" value="<fmt:message key="id" bundle="${ bundle }" />:" class="btn btn-default" />
              </form>
          </th>
          <th>
              <form action="/FrontController" method="get">
                  <input type="hidden" name="commandType" value="users" />
                  <input type="hidden" name="page" value="${ pageNumber }" />
                  <input type="hidden" name="orderBy" value="first_name" />
                  <c:choose>
                      <c:when test="${ orderBy.name == 'first_name' }">
                          <c:choose>
                              <c:when test="${ direction.name == 'asc'}">
                                  <input type="hidden" name="direction" value="desc" />
                              </c:when>
                              <c:otherwise>
                                  <input type="hidden" name="direction" value="asc" />
                              </c:otherwise>
                          </c:choose>
                      </c:when>
                      <c:otherwise>
                          <input type="hidden" name="direction" value="${ direction }" />
                      </c:otherwise>
                  </c:choose>
                  <input type="submit" value="<fmt:message key="first_name" bundle="${ bundle }" />:" class="btn btn-default" />
              </form>
          </th>
          <th>
              <form action="/FrontController" method="get">
                  <input type="hidden" name="commandType" value="users" />
                  <input type="hidden" name="page" value="${ pageNumber }" />
                  <input type="hidden" name="orderBy" value="last_name" />
                  <c:choose>
                      <c:when test="${ orderBy.name == 'last_name' }">
                          <c:choose>
                              <c:when test="${ direction.name == 'asc'}">
                                  <input type="hidden" name="direction" value="desc" />
                              </c:when>
                              <c:otherwise>
                                  <input type="hidden" name="direction" value="asc" />
                              </c:otherwise>
                          </c:choose>
                      </c:when>
                      <c:otherwise>
                          <input type="hidden" name="direction" value="${ direction }" />
                      </c:otherwise>
                  </c:choose>
                  <input type="submit" value="<fmt:message key="last_name" bundle="${ bundle }" />:" class="btn btn-default" />
              </form>
          </th>
          <th>
              <form action="/FrontController" method="get">
                  <input type="hidden" name="commandType" value="users" />
                  <input type="hidden" name="page" value="${ pageNumber }" />
                  <input type="hidden" name="orderBy" value="telephone_number" />
                  <c:choose>
                      <c:when test="${ orderBy.name == 'telephone_number' }">
                          <c:choose>
                              <c:when test="${ direction.name == 'asc'}">
                                  <input type="hidden" name="direction" value="desc" />
                              </c:when>
                              <c:otherwise>
                                  <input type="hidden" name="direction" value="asc" />
                              </c:otherwise>
                          </c:choose>
                      </c:when>
                      <c:otherwise>
                          <input type="hidden" name="direction" value="${ direction }" />
                      </c:otherwise>
                  </c:choose>
                  <input type="submit" value="<fmt:message key="phone_number" bundle="${ bundle }" />:" class="btn btn-default" />
              </form>
          </th>
          <th>
              <form action="/FrontController" method="get">
                  <input type="hidden" name="commandType" value="users" />
                  <input type="hidden" name="page" value="${ pageNumber }" />
                  <input type="hidden" name="orderBy" value="email" />
                  <c:choose>
                      <c:when test="${ orderBy.name == 'email' }">
                          <c:choose>
                              <c:when test="${ direction.name == 'asc'}">
                                  <input type="hidden" name="direction" value="desc" />
                              </c:when>
                              <c:otherwise>
                                  <input type="hidden" name="direction" value="asc" />
                              </c:otherwise>
                          </c:choose>
                      </c:when>
                      <c:otherwise>
                          <input type="hidden" name="direction" value="${ direction }" />
                      </c:otherwise>
                  </c:choose>
                  <input type="submit" value="<fmt:message key="email" bundle="${ bundle }" />:" class="btn btn-default" />
              </form>
          </th>
          <th>
              <form action="/FrontController" method="get">
                  <input type="hidden" name="commandType" value="users" />
                  <input type="hidden" name="page" value="${ pageNumber }" />
                  <input type="hidden" name="orderBy" value="role" />
                  <c:choose>
                      <c:when test="${ orderBy.name == 'role' }">
                          <c:choose>
                              <c:when test="${ direction.name == 'asc'}">
                                  <input type="hidden" name="direction" value="desc" />
                              </c:when>
                              <c:otherwise>
                                  <input type="hidden" name="direction" value="asc" />
                              </c:otherwise>
                          </c:choose>
                      </c:when>
                      <c:otherwise>
                          <input type="hidden" name="direction" value="${ direction }" />
                      </c:otherwise>
                  </c:choose>
                  <input type="submit" value="<fmt:message key="role" bundle="${ bundle }" />:" class="btn btn-default" />
              </form>
          </th>
            <th>
                <form action="/FrontController" method="get">
                    <input type="hidden" name="commandType" value="users" />
                    <input type="hidden" name="page" value="${ pageNumber }" />
                    <input type="hidden" name="orderBy" value="discount" />
                    <c:choose>
                        <c:when test="${ orderBy.name == 'discount' }">
                            <c:choose>
                                <c:when test="${ direction.name == 'asc'}">
                                    <input type="hidden" name="direction" value="desc" />
                                </c:when>
                                <c:otherwise>
                                    <input type="hidden" name="direction" value="asc" />
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:otherwise>
                            <input type="hidden" name="direction" value="${ direction }" />
                        </c:otherwise>
                    </c:choose>
                    <input type="submit" value="<fmt:message key="discount" bundle="${ bundle }" />:" class="btn btn-default" />
                </form>
            </th>
            <th>
                <form action="/FrontController" method="get">
                    <input type="hidden" name="commandType" value="users" />
                    <input type="hidden" name="page" value="${ pageNumber }" />
                    <input type="hidden" name="orderBy" value="balance" />
                    <c:choose>
                        <c:when test="${ orderBy.name == 'balance' }">
                            <c:choose>
                                <c:when test="${ direction.name == 'asc'}">
                                    <input type="hidden" name="direction" value="desc" />
                                </c:when>
                                <c:otherwise>
                                    <input type="hidden" name="direction" value="asc" />
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:otherwise>
                            <input type="hidden" name="direction" value="${ direction }" />
                        </c:otherwise>
                    </c:choose>
                    <input type="submit" value="<fmt:message key="balance" bundle="${ bundle }" />:" class="btn btn-default" />
                </form>
            </th>
            <th>
                <form action="/FrontController" method="get">
                    <input type="hidden" name="commandType" value="users" />
                    <input type="hidden" name="page" value="${ pageNumber }" />
                    <input type="hidden" name="orderBy" value="registration_date" />
                    <c:choose>
                        <c:when test="${ orderBy.name == 'registration_date' }">
                            <c:choose>
                                <c:when test="${ direction.name == 'asc'}">
                                    <input type="hidden" name="direction" value="desc" />
                                </c:when>
                                <c:otherwise>
                                    <input type="hidden" name="direction" value="asc" />
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:otherwise>
                            <input type="hidden" name="direction" value="${ direction }" />
                        </c:otherwise>
                    </c:choose>
                    <input type="submit" value="<fmt:message key="registration_date" bundle="${ bundle }" />:" class="btn btn-default" />
                </form>
            </th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="elem" items="${ requestScope.userPage.users }" varStatus="status">
            <fmt:formatDate value="${ elem.registrationDate }" pattern="yyyy-MM-dd" var="registrationDate" />
            <tr>
              <td>${ elem.id }</td>
              <td>${ elem.firstName }</td>
              <td>${ elem.lastName }</td>
              <td>${ elem.telephoneNumber }</td>
              <td>${ elem.email }</td>
              <td>${ elem.role }</td>
                <td>${ elem.discount }</td>
                <td>${ elem.balance }</td>
                <td>${ registrationDate }</td>
                <td>
                    <c:if test="${ elem.role.name != 'administrator' }">
                        <button type="button" class="btn btn-primary" data-toggle="collapse" data-target="#edit-form-${ elem.id }"><fmt:message key="edit" bundle="${ bundle }" /></button>
                        <button type="button" class="btn btn-primary" data-toggle="collapse" data-target="#edit-password-${ elem.id }"><fmt:message key="change_password" bundle="${ bundle }" /></button>
                        <form action="/FrontController" method="get">
                            <input type="hidden" name="commandType" value="userHistory" />
                            <input type="hidden" name="user_id" value="${ elem.id }" />
                            <input type="hidden" name="page" value="1" />
                            <input type="submit" value="<fmt:message key="user_history" bundle="${ bundle }" />" class="btn btn-success" />
                        </form>
                    </c:if>
                </td>
            </tr>
            <c:if test="${ elem.role.name != 'administrator' }">
                <tr id="edit-form-${ elem.id }" class="collapse">
                    <form action="/FrontController" method="post" name="editUserForm-${ elem.id }" onsubmit="return validateEditUserForm(${ elem.id })">
                        <input type="hidden" name="commandType" value="edit_user" />
                        <input type="hidden" name="user_id" value="${ elem.id }" />
                        <input type="hidden" name="page" value="${ pageNumber }" />
                        <input type="hidden" value="${ registrationDate }" class="form-control" name="registration_date" />
                        <input type="hidden" value="${ elem.balance }" class="form-control" name="balance" id="balance-${ elem.id }" />
                        <td colspan="6">
                            <label for="firstName-${ elem.id }" id="firstNameLabel-${ elem.id }"><fmt:message key="first_name" bundle="${ bundle }" />:</label>
                            <input type="text" value="${ elem.firstName }" class="form-control" name="firstName" id="firstName-${ elem.id }" />
                            <label for="lastName-${ elem.id }" id="lastNameLabel-${ elem.id }"><fmt:message key="last_name" bundle="${ bundle }" />:</label>
                            <input type="text" value="${ elem.lastName }" class="form-control" name="lastName" id="lastName-${ elem.id }" />
                            <label for="tel-${ elem.id }" id="telLabel-${ elem.id }"><fmt:message key="phone_number" bundle="${ bundle }" />:</label>
                            <input type="text" value="${ elem.telephoneNumber }" class="form-control" name="tel" id="tel-${ elem.id }" />
                            <label for="email-${ elem.id }" id="emailLabel-${ elem.id }"><fmt:message key="email" bundle="${ bundle }" />:</label>
                            <input type="text" value="${ elem.email }" class="form-control" name="email" id="email-${ elem.id }" />

                            <label for="role-${ elem.id }"><fmt:message key="role" bundle="${ bundle }" />:</label>
                            <select class="form-control" name="role" id="role-${ elem.id }" />
                                <option>banned</option>
                                <option>user</option>
                                <option>moderator</option>
                            </select>

                            <label for="discount-${ elem.id }" id="discountLabel-${ elem.id }"><fmt:message key="discount" bundle="${ bundle }" />:</label>
                            <input type="text" value="${ elem.discount }" class="form-control" name="discount" id="discount-${ elem.id }" />

                        </td>
                        <td>
                            <input type="submit" value="<fmt:message key="save" bundle="${ bundle }" />" class="btn btn-success" />
                        </td>
                    </form>
                </tr>
                <tr id="edit-password-${ elem.id }" class="collapse">
                    <form action="/FrontController" method="post" name="editPasswordForm-${ elem.id }" onsubmit="return validateEditPasswordForm(${ elem.id })">
                        <input type="hidden" name="commandType" value="edit_password" />
                        <input type="hidden" name="user_id" value="${ elem.id }" />
                        <input type="hidden" name="page" value="${ requestScope.userPage.currentPage }" />
                        <input type="hidden" name="orderBy" value="${ orderBy.name }" />
                        <input type="hidden" name="direction" value="${ direction.name }" />
                        <td colspan="3">
                            <label for="newPassword-${ elem.id }" id="newPasswordLabel-${ elem.id }"><fmt:message key="new_password" bundle="${ bundle }" />:</label>
                        </td>
                        <td colspan="3">
                            <input type="text" class="form-control" id="newPassword-${ elem.id }" name="new_password" />
                        </td>
                        <td>
                            <input type="submit" value="<fmt:message key="save" bundle="${ bundle }" />" class="btn btn-success" />
                        </td>
                    </form>
                </tr>
            </c:if>
        </c:forEach>
      </tbody>
        <tfoot>
            <tr align="center">
                <td colspan="8">
                    <ul class="pagination" id="pagination">

                    </ul>
                </td>
            </tr>
        </tfoot>
    </table>
</div>

<c:set var="pageAmount" value="${ requestScope.userPage.pageAmount }" scope="page" />

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/js/bootstrap-datepicker.min.js"></script>
<script type="text/javascript" src="https://formden.com/static/cdn/formden.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>
<script type="text/javascript" src="../../js/functions.js"></script>

<script type="text/javascript">
    executePagination(${ pageNumber }, ${ pageAmount }, "${ orderBy }", "${ direction }");

    function validateEditUserForm(number) {
        var emptyFieldString = "<fmt:message key="empty_field_answer" bundle="${ bundle }" />";
        var wrongTelString = "<fmt:message key="telephone_number_invalid" bundle="${ bundle }" />";
        var wrongEmailString = "<fmt:message key="email_invalid" bundle="${ bundle }" />";
        return validateEditUser(emptyFieldString, wrongTelString, wrongEmailString, number);
    };

    function validateEditPasswordForm(number) {
        var shortPasswordString = "<fmt:message key="password_invalid" bundle="${ bundle }" />";
        var formValid = validateEditPassword(shortPasswordString, number);
        if (formValid) {
            document.getElementById("newPassword-" + number).value = MD5(document.forms["editPasswordForm-" + number]["new_password"].value);
        } else {
            document.getElementById("newPassword-" + number).value = "";
        }
        return formValid;
    };
</script>

</body>
</html>
