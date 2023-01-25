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
              <a class="navbar-brand"href="${pageContext.request.contextPath}/schedule"><fmt:message key="button.schedule"/></a>
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
                       <a class="btn btn-outline-secondary" href="logout" role="button"><fmt:message key="label.logout"/></a>
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

                             </div>
                           </div>
                         </nav>
                       <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                       <title>Cinema</title>
                   </head>
<html lang="${param.lang}">
   <style type="text/css">
 body{  background-image: url(https://phonoteka.org/uploads/posts/2022-01/1642994019_18-phonoteka-org-p-kino-fon-dlya-prezentatsii-20.jpg)
   </style>
<body>

<div class="container col-3">
<h1><fmt:message key="button.login"/></h1><br/>
<c:if test="${sessionScope.exception == true}">
    <label class="alert alert-info"> <fmt:message key="alert.wrong.login.or.password"/></label>
</c:if>
<c:remove var="exception" scope="session"/>
<!--<c:if test="${sessionScope.success == true}">
    <label class="alert alert-info"> <fmt:message key="alert.success.sing.up"/></label>

</c:if> --!>
 <c:remove var="success" scope="session"/>

<form method="post" action="${pageContext.request.contextPath}/login">
 <div class="md-3">
     <label for="name" class="form-label"><fmt:message key="label.login"/></label>
     <input id="name" class="form-control" type="text" required pattern="^[A-Za-z0-9_-]{3,16}$" name="name"><br/>
     <label for="password"class="form-label"> <fmt:message key="label.password"/></label>
     <input id="password" class="form-control" type="password" required pattern="^[A-Za-z0-9_-]{5,18}$" name="pass"><br/><br/>
     <input class="btn btn-outline-primary" type="submit" value="<fmt:message key="button.login"/>">
    <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}" role="button"><fmt:message key="button.back"/></a>



      </div>
</form>

</div>
</body>
</html>