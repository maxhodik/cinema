package web.filter;

import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter implements Filter {

    public static final String CONTENT_TYPE = "text/html";
    public static final String ENCODING = "UTF-8";
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        byte[] b = servletRequest.getParameter("name").getBytes(StandardCharsets.ISO_8859_1);
//        new String(b, StandardCharsets.UTF_8);
        servletResponse.setContentType(CONTENT_TYPE);
        servletResponse.setCharacterEncoding(ENCODING);
        servletRequest.setCharacterEncoding(ENCODING);
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
