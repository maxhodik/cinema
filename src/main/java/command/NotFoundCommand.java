package command;

import javax.servlet.http.HttpServletRequest;

public class NotFoundCommand extends MultipleMethodCommand{
    @Override
    public String performGet(HttpServletRequest request) {
        return "/WEB-INF/404.jsp";
    }

    @Override
    public String performPost(HttpServletRequest request) {
        throw new RuntimeException("NOT FOUND");
    }


}
