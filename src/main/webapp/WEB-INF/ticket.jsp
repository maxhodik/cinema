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
<div class="container">

<div class="form-group">
<label for="Login"> Login: ${orderDto.login}</label>
<br>
<label for="Date"> Date: ${sessionDto.date} </label>
<br>
<label for="Time"> Time: ${sessionDto.time} </label>
<br>
<label for="Movie"> Movie: ${sessionDto.movieName} </label>
<br>
<label for="Number of tickets"> Number of tickets: ${orderDto.numberOfSeats} </label>
<br>
<label for="Price"> Price: ${orderDto.price}</label>
<br>
<label for="Count"> Count: ${orderDto.count}</label>
<br>
<label for="session"> session:${sessionDto.id} </label>
<br>

</div>
</div>
<br>

<button> <a href="/cinema"> <fmt:message key="To main page"/></a>
</body>
</html>