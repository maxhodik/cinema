<%@ page contentType="text/html;charset=UTF-8" language="java" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
      <%@ page isELIgnored="false" %>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
          integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
          crossorigin="anonymous"></script>

        <c:if test="${not empty param.lang}">
          <fmt:setLocale value="${param.lang}" scope="session" />
        </c:if>

        <fmt:setBundle basename="message" />

        <html lang="${param.lang}">
        <c:import url="head-admin.jsp" />
       <c:if test="${errors}">
              <label class="alert alert-info">
                <fmt:message key="alert.session.form.not.valid" />
              </label>
               <c:remove var="errors" scope="session" />
       </c:if>
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
                                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                                  data-bs-toggle="dropdown" aria-expanded="false">
                                  <fmt:message key="label.date" />
                                </a>
                                <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                  <label>
                                    <fmt:message key="label.start" /> <input type="date" name="date" <br />
                                    <label>
                                      <fmt:message key="label.end" /> <input type="date" name="date" <br />
                                </ul>
                              </li>
                              <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                                  data-bs-toggle="dropdown" aria-expanded="false">
                                  <fmt:message key="label.day_of_week" />
                                </a>
                                <ul class="dropdown-menu" aria-labelledby="navbarDropdown">

                                  <li> <input type="checkbox" name="day" id=6 value="6">
                                    <fmt:message key="SUNDAY" />
                                  </li>
                                  <li> <input type="checkbox" name="day" id=0 value="0">
                                    <fmt:message key="MONDAY" />
                                  </li>
                                  <li> <input type="checkbox" name="day" id=1 value="1">
                                    <fmt:message key="TUESDAY" />
                                  </li>
                                  <li> <input type="checkbox" name="day" id=2 value="2">
                                    <fmt:message key="WEDNESDAY" />
                                  </li>
                                  <li> <input type="checkbox" name="day" id=3 value="3">
                                    <fmt:message key="THURSDAY" />
                                  </li>
                                  <li> <input type="checkbox" name="day" id=4 value="4">
                                    <fmt:message key="FRIDAY" />
                                  </li>
                                  <li> <input type="checkbox" name="day" id=5 value="5">
                                    <fmt:message key="SATURDAY" />
                                  </li>
                                </ul>
                              </li>
                              <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                                  data-bs-toggle="dropdown" aria-expanded="false">
                                  <fmt:message key="label.time" />
                                </a>
                                <ul class="dropdown-menu" aria-labelledby="navbarDropdown">

                                  <li><label>
                                      <fmt:message key="label.start" /> <input type="time" name="time" <br /></li>
                                  <li><label>
                                      <fmt:message key="label.end" /> <input type="time" name="time" <br /></li>
                                </ul>
                              </li>
                              <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                                  data-bs-toggle="dropdown" aria-expanded="false">
                                  <fmt:message key="label.movie" />
                                </a>
                                <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                  <c:choose>
                                    <c:when test="${schedule.isEmpty()}">
                                      <h2>
                                        <fmt:message key="alert.schedule.list.is.empty" />
                                      </h2>
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
                                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                                  data-bs-toggle="dropdown" aria-expanded="false">
                                  <fmt:message key="label.status" />
                                </a>
                                <ul class="dropdown-menu" aria-labelledby="navbarDropdown">

                                  <input type="checkbox" name="status" value="ACTIVE" />
                                  <fmt:message key="label.status" />
                                </ul>
                              </li>
                              <li>
                                <class="nav-item dropdown">
                                  <a class="btn btn-outline-secondary dropdown-toggle" role="button"
                                    data-bs-toggle="dropdown" aria-expanded="false">
                                    <fmt:message key="label.sort" />
                                  </a>
                                  <ul class="dropdown-menu" aria-label="navbarDropdown">
                                    <li><input type="radio" name="orderBy" value="date" />
                                      <fmt:message key="label.date" />
                                    </li>
                                    <li><input type="radio" name="orderBy" value="day" />
                                      <fmt:message key="label.day_of_week" />
                                    </li>
                                    <li><input type="radio" name="orderBy" value="time" />
                                      <fmt:message key="label.time" />
                                    </li>
                                    <li><input type="radio" name="orderBy" value="movie" />
                                      <fmt:message key="label.movie" />
                                    <li>
                                    <li><input type="radio" name="orderBy" value="number_available_seats" />
                                      <fmt:message key="label.available_seats" />
                                    </li>
                                    <li><input type="radio" name="orderBy" value="number_sold_seats" />
                                      <fmt:message key="label.sold_seats" />
                                    </li>
                                    <li><input type="radio" name="orderBy" value="capacity" />
                                      <fmt:message key="label.hall_capacity" />
                                    </li>
                                    <li><input type="radio" name="orderBy" value="attendance" />
                                      <fmt:message key="label.hall_attendance" />
                                    </li>
                                  </ul>
                              </li>

                              <li class="nav-item">
                                <input hidden type="number" name="records" value="${records}" />
                                <input hidden type="number" name="offset" value="${offset}" />
                                <input class="btn btn-secondary" type="submit" value=<fmt:message key="button.submit" />
                              </li>
                        </form>
                    <li>
                      <form method="post" action="${pageContext.request.contextPath}/admin/analise">
                        <input hidden type="text" name="reset" value="true" />
                        <input class="btn btn-secondary" type="submit" value="<fmt:message key="reset" />">
                      </form>
                    </li>
                    </a>
                </div>
              </div>
            </nav>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>Cinema</title>
          </div>
          <div class="container col-11">
            <table class="table table-striped table-responsive-md btn-table table-bordered table-hover">


              <tr>
                <th scope="col">
                  <fmt:message key="label.date" />
                </th>
                <th scope="col">
                  <fmt:message key="label.day_of_week" />
                </th>
                <th scope="col">
                  <fmt:message key="label.time" />
                </th>
                <th scope="col">
                  <fmt:message key="label.movie" />
                </th>
                <th scope="col">
                  <fmt:message key="label.status" />
                </th>
                <th scope="col">
                  <fmt:message key="label.available_seats" />
                </th>
                <th scope="col">
                  <fmt:message key="label.sold_seats" /></a>
                </th>
                <th scope="col">
                  <fmt:message key="label.hall_capacity" /></a>
                </th>
                <th scope="col">
                  <fmt:message key="label.hall_attendance" /></a>
                </th>
              </tr>


              </form>
              </thead>

              <tbody>
                <c:choose>
                  <c:when test="${sessionAdminDto.isEmpty()}">
                     <label class="alert alert-info">
                      <fmt:message key="alert.schedule.list.is.empty" />
                    </label>
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
          </div>
          </tbody>
          </table>
          <!-- jQuery -->
          <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
          <!-- Latest compiled and minified JavaScript -->
          <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
            crossorigin="anonymous"></script>
        </body>
        <c:choose>
          <c:when test="${not empty orderBy}">
            <c:set var="href" scope="request" value="analise?orderBy=${orderBy}&" />
          </c:when>
          <c:otherwise>
            <c:set var="href" scope="request" value="analise?" />
          </c:otherwise>
        </c:choose>
        </form>
        <c:import url="/WEB-INF/pagination.jsp" />

        </html>