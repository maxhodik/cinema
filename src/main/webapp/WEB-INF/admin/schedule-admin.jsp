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
<h6>schedule-admin</h6>
<table class="table table-striped table-responsive-md btn-table table-bordered table-hover">
    <thead class="thead-dark">
    <form action="schedule?admin=true">

       <th> <input type="radio" name="admin" value="true" checked /><fmt:message key="Admin"/> </th>
      <tr>

       <th> <input type="radio" name="orderBy" value="date" /><fmt:message key="Sort by Date"/> </th>
       <th> <input type="radio" name="orderBy" value="time"  /><fmt:message key="Sort by time"/> </th>
       <th> <input type="radio" name="orderBy" value="movie"  /><fmt:message key="Sort by movie"/></th>

   <div>
    <th>
     <input type="radio" name="orderBy" value="number_available_seats"  /><fmt:message key="Sort by number of available seats"/>
    <input type="checkbox" name="number_available_seats" value = "true"><fmt:message key="Filter by number of available seats"/></th>
   </div>
       </tr>
      <input type="submit" value="Submit" />
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
                       <td>
                    <div>
                        <button> <a href="${pageContext.request.contextPath}/admin/update-session?id=${sessionAdminDto.id}"><fmt:message key="Edit"/></a>
                        </div>
                        <div>
                            <form method="post" action="${pageContext.request.contextPath}/schedule/delete" >
                                 <class="form-group">
                                 <input hidden type="number" name="id" value="${sessionAdminDto.id}"/>
                                 <input class="btn btn-primary" type="submit" value="<fmt:message key="delete"/>">
                              </form>
                        </div>
                    </td>
                </tr>
            </c:forEach>
        </c:otherwise>
    </c:choose>

    </tbody>


</table>

<br>
<div>
<button> <a href="${pageContext.request.contextPath}/admin/add-session"><fmt:message key="Add new session"/></a>
</div>
<hr>
<button> <a href="/cinema"> <fmt:message key="To main page"/></a>
<button> <a href="${pageContext.request.contextPath}/admin/analise"> <fmt:message key="Analise"/></a>
</body>
</html>