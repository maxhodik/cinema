package command;

import exceptions.UserAlreadyExistException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import persistance.ConnectionPoolHolder;
import service.UserService;

import javax.servlet.http.HttpServletRequest;

public class RegisterCommand extends MultipleMethodCommand{
    private static final Logger LOGGER = LogManager.getLogger(RegisterCommand.class);

    private UserService userService;

    public RegisterCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String performGet(HttpServletRequest request) {
        return "/WEB-INF/register.jsp";
    }

    @Override
    public String performPost(HttpServletRequest request) {
        String name = request.getParameter("name");
        String password = request.getParameter("pass");
        try {
            userService.create(name, password);
        } catch (UserAlreadyExistException e) {
            //todo handle exception --> add message on the register page
            LOGGER.info("User already exist with name=" + name);
            return "redirect:register";
        }

        return "redirect:index.jsp";
    }

}
