<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>404</title>
</head>
<body>
<c:if test="${not empty param.lang}">
    <fmt:setLocale value="${param.lang}" scope="session"/>
</c:if>

<fmt:setBundle basename="message"/>

 <style type="text/css">
            body {

              background-image: url(https://phonoteka.org/uploads/posts/2022-01/1642994024_51-phonoteka-org-p-kino-fon-dlya-prezentatsii-57.jpg)
            }
          </style>


<h1>404</h1>
<a aria-pressed="true" class="btn button" role="button" href="${pageContext.request.contextPath}/index.jsp">
<fmt:message key="button.main"/></a
</body>
</html>
