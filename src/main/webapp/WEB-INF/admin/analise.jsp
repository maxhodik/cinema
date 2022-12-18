<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<!-- Latest compiled and minified CSS -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

  <!-- Optional theme -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
<c:if test="${not empty param.lang}">
    <fmt:setLocale value="${param.lang}" scope="session"/>
</c:if>

<fmt:setBundle basename="message"/>

<html lang="${param.lang}">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Cinema</title>
</head>
<body>
<ul>

    <li><button><a href="?lang=en"><fmt:message key="label.lang.en"/></a></li>
    <li><button><a href="?lang=ru"><fmt:message key="label.lang.ru"/></a></li>
</ul>
<h6>schedule-admin</h6>
<table class="table table-striped table-responsive-md btn-table table-bordered table-hover">
    <thead class="thead-dark">
      <form method="post" action="${pageContext.request.contextPath}/admin/analise">
    <tr>
        <th scope="col">
        <div class = "dropdown">
          <button class="btn btn-info dropdown-toggle" data-toggle="dropdown">Список <span class="caret"></span></button>
                           <ul class="dropdown-menu">
            <label> Start  <input  type="date"   name="date" <br/>
            <label> End <input  type="date"   name="date" <br/>
              </ul>
         </div>
              <input type="radio" name="orderBy" value="date" /><fmt:message key="Sort by Date"/> </th>
        <th scope="col">
       <div class="dropdown">
         <button class="btn btn-info dropdown-toggle" data-toggle="dropdown">Список <span class="caret"></span></button>
            <ul class="dropdown-menu">
                <li> <input type="checkbox" name="day" id=6 value = "6"><fmt:message key="SUNDAY"/></li>
                <li> <input type="checkbox" name="day" id=0 value = "0"><fmt:message key="MONDAY"/></li>
                <li> <input type="checkbox" name="day" id=1 value = "1"><fmt:message key="TUESDAY"/></li>
                <li> <input type="checkbox" name="day" id=2 value = "2"><fmt:message key="WEDNESDAY"/></li>
                <li> <input type="checkbox" name="day" id=3 value = "3"><fmt:message key="THURSDAY"/></li>
                <li> <input type="checkbox" name="day" id=4 value = "4"><fmt:message key="FRIDAY"/></li>
                <li> <input type="checkbox" name="day" id=5 value = "5"><fmt:message key="SATURDAY"/></li>
            </ul>
      </div>
         <input type="radio" name="orderBy" value="day" /><fmt:message key="Sort by Day"/>
        </th>
          <th scope="col">
                <div class = "dropdown">
                  <button class="btn btn-info dropdown-toggle" data-toggle="dropdown">Список <span class="caret"></span></button>
                                   <ul class="dropdown-menu">
                    <label> Start  <input  type="time"   name="time" <br/>
                    <label> End <input  type="time"   name="time" <br/>
                      </ul>
                 </div>
                     </div> <input type="radio" name="orderBy" value="time" /><fmt:message key="Sort by time"/></a></th>
        <th scope="col">
        <div class="dropdown">
          <button class="btn btn-info dropdown-toggle" data-toggle="dropdown">Список <span class="caret"></span></button>
            <ul class="dropdown-menu">
                <c:choose>
                <c:when test="${schedule.isEmpty()}">
                <h2><fmt:message key="alert.orders.list.is.empty"/></h2>
                  </c:when>
                <c:otherwise>
                <c:forEach var="movieDto" items="${movieDto}">
                    <li> <input type="checkbox" name="movie" value = ${movieDto}>${movieDto}</li>
                </c:forEach>
                   </c:otherwise>
                      </c:choose>
            </ul>
           </div> <input type="radio" name="orderBy" value="movie" /><fmt:message key="Sort by Movie"/>
           </th>
           <th scope="col"><input type="radio" name="orderBy" value="number_available_seats"/> <fmt:message key="Sort by number of available seats"/></th>
           <th scope="col"><input type="radio" name="orderBy" value="number_sold_seats"/>  <fmt:message key="Sort by number of sold seats"/></a></th>
           <th scope="col"><input type="radio" name="orderBy" value="capacity"/>  <fmt:message key="Sort by hall capacity"/></a></th>
           <th scope="col"><input type="radio" name="orderBy" value="attendance"/>  <fmt:message key="Sort by hall attendance"/></a></th>
    </tr>
    <input type="submit" value="Submit" />
    </form>
    </thead>

    <tbody>
    <c:choose>
        <c:when test="${schedule.isEmpty()}">
            <h2><fmt:message key="alert.orders.list.is.empty"/></h2>
        </c:when>
        <c:otherwise>
            <c:forEach var="sessionAdminDto" items="${sessionAdminDto}">
                <tr>
                    <td>${sessionAdminDto.date}</td>
                    <td>${sessionAdminDto.dayOfWeek}</td>
                    <td>${sessionAdminDto.time}</td>
                    <td>${sessionAdminDto.movieName}</td>
                    <td>${sessionAdminDto.numberOfAvailableSeats}</td>
                    <td>${sessionAdminDto.numberOfSoldSeats}</td>
                    <td>${sessionAdminDto.capacity}</td>
                    <td>${sessionAdminDto.attendance}</td>
                </tr>
            </c:forEach>
        </c:otherwise>
    </c:choose>

    </tbody>
</table>
<br>

<hr>
<button> <a href="/cinema"> <fmt:message key="To main page"/></a>

  <!-- jQuery -->
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
  <!-- Latest compiled and minified JavaScript -->
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</body>
</html>