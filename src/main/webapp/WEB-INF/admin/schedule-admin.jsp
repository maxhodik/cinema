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
 <form action="schedule?admin=true">
<div>
 <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container-fluid">
               <div class="collapse navbar-collapse" id="navbarSupportedContent">
                            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                              <li class="nav-item dropdown">
<a>


              <form method="get" action="schedule?admin=true">
              <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">



 <li class="nav-item dropdown">
                   <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                  <fmt:message key="Filter by"/>
                  </a>
                 <ul class="dropdown-menu" aria-labelledby="navbarDropdown">

                  <li>  <input type="checkbox" name="status" value="ACTIVE"/> <fmt:message key="Filter by status"/></li>
                    <li><input type="checkbox" name="number_available_seats" value = "true"><fmt:message key="Filter by number of available seats"/></li>
              </ul
               </li>
         <li class="nav-item">

            <input  class="btn btn-secondary" type="submit" value="Submit" />
            </li>
            </form>
            <li >
 <form method="get" action="schedule?admin=true">
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
       </form>
 <form method="get" action="schedule?admin=true">
             <class="item dropdown">
    <a class="btn btn-outline-secondary dropdown-toggle"  id="Dropdown" role="button" data-bs-toggle="dropdown" >
        <fmt:message key="label.sort"/>
       </a>
     <ul class="dropdown-menu" aria-labelledby="navbarDropdown">

<li><a class="dropdown-item" href="?orderBy=date"> <fmt:message key="Sort by Date"/></a></li>
                        <li><a class="dropdown-item" href="?orderBy=time"> <fmt:message key="Sort by time"/></a></li>
                        <li><a class="dropdown-item" href="?orderBy=movie"> <fmt:message key="Sort by movie"/></a></li>
                        <li><a class="dropdown-item" href="?orderBy=number_available_seats"> <fmt:message key="Sort by number of available seats"/></a><li>

      </ul>
          <input  class="btn btn-secondary" type="submit" value="Submit" />
      </form>
<table class="table table-striped table-responsive-md btn-table table-bordered table-hover">
    <thead class="thead-dark">


      <tr>

       <th> <fmt:message key="Date"/> </th>
       <th> <fmt:message key="time"/> </th>
       <th><fmt:message key="movie"/></th>

   <div>
    <th>
     <fmt:message key="number of available seats"/></th>
    <th> <fmt:message key="status"/></th>

   </div>
       </tr>

</form>
    </thead>

       <tbody>
       <c:choose>
           <c:when test="${sessionAdminDto.isEmpty()}">
               <h2><fmt:message key="alert.orders.list.is.empty"/></h2>
           </c:when>
           <c:otherwise>
               <c:forEach var="sessionAdminDto" items="${sessionAdminDto}">
                   <tr>
                          <td>${sessionAdminDto.date}</td>
                          <td>${sessionAdminDto.time}</td>
                          <td>${sessionAdminDto.movieName}</td>
                          <td>${sessionAdminDto.numberOfAvailableSeats}</td>
                          <td>${sessionAdminDto.status}</td>
                       <td>
                    <div>
                       <a input class="btn btn-outline-dark" type="submit" href="${pageContext.request.contextPath}/admin/update-session?id=${sessionAdminDto.id}"><fmt:message key="Edit"/></a>
                      </div>
                      <div>
                            <form method="post" action="${pageContext.request.contextPath}/schedule/delete" >
                                 <class="form-group" horizontal>
                                 <input hidden type="number" name="id" value="${sessionAdminDto.id}"/>
                                 <input class="btn btn-outline-dark" type="submit" value="<fmt:message key="cancel"/>">
                              </form>
                        </div>
                    </td>
                </tr>
            </c:forEach>
        </c:otherwise>
    </c:choose>

    </tbody>


</table>


</body>
</html>