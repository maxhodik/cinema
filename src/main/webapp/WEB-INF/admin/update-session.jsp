<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
            <%@ page isELIgnored="false" %>
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet"
                    integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
                    crossorigin="anonymous">
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                    integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
                    crossorigin="anonymous"></script>

                <c:if test="${not empty param.lang}">
                    <fmt:setLocale value="${param.lang}" scope="session" />
                </c:if>

                <fmt:setBundle basename="message" />

                <html lang="${param.lang}">

                <head>
                    <c:import url="head-admin.jsp" />
                    <title>Cinema</title>
                </head>
                <c:if test="${cantUpdate == true}">
                    <label class="alert alert-info">
                        <fmt:message key="alert.session.cant.update" />
                    </label>
                    <c:remove var="exception" scope="session" />
                </c:if>
                <c:if test="${cantEdit == true}">
                    <label class="alert alert-info">
                        <fmt:message key="alert.session.cant.edit" />
                    </label>
                    <c:remove var="exception" scope="session" />
                </c:if>

                <c:if test="${movieDoesntExist == true}">
                    <label class="alert alert-info">
                        <fmt:message key="alert.session.movie.not.exist" />
                    </label>
                    <c:remove var="exception" scope="session" />
                </c:if>
                <div class="container col-11">

                        <c:if test="${errors}">
                            <label class="alert alert-info">
                                <fmt:message key="alert.session.form.not.valid" />
                         </label>
                            <c:remove var="errors" scope="session" />
                        </c:if>
                    <body>

                        <table class="table table-striped table-responsive-md btn-table table-bordered table-hover">
                            <thead class="thead-dark">
                                <tr>
                                    <th scope="col">
                                        <fmt:message key="label.date" />"${sessionDto.date}"
                                    </th>
                                    <th scope="col">
                                        <fmt:message key="label.time" />
                                    </th>
                                    <th scope="col">
                                        <fmt:message key="label.movie" />
                                    </th>
                                    <th scope="col">
                                        <fmt:message key="label.available_seats" />"${sessionDto.numberOfSeats}"
                                    </th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:choose>
                                    <c:when test="${schedule.isEmpty()}">
                                        <h2>
                                            <fmt:message key="alert.orders.list.is.empty" />
                                        </h2>
                                    </c:when>
                                    <c:otherwise>
                                        <tr>
                                            <form method="post"
                                                action="${pageContext.request.contextPath}/admin/update-session">
                                                <input hidden type="number" name="id" value="${sessionDto.id}" />
                                                <td><input type="date" name="date"
                                                        placeholder="${sessionDto.date}"><br /></td>
                                                <td><input type="time" name="time"
                                                        placeholder="${sessionDto.time}"><br /></td>
                                                <td>
                                                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
                                                        role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                                        <fmt:message key="label.movie" />
                                                    </a>
                                                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                                        <c:choose>
                                                            <c:when test="${schedule.isEmpty()}">
                                                                <h2>
                                                                    <fmt:message key="alert.orders.list.is.empty" />
                                                                </h2>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <c:forEach var="movieDto" items="${movieDto}">
                                                                    <li> <input type="radio" name="movieName"
                                                                            value="${movieDto}">${movieDto}</li>
                                                                </c:forEach>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </ul>
                                                </td>
                                                <td><input type="number" name="seats" min="1"
                                                        placeholder="${sessionDto.numberOfSeats}"><br /></td>
                                                <input class="btn btn-primary" type="submit" value="<fmt:message key="button.submit" />">
                                            </form>
                                        </tr>
                                    </c:otherwise>
                                </c:choose>
                            </tbody>
                        </table>
                    </body>
                </div>
                </html>