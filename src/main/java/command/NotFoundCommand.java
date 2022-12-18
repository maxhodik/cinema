package command;

import javax.servlet.http.HttpServletRequest;

public class NotFoundCommand extends MultipleMethodCommand{
    @Override
    public String performGet(HttpServletRequest request) {
        throw new RuntimeException("NOT FOUND");
    }

    @Override
    public String performPost(HttpServletRequest request) {
        throw new RuntimeException("NOT FOUND");
    }


}
