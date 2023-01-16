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


<c:import url="head-admin.jsp"/>

    <title>Cinema</title>
</head>
<body>
<c:if test="${cantUpdate == true}">
    <label class="alert alert-danger"> <fmt:message key="alert.session.cant.update"/></label>
</c:if>
<c:if test="${cantEdit == true}">
    <label class="alert alert-info"> <fmt:message key="alert.session.cant.edit"/></label>
</c:if>
 <div class="container-fluid">
              <a class="btn btn-secondary" href="${pageContext.request.contextPath}/schedule"><fmt:message key="button.schedule"/></a>

 </div>
</body>