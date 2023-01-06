package web.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class QueryStringTag extends TagSupport {

    private String paramName;
    private String paramValue;

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
            String queryString =  request.getQueryString();
//            if (queryString.contains(paramName)){
//                String[] split = queryString.split("&");
//                List<String> strings = Arrays.asList(split);
//                for (String string : strings) {
//
//                }
//            }
            if (queryString == null || queryString.isEmpty()) {
                String newQuery ="?" +  paramName + "=" + paramValue;
                request.setAttribute(paramName, "dasdfaetqemdas dada");
                out.print(newQuery);
            } else {
                out.print("?" + queryString + "&" + paramName + "=" + paramValue);
            }
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        paramName = null;
        paramValue = null;
        return super.doEndTag();
    }
}