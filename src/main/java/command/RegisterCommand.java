package command;

import exceptions.UserAlreadyExistException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.UserService;
import web.form.UserForm;
import web.form.validation.UserFormValidator;
import web.form.validation.Validator;

import javax.servlet.http.HttpServletRequest;

public class RegisterCommand extends MultipleMethodCommand{
    private static final Logger LOGGER = LogManager.getLogger(RegisterCommand.class);

    private UserService userService;
    private Validator<UserForm> userFormValidator;

    public RegisterCommand(UserService userService, Validator<UserForm> userFormValidator) {
        this.userService = userService;
        this.userFormValidator = userFormValidator;
    }

    @Override
    public String performGet(HttpServletRequest request) {
        return "/WEB-INF/register.jsp";
    }

    @Override
    public String performPost(HttpServletRequest request) {
        String name = request.getParameter("name");
        String password = request.getParameter("pass");
        UserForm userForm = new UserForm(name,password);
        if (userFormValidator.validate(userForm)) {
            request.setAttribute("errors", true);
            return "WEB-INF/register.jsp";
        }
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
