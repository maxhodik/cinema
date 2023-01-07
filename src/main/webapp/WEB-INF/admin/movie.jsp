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
                  <a class="nav-link active" aria-current="page"  href="${pageContext.request.contextPath}/admin/movie/add-movie"><fmt:message key="Add new movie"/></a>
                  <li class="nav-item">
                  <a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}/admin/analise"> <fmt:message key="Analise"/></a>
                  </li>
                </ul>
                  </a>
                       <form class="nav-item">
                             <label for="records"><fmt:message key="number.records"/></label>
                                   <input class="col-2" type="number" min="1" name="records" id="records"
                                    value="${not empty requestScope.records ? requestScope.records : "5"}">
                                   <input type="hidden" name="offset" value="0">
                                   <button type="submit" class="btn btn-dark mt-2 mb-3"><fmt:message key="submit"/></button>
                       </form>

                </form>
              </div>
            </div>
          </nav>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cinema</title>
    </head>
<body><form action="movie">
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
                              <li><a class="dropdown-item" href="${base.concat(orderById).concat(limits)}"> <fmt:message key="Sort by Date"/></a></li>
                              <li><a class="dropdown-item" href="${base.concat(orderByMovie).concat(limits)}"> <fmt:message key="Sort by movie"/></a></li>

      </ul>
        </thead>
                    </form>
<table class="table table-striped table-responsive-md btn-table table-bordered table-hover">
    <thead class="thead-dark">
    <tr>
        <th scope="col"><fmt:message key="id"/></th>
        <th scope="col"> <fmt:message key="movie name"/></a></th>
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
         <input class="btn btn-outline-dark" type="submit" value="<fmt:message key="delete"/>">
      </form>


  <a class="btn btn-outline-dark" type="submit" href="${pageContext.request.contextPath}/admin/movie/update-movie?id=${movieDto.id}" ><fmt:message key="update"/></a>
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