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
                      <td><input id="name" type="text"  name="movieName"><br/></td>
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