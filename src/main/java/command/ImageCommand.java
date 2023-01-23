package command;

import javax.servlet.http.HttpServletRequest;

public class ImageCommand extends MultipleMethodCommand {
    @Override
    protected String performGet(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String s = requestURI.replaceAll(".*/cinema/", "");
        return s;
    }

    @Override
    protected String performPost(HttpServletRequest request) {
        return null;
    }
}
