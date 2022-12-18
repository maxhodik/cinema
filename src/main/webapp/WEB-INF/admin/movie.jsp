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
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Cinema</title>
</head>
<body>
<ul>
    <li><button><a href="?lang=en"><fmt:message key="label.lang.en"/></a></li>
    <li><button><a href="?lang=ru"><fmt:message key="label.lang.ru"/></a></li>
</ul>

<table class="table table-striped table-responsive-md btn-table table-bordered table-hover">
    <thead class="thead-dark">
    <tr>
        <th scope="col"> id </th>

        <th scope="col"><button> <a href="?orderBy=name"> <fmt:message key="Sort by movie name"/></a></th>

    </tr>
    </thead>

    <tbody>
    <c:choose>
        <c:when test="${movie.isEmpty()}">
            <h2><fmt:message key="alert.orders.list.is.empty"/></h2>
        </c:when>
        <c:otherwise>
            <c:forEach var="movie" items="${movie}">
                <tr>
                    <td>${movie.id}</td>
                    <td>${movie.name}</td>

                    <td>
      <form method="post" action="${pageContext.request.contextPath}/admin/movie/delete" >
         <class="form-group">
         <input hidden type="number" name="id" value="${movie.id}"/>
         <input class="btn btn-primary" type="submit" value="<fmt:message key="delete"/>">
      </form>


<button>  <a href="${pageContext.request.contextPath}/admin/movie/update-movie?id=${movie.id}" class="btn btn-primary"><fmt:message key="update"/></a>
                    </td>
                </tr>
            </c:forEach>
        </c:otherwise>
    </c:choose>

    </tbody>
</table>
<div>
 <button><a href="${pageContext.request.contextPath}/admin/movie/add-movie"><fmt:message key="Add new movie"/></a>
</div>

<hr>
<button> <a href="/cinema"> <fmt:message key="To main page"/></a>
</body>
</html>