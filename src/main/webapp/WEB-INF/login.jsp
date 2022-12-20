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

<h1><fmt:message key="button.login"/></h1><br/>
<c:if test="${sessionScope.exception == true}">
    <label class="alert alert-info"> <fmt:message key="alert.wrong.password.or.login"/></label>
</c:if>
<c:remove var="exception" scope="session"/>
<c:if test="${sessionScope.success == true}">
    <label class="alert alert-info"> <fmt:message key="alert.success.sing.up"/></label>
</c:if>
<c:remove var="success" scope="session"/>

<form method="post" action="${pageContext.request.contextPath}/login">
    <label for="name"> <fmt:message key="label.login"/></label>
    <input id="name" type="text" name="name"><br/>
    <label for="password"> <fmt:message key="label.password"/></label>
    <input id="password" type="password" name="pass"><br/><br/>
    <input class="button" type="submit" value="<fmt:message key="button.login"/>">
</form>
<br/>
<button>
<a href="${pageContext.request.contextPath}"><fmt:message key="button.back"/> </a>
<button>
<a href="logout"><fmt:message key="Logout"/> </a>

</body>
</html>