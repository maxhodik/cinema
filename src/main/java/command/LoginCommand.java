package command;


import entities.User;
import exceptions.UserNotFoundException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.UserService;
import web.handler.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

public class LoginCommand extends MultipleMethodCommand {
    private UserService userService;
    private static final Logger LOGGER = LogManager.getLogger(RegisterCommand.class);

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String performGet(HttpServletRequest request) {
        return "/WEB-INF/login.jsp";
    }

    @Override
    public String performPost(HttpServletRequest request) {
        request.getSession().invalidate();
        String name = request.getParameter("name");
        String password = request.getParameter("pass");
        try {
            userService.login(name, password);

        } catch (UserNotFoundException e) {
            ExceptionHandler handler = new ExceptionHandler(e, "login","redirect");
            LOGGER.info("User not found with name=" + name);
            return handler.handling(request);
        }
        User user = userService.findEntityByLogin(name);
        request.getSession().setAttribute("name", user.getLogin());
        request.getSession().setAttribute("role", user.getRole());
        request.getSession().setAttribute("success", true);
        LOGGER.info("User logged success role: " + user.getRole());
        return "redirect:index.jsp";
    }


}


