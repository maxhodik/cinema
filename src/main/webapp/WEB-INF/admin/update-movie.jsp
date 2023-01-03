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
                  <a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}/admin/movie"><fmt:message key="admin menu"/> </a>
                  </li>
                  <li class="nav-item">
                  <a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}/admin/analise"> <fmt:message key="Analise"/></a>
                  </li>
                </ul>
                <form>
                 <class="nav-item dropdown">
                      <a class="nav-link dropdown-toggle"  id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        <fmt:message key="label.language"/>
                       </a>
                      <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="?lang=en"><fmt:message key="label.lang.en"/></a></li>
                        <li><a class="dropdown-item" href="?lang=ru"><fmt:message key="label.lang.ru"/></a></li>
                       </ul>

                </form>
              </div>
            </div>
          </nav>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cinema</title>
    </head>
<h1><fmt:message key="Update movie"/></h1><br/>

<c:if test="${sessionScope.exception == true}">
    <label class="alert alert-info"> <fmt:message key="alert.user.already.exist"/></label>
</c:if>
<c:remove var="exception" scope="session"/>

<c:if test="${errors}">
    <p>Not Valid</p>
</c:if>
< id:${movie.id} Name:${movie.name}>
<form method="post" action="${pageContext.request.contextPath}/admin/movie/update-movie">
    <label for="name"> <fmt:message key="Name"/></label>
    <input hidden type="number" name="id" value="${movie.id}"/>
    <input type="text"  name="name"><br>
    <input class="button" type="submit" value="<fmt:message key="Update"/>">
</form>


<hr>
<button> <a href="/cinema"> <fmt:message key="To main page"/></a>
</body>
</html>