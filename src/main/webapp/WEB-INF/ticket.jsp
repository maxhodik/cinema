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
 <style type="text/css">
          body{
          background-image: url(https://phonoteka.org/uploads/posts/2022-01/1642994019_18-phonoteka-org-p-kino-fon-dlya-prezentatsii-20.jpg)
</style>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


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
                </ul>
                <form>
               </li>
                </form>
              </div>
            </div>
          </nav>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cinema</title>
    </head>


<body>
<div class="container col-3" >


<div class="form-group">
<label for="Login"><fmt:message key="label.login"/>: ${orderDto.login}</label>
<br>
<label for="Date"> <fmt:message key="label.date"/>: ${sessionDto.date} </label>
<br>
<label for="Time"> <fmt:message key="label.time"/>: ${sessionDto.time} </label>
<br>
<label for="Movie"> <fmt:message key="label.movie"/>: ${sessionDto.movieName} </label>
<br>
<label for="Number of tickets"><fmt:message key="label.seats"/>: ${orderDto.numberOfSeats} </label>
<br>
<label for="Price"> <fmt:message key="label.price"/>: ${orderDto.price}</label>
<br>
<label for="Count"> <fmt:message key="label.count"/>: ${orderDto.count}</label>
<br>
<label for="session"> session:${sessionDto.id} </label>
<br>

</div>
</div>
</body>
</html>