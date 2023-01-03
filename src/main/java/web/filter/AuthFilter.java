package web.filter;

import entities.Role;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession();
        String path = req.getRequestURI();

        boolean isAccessedRequest = path.contains("login") || path.contains("register")
                || path.contains("schedule") || path.contains("index");

        if (session.getAttribute("name") == null && !isAccessedRequest) {
            resp.sendRedirect("/cinema/index.jsp");
            return;
        }
        if (isAccessedRequest) {
            filterChain.doFilter(request, response);
            return;
        }
        if (session.getAttribute("name") != null) {
            if (path.contains("admin") && session.getAttribute("role") != Role.ADMIN) {
                //todo create access denied page
                resp.sendRedirect("/schedule-admin");
                return;
            } else if (path.matches(".*/cinema/.*")) {
                filterChain.doFilter(request, response);
            } else {
                //todo 404 page
                resp.sendRedirect("/cinema");
            }
        }

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
