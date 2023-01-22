package web.tag;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class QueryStringTag extends TagSupport {

    private String value;

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int doStartTag() {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        String queryString = request.getQueryString();
        if (value == null && (queryString == null || queryString.isBlank())) {
            return 0;
        }
        queryString = clearLang(queryString);
        if (queryString == null || queryString.isBlank()) {
            queryString = "?lang=" + value;
        } else {
            if (value != null) {
                queryString = "?" + queryString + "&lang=" + value;
            } else {
                queryString = "?" + queryString;
            }
        }
        JspWriter out = pageContext.getOut();
        try {
            out.print(queryString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return  SKIP_BODY;
    }

    private String clearLang(String queryString) {
        if (queryString == null) {
            return null;
        }
        String[] split = queryString.split("&");
        String[] withoutLang = Arrays.stream(split).filter(param -> !param.contains("lang")).toArray(String[]::new);
        String newQueryString = String.join("&", withoutLang);
        return newQueryString;
    }
}