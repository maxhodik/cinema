<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="my" uri = "tags/addParam.tld"%>
<%@ taglib prefix="pgn" uri = "tags/pagination.tld"%>
<%@ page isELIgnored="false" %>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>

<c:if test="${not empty lang}">
    <fmt:setLocale value="${param.lang}" scope="session"/>
</c:if>
<c:set var="queryString" value="${pageContext.request.queryString}" />
<fmt:setBundle basename="message"/>

<html lang="${param.lang}">

 <head>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container-fluid">
              <a class="navbar-brand"href="/cinema"> <fmt:message key="label.mainPage"/></a>
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

                  <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                     <fmt:message key="label.language"/>
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                      <li><a class="dropdown-item" href="?lang=en"><fmt:message key="label.lang.en"/></a></li>
                      <li><a class="dropdown-item" href="?lang=ua"><fmt:message key="label.lang.ua"/></a></li>
                    </ul>
                  </li>
                </ul>
                 <form class="nav-item" action="schedule">
                           <label for="records"><fmt:message key="number.records"/></label>
                           <input class="col-2" type="number" min="1" name="records" id="records"
                            value="${not empty requestScope.records ? requestScope.records : "5"}">
                           <input type="hidden" name="offset" value="0">
                           <button type="submit" class="btn btn-dark mt-2 mb-3"><fmt:message key="button.submit"/></button>
                </form>
              </div>
            </div>

          </nav>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cinema</title>
    </head>
<h1>"${requestScope}"</h1>
<body>


 <form action="schedule">
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
              </form>
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
            <h2><fmt:message key="alert.orders.list.is.empty"/></h2>
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
