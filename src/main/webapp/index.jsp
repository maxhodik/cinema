<%@ page contentType="text/html;charset=UTF-8" language="java" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
      <%@ page isELIgnored="false" %>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
          integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
          crossorigin="anonymous"></script>
     <!--   <c:if test="${not empty param.lang}">
          <fmt:setLocale value="${param.lang}" scope="session" />
        </c:if> --!>
         <c:if test="${not empty lang}">
          <fmt:setLocale value="${lang}" scope="session" />
        </c:if>

        <fmt:setBundle basename="message" />

        <html lang="${param.lang}">

        <head>
          <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

          <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container-fluid">
              <a class="navbar-brand" href="${pageContext.request.contextPath}/schedule">
                <fmt:message key="button.schedule" />
              </a>
              <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false"
                aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
              </button>
              <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                  <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}/login">
                      <fmt:message key="button.login" />
                    </a>
                  </li>
                  <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}/register">
                      <fmt:message key="button.registration" />
                    </a>
                  </li>
                  <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                      data-bs-toggle="dropdown" aria-expanded="false">
                      <fmt:message key="label.language" />
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                      <li><a class="dropdown-item" href="?lang=en">
                          <fmt:message key="label.lang.en" />
                        </a></li>
                      <li><a class="dropdown-item" href="?lang=ua">
                          <fmt:message key="label.lang.ua" />
                        </a></li>
                    </ul>
                  </li>
                </ul>

              </div>
            </div>
          </nav>
          <title>Cinema</title>
          <style type="text/css">
            body {

              background-image: url(https://phonoteka.org/uploads/posts/2022-01/1642994024_51-phonoteka-org-p-kino-fon-dlya-prezentatsii-57.jpg)
            }
          </style>

        </head>

        <body>

          <h2>
            <fmt:message key="label.hello.cinema" />
          </h2>
        </body>

        </html>