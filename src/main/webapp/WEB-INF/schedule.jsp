<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="my" uri = "tags/lang.tld"%>
<%@ page isELIgnored="false" %>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>

<c:if test="${not empty param.lang}">
    <fmt:setLocale value="${param.lang}" scope="session"/>
</c:if>
<c:set var="queryString" value="${pageContext.request.queryString}" />
<fmt:setBundle basename="message"/>

<html lang="${param.lang}">
<c:import url="head.jsp" />



<!--<h1>"${requestScope}"</h1>--!>
<body>
 <form action="schedule">
<div>
 <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container-fluid">
               <div class="collapse navbar-collapse" id="navbarSupportedContent">
                            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                              <li class="nav-item dropdown">
    <a>
              <form method="get" action="schedule">
              <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
            <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                       <fmt:message key="label.movie"/>
                       </a>
                      <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                       <c:choose>
                                       <c:when test="${schedule.isEmpty()}">
                                       <h2><fmt:message key="alert.schedule.list.is.empty"/></h2>
                                         </c:when>
                                       <c:otherwise>
                                       <c:forEach var="movieDto" items="${movieDto}">
                                           <li> <input type="checkbox" name="movie" value="${movieDto}">${movieDto}</li>
                                       </c:forEach>
                                          </c:otherwise>
                                             </c:choose>
                   </ul>
                    </li>
         <li class="nav-item">
                           <input type="hidden" name="offset" value="0">
                           <input type="hidden" name="records" value="${requestScope.records}">
            <input  class="btn btn-secondary" type="submit" value=<fmt:message key="button.submit"/>
            </li>
            </form>
            <li >
 <form method="get" action="schedule">
  <input hidden type="text" name="reset" value="true"/>
  <input type="hidden" name="offset" value="0">
  <input type="hidden" name="records" value="${requestScope.records}">
  <input class="btn btn-secondary" type="submit" value="<fmt:message key="reset"/>">
      </form>
   </li>
   <li><form action="schedule">
                         <class="item dropdown">
                              <a class="btn btn-outline-secondary dropdown-toggle"  id="Dropdown" role="button" data-bs-toggle="dropdown" >
                                <fmt:message key="label.sort"/>
                               </a>
                              <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <thead>
                                <c:set var="base" value="schedule?"/>
                             <c:set var="orderByDate" value="orderBy=date"/>
                             <c:set var="orderByTime" value="orderBy=time"/>
                             <c:set var="orderByMovie" value="orderBy=movie"/>
                             <c:set var="orderBySeats" value="orderBy=number_available_seats"/>
                             <c:set var="limits" value="&offset=0&records=${records}"/>
                                <li><a class="dropdown-item" href="${base.concat(orderByDate).concat(limits)}"> <fmt:message key="label.date"/></a></li>
                                <li><a class="dropdown-item" href="${base.concat(orderByTime).concat(limits)}"> <fmt:message key="label.time"/></a></li>
                                <li><a class="dropdown-item" href="${base.concat(orderByMovie).concat(limits)}"> <fmt:message key="label.movie"/></a></li>
                                <li><a class="dropdown-item" href="${base.concat(orderBySeats).concat(limits)}"> <fmt:message key="label.available_seats"/></a><li>
        </ul>
          </thead>
                      </form> </li>
      </a>
              </div>
            </div>
          </nav>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cinema</title>
</div>
       </form>

      </form>
<div class="container col-11">
<table class="table table-striped table-responsive-md btn-table table-bordered table-hover">
    <thead class="thead-dark">
<form action="schedule">

    <tr>
    <th> <fmt:message key="label.date"/> </th>
    <th> <fmt:message key="label.time"/> </th>
    <th> <fmt:message key="label.movie"/></th>
<div>
 <th> <fmt:message key="label.available_seats"/>
</div>
    </tr>

    </thead>

    <tbody>
    <c:choose>
        <c:when test="${sessionAdminDto.isEmpty()}">
            <h2><fmt:message key="alert.list.is.empty"/></h2>
        </c:when>
        <c:otherwise>
            <c:forEach var="sessionAdminDto" items="${sessionAdminDto}">
                <tr>
                       <td>${sessionAdminDto.date}</td>
                       <td>${sessionAdminDto.time}</td>
                        <td>${sessionAdminDto.movieName}</td>
                       <td>${sessionAdminDto.numberOfAvailableSeats}</td>
                    <td>
                        <button> <a href="${pageContext.request.contextPath}/order?id=${sessionAdminDto.id}"><fmt:message key="purchase"/></a>
                    </td>
                </tr>
            </c:forEach>
        </c:otherwise>
    </c:choose>
    </tbody>
<div>
</table>
<c:choose>
 <c:when test="${not empty orderBy}">
           <c:set var="href" scope="request"
                         value="schedule?orderBy=${orderBy}&"/>
 </c:when>
 <c:otherwise>
          <c:set var="href" scope="request"
                                  value="schedule?"/>
 </c:otherwise>
</c:choose>
</form>
<c:import url="pagination.jsp"/>
</body>

</html>
