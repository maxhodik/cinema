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
        <title>Cinema</title>
    </head>
<body>

<h6>schedule-admin</h6>
<h6>add-schedule-admin</h6>
<table class="table table-striped table-responsive-md btn-table table-bordered table-hover">
    <thead class="thead-dark">
    <tr>

        <th scope="col"><fmt:message key=" Date"/></a></th>
        <th scope="col"> <fmt:message key="time"/></a></th>
        <th scope="col"><fmt:message key="movie"/></a></th>
        <th scope="col"><fmt:message key="seats"/></a></th>



    </tr>
    </thead>

    <tbody>
    <c:choose>
        <c:when test="${schedule.isEmpty()}">
            <h2><fmt:message key="alert.orders.list.is.empty"/></h2>
        </c:when>
        <c:otherwise>

                <tr>
                  <form method="post" action="${pageContext.request.contextPath}/admin/add-session">
                      <td><input id="name" type="date"  name="date"><br/></td>
                      <td><input id="name" type="time" name="time"><br/></td>
                                       <td>
                              <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                       <fmt:message key="movie"/>
                                              </a>
                                              <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                            <c:choose>
                                                 <c:when test="${schedule.isEmpty()}">
                                                  <h2><fmt:message key="alert.orders.list.is.empty"/></h2>
                                              </c:when>
                                         <c:otherwise>
                                              <c:forEach var="movieDto" items="${movieDto}">
                                      <li> <input type="radio" name="movieName" value="${movieDto}">${movieDto}</li>
                                     </c:forEach>
                                        </c:otherwise>
                                          </c:choose>
                                           </ul>
                                       </td>
                      <td><input id="name" type="number"  name="seats"><br/></td>
                       <input class="btn btn-primary" type="submit" value="<fmt:message key="submit"/>">

                    </form>
              </tr>

        </c:otherwise>
    </c:choose>

    </tbody>
</table>
<br>
<hr>
<button> <a href="/cinema"> <fmt:message key="To main page"/></a>
</body>
</html>