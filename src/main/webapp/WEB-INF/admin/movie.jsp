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
<c:import url="head-admin.jsp" />

<body>
<c:if test="${sessionScope.exception == true}">
    <label class="alert alert-info"> <fmt:message key="alert.movie.already.busy"/></label>
</c:if>
<c:remove var="exception" scope="session"/>
<form action="movie">
<li class="nav-item">
                  <a class="btn btn-outline-secondary " aria-current="page"  href="${pageContext.request.contextPath}/admin/movie/add-movie"><fmt:message key="button.new_movie"/></a>
                           <class="item dropdown">
                            <a class="btn btn-outline-secondary dropdown-toggle"  id="Dropdown" role="button" data-bs-toggle="dropdown" >
                              <fmt:message key="label.sort"/>
                             </a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                              <thead>
                              <c:set var="base" value="movie?"/>
                           <c:set var="orderById" value="orderBy=id"/>
                           <c:set var="orderByMovie" value="orderBy=name"/>
                           <c:set var="limits" value="&offset=0&records=${records}"/>
                              <li><a class="dropdown-item" href="${base.concat(orderById).concat(limits)}"> <fmt:message key="label.id"/></a></li>
                              <li><a class="dropdown-item" href="${base.concat(orderByMovie).concat(limits)}"> <fmt:message key="label.movie"/></a></li>

      </ul>
        </thead>
                    </form>
  <div class="container col-8">
<table class="table table-striped table-responsive-md btn-table table-bordered table-hover">
    <thead class="thead-dark">
    <tr>
        <th scope="col"><fmt:message key="label.id"/></th>
        <th scope="col"> <fmt:message key="label.movie"/></a></th>
    </tr>
    </thead>

    <tbody>
    <c:choose>
        <c:when test="${movieDto.isEmpty()}">
            <h2><fmt:message key="alert.orders.list.is.empty"/></h2>
        </c:when>
        <c:otherwise>
            <c:forEach var="movieDto" items="${movieDto}">
                <tr>
                    <td>${movieDto.id}</td>
                    <td>${movieDto.name}</td>
                    <td>
      <form method="post" action="${pageContext.request.contextPath}/admin/movie/delete" >
         <class="form-group">
         <input hidden type="number" name="id" value="${movieDto.id}"/>
         <input class="btn btn-outline-dark" type="submit" value="<fmt:message key="button.delete"/>">
      </form>


  <a class="btn btn-outline-dark" type="submit" href="${pageContext.request.contextPath}/admin/movie/update-movie?id=${movieDto.id}" ><fmt:message key="button.edit"/></a>
                    </td>
                </tr>
            </c:forEach>
        </c:otherwise>
    </c:choose>

    </tbody>
</table>
</div>
<c:choose>
 <c:when test="${not empty orderBy}">
           <c:set var="href" scope="request"
                         value="movie?orderBy=${orderBy}&"/>
 </c:when>
 <c:otherwise>
          <c:set var="href" scope="request"
                                  value="movie?"/>
 </c:otherwise>
</c:choose>
  <c:import url="/WEB-INF/pagination.jsp"/>
</body>
</html>