<%--
  Created by IntelliJ IDEA.
  User: Tima
  Date: 30.01.2020
  Time: 12:54
  To change this template use File | Settings | File Templates.
--%>
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

<figure class="text-center">
    <img src="${pageContext.request.contextPath}/picture/cinema.jpg"/>
</figure>


<h1>404</h1>
<a aria-pressed="true" class="btn button" role="button" href="${pageContext.request.contextPath}/index.jsp"> <fmt:message
        key="button.main"/></a
</body>
</html>
