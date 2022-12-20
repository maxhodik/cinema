package command;

import javax.servlet.http.HttpServletRequest;

public class LogOutCommand implements Command{

    @Override
    public String execute(HttpServletRequest request) {
       request.getSession().invalidate();
       return "redirect:index.jsp";
    }
}
