<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<c:import url="head-admin.jsp"/>
<body>
<div class="container col-3">
<h1><fmt:message key="button.new_movie"/></h1><br/>

<c:if test="${sessionScope.exception == true}">
    <label class="alert alert-info"> <fmt:message key="alert.user.already.exist"/></label>
</c:if>
<c:remove var="exception" scope="session"/>

<c:if test="${errors}">
    <p>movie_name_not_valid</p>
</c:if>
<form method="post" action="${pageContext.request.contextPath}/admin/movie/add-movie">
    <label for="name"> <fmt:message key="label.movie"/></label>
    <input type="text"  name="name"><br>
    <input class="button" type="submit" value="<fmt:message key="label.add"/>">
</form>

<div>

</div>
</div>
</html>