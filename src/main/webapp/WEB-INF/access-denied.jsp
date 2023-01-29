
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Access denied</title>
</head>

<body>
<c:if test="${not empty param.lang}">
    <fmt:setLocale value="${param.lang}" scope="session"/>
</c:if>

<fmt:setBundle basename="message"/>
<c:if test="${cruiseNotFound}">
    <h1><fmt:message key="alert.wrong.input.data"/></h1>
</c:if>
<c:if test="${notAllData}">
    <h1><fmt:message key="alert.not.enough.data"/></h1>
</c:if>
<h1>AccessDenied</h1>
<a href="${pageContext.request.contextPath}/main" class="button"></a>
</body>
</html>
