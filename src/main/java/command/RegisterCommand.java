package command;

import exceptions.DBException;
import exceptions.EntityAlreadyExistException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.UserService;
import web.form.UserForm;
import web.form.validation.Validator;
import web.handler.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Handler;
import java.util.logging.StreamHandler;

public class RegisterCommand extends MultipleMethodCommand {
    private static final Logger LOGGER = LogManager.getLogger(RegisterCommand.class);

    private UserService userService;
    private Validator<UserForm> userFormValidator;


    public RegisterCommand(UserService userService, Validator<UserForm> userFormValidator) {
        this.userService = userService;
        this.userFormValidator = userFormValidator;
    }

    @Override
    public String performGet(HttpServletRequest request) {
        if (request.getSession().getAttribute("name") != null) {
            return "redirect:logout";
        }
        return "WEB-INF/register.jsp";
    }

    @Override
    public String performPost(HttpServletRequest request) {
        String name = request.getParameter("name");
        String password = request.getParameter("pass");
        UserForm userForm = new UserForm(name, password);
        if (userFormValidator.validate(userForm)) {
            request.setAttribute("errors", true);
            return "WEB-INF/register.jsp";
        }
        try {
            userService.create(name, password);
        } catch (EntityAlreadyExistException e) {
            ExceptionHandler handler = new ExceptionHandler(e, "register", "redirect");
            LOGGER.info("User already exist with name=" + name);
            return handler.handling(request);
        }

        return "redirect:index.jsp";
    }

}
