<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>

<c:if test="${not empty param.lang}">
    <fmt:setLocale value="${param.lang}" scope="session"/>
</c:if>

<fmt:setBundle basename="message"/>

<html lang="${param.lang}">
<head>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container-fluid">
              <a class="navbar-brand"href="/cinema"> <fmt:message key="To main page"/></a>
              <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
              </button>
              <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                  <li class="nav-item">
                    <a class="nav-link active" aria-current="page"  href="${pageContext.request.contextPath}/login"><fmt:message key="button.login"/></a>
                  </li>
                  <li class="nav-item">
                     <a class="nav-link active" aria-current="page"  href="${pageContext.request.contextPath}/register"><fmt:message key="button.registration"/></a>
                   </li>
                  <li class="nav-item">
                    <a class="nav-link" href="#">Link</a>
                  </li>
                  <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                     <fmt:message key="label.language"/>
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                      <li><a class="dropdown-item" href="?lang=en"><fmt:message key="label.lang.en"/></a></li>
                      <li><a class="dropdown-item" href="?lang=ru"><fmt:message key="label.lang.ru"/></a></li>
                    </ul>
                  </li>
                  <li class="nav-item">
                  <a class="nav-link active" aria-current="page"  href="${pageContext.request.contextPath}/admin/add-session"><fmt:message key="Add new session"/></a>
                  </li>
                  <li class="nav-item">
                  <a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}/admin/movie"><fmt:message key="admin menu"/> </a>
                  </li>
                  <li class="nav-item">
                  <a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}/admin/analise"> <fmt:message key="Analise"/></a>
                  </li>
                </ul>
                <form>
                 <class="nav-item dropdown">
                      <a class="nav-link dropdown-toggle"  id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        <fmt:message key="label.language"/>
                       </a>
                      <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="?lang=en"><fmt:message key="label.lang.en"/></a></li>
                        <li><a class="dropdown-item" href="?lang=ru"><fmt:message key="label.lang.ru"/></a></li>
                       </ul>

                </form>
              </div>
            </div>
          </nav>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
<body>
<div>
 <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container-fluid">
               <div class="collapse navbar-collapse" id="navbarSupportedContent">
                            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                              <li class="nav-item dropdown">
<a>


              <form method="post" action="${pageContext.request.contextPath}/admin/analise">
              <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                  <li class="nav-item dropdown">
                   <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                  <fmt:message key="date"/>
                  </a>
                 <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
               <label> Start  <input  type="date"   name="date" <br/>
               <label> End <input  type="date"   name="date" <br/>
              </ul>
               </li>
                    <li class="nav-item dropdown">
                   <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                  <fmt:message key="day of week"/>
                  </a>
                 <ul class="dropdown-menu" aria-labelledby="navbarDropdown">

                              <li> <input type="checkbox" name="day" id=6 value = "6"><fmt:message key="SUNDAY"/></li>
                              <li> <input type="checkbox" name="day" id=0 value = "0"><fmt:message key="MONDAY"/></li>
                              <li> <input type="checkbox" name="day" id=1 value = "1"><fmt:message key="TUESDAY"/></li>
                              <li> <input type="checkbox" name="day" id=2 value = "2"><fmt:message key="WEDNESDAY"/></li>
                              <li> <input type="checkbox" name="day" id=3 value = "3"><fmt:message key="THURSDAY"/></li>
                              <li> <input type="checkbox" name="day" id=4 value = "4"><fmt:message key="FRIDAY"/></li>
                              <li> <input type="checkbox" name="day" id=5 value = "5"><fmt:message key="SATURDAY"/></li>
              </ul>
               </li>
 <li class="nav-item dropdown">
                   <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                  <fmt:message key="time"/>
                  </a>
                 <ul class="dropdown-menu" aria-labelledby="navbarDropdown">

                        <label> Start  <input  type="time"   name="time" <br/>
                       <label> End <input  type="time"   name="time" <br/>
              </ul>
               </li>
               <li class="nav-item dropdown">
                   <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                  <fmt:message key="movi"/>
                  </a>
                 <ul class="dropdown-menu" aria-labelledby="navbarDropdown">

                  <c:choose>
                                  <c:when test="${schedule.isEmpty()}">
                                  <h2><fmt:message key="alert.orders.list.is.empty"/></h2>
                                    </c:when>
                                  <c:otherwise>
                                  <c:forEach var="movieDto" items="${movieDto}">
                                      <li> <input type="checkbox" name="movie" value="${movieDto}">${movieDto}</li>
                                  </c:forEach>
                                     </c:otherwise>
                                        </c:choose>
              </ul>
               </li>
 <li class="nav-item dropdown">
                   <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                  <fmt:message key="Filter by status"/>
                  </a>
                 <ul class="dropdown-menu" aria-labelledby="navbarDropdown">

                    <input type="checkbox" name="status" value="ACTIVE"/> <fmt:message key="Filter by status"/>
              </ul
               </li>
         <li class="nav-item">

            <input  class="btn btn-secondary" type="submit" value="Submit" />
            </li>
            </form>
            <li >
 <form method="post" action="${pageContext.request.contextPath}/admin/analise">
  <input hidden type="text" name="reset" value="true"/>
  <input class="btn btn-secondary" type="submit" value="<fmt:message key="reset"/>">
      </form>
   </li>
      </a>
              </div>
            </div>
          </nav>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cinema</title>
</div>
<h6>schedule-admin</h6>
<table class="table table-striped table-responsive-md btn-table table-bordered table-hover">
    <thead class="thead-dark">
      <form method="post" action="${pageContext.request.contextPath}/admin/analise">
             <class="item dropdown">
                            <a class="btn btn-outline-secondary dropdown-toggle"  id="Dropdown" role="button" data-bs-toggle="dropdown" >
                              <fmt:message key="label.sort"/>
                             </a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                              <li><input type="radio" name="orderBy" value="date" /><fmt:message key="Sort by Date"/> </li>
                              <li>   <input type="radio" name="orderBy" value="day" /><fmt:message key="Sort by Day"/></li>
                              <li> <input type="radio" name="orderBy" value="time" /><fmt:message key="Sort by time"/></li>
                              <li> <input type="radio" name="orderBy" value="movie" /><fmt:message key="Sort by Movie"/><li>
          <li><input type="radio" name="orderBy" value="number_available_seats"/> <fmt:message key="Sort by number of available seats"/></li>
      <li> <input type="radio" name="orderBy" value="number_sold_seats"/>  <fmt:message key="Sort by number of sold seats"/></li>
      <li> <input type="radio" name="orderBy" value="capacity"/>  <fmt:message key="Sort by hall capacity"/></li>
      <li> <input type="radio" name="orderBy" value="attendance"/>  <fmt:message key="Sort by hall attendance"/></li>

      </ul>

    <tr>
        <th scope="col">

         </div>
              <input type="radio" name="orderBy" value="date" /><fmt:message key="Sort by Date"/> </th>
        <th scope="col">

         <input type="radio" name="orderBy" value="day" /><fmt:message key="Sort by Day"/>
        </th>
          <th scope="col">

                     </div> <input type="radio" name="orderBy" value="time" /><fmt:message key="Sort by time"/></a></th>
        <th scope="col">

           <input type="radio" name="orderBy" value="movie" /><fmt:message key="Sort by Movie"/>
           </th>
           <th scope="col"><input type="checkbox" name="status" value="ACTIVE"/> <fmt:message key="Filter by status"/></th>
           <th scope="col"><input type="radio" name="orderBy" value="number_available_seats"/> <fmt:message key="Sort by number of available seats"/></th>
           <th scope="col"><input type="radio" name="orderBy" value="number_sold_seats"/>  <fmt:message key="Sort by number of sold seats"/></a></th>
           <th scope="col"><input type="radio" name="orderBy" value="capacity"/>  <fmt:message key="Sort by hall capacity"/></a></th>
           <th scope="col"><input type="radio" name="orderBy" value="attendance"/>  <fmt:message key="Sort by hall attendance"/></a></th>
    </tr>
    <input  class="btn btn-secondary" type="submit" value="Submit" />

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
                    <td>${sessionAdminDto.status}</td>
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
  <!-- jQuery -->
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
  <!-- Latest compiled and minified JavaScript -->
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</body>
</html>