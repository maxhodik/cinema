<%@ tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="param" required="true"%>
<%@ attribute name="value" required="true"%>

<c:set var="queryString" value="${param.queryString}"/>

<c:if test="${not empty queryString}">
  <c:set var="queryString" value="${queryString}&"/>
</c:if>

<c:if test="${empty queryString}">
  <c:set var="queryString" value="?"/>
</c:if>



<c:set var="queryString" value="${queryString}${param}=${value}"/>

${queryString}