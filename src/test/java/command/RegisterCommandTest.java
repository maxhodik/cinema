package command;

import exceptions.EntityAlreadyExistException;
import org.junit.jupiter.api.Test;
import service.UserService;
import web.form.UserForm;
import web.form.validation.UserFormValidator;
import web.handler.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.logging.Handler;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegisterCommandTest {
    private static final String NAME = "name";
    private static final String PASSWORD = "password";
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final UserService userService = mock(UserService.class);
    private final UserFormValidator userFormValidator = mock(UserFormValidator.class);
    private final UserForm userForm = mock(UserForm.class);
    private final HttpSession httpSession = mock(HttpSession.class);
    private final ExceptionHandler handler = mock(ExceptionHandler.class);
    private RegisterCommand registerCommand = new RegisterCommand(userService, userFormValidator);


    @Test
    void performGet() {
        when(request.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute("name")).thenReturn(null);
        String path = registerCommand.performGet(request);
        assertEquals("WEB-INF/register.jsp", path);
    }

    @Test
    void performGetUserNotNull() {
        when(request.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute("name")).thenReturn(NAME);
        String path = registerCommand.performGet(request);
        assertEquals("redirect:logout", path);
    }

    @Test
    void performPostUnsuccessfulValidate() throws EntityAlreadyExistException {
        when(request.getParameter("name")).thenReturn(NAME);
        when(request.getParameter("pass")).thenReturn(PASSWORD);
        UserForm userForm = new UserForm(NAME, PASSWORD);
        when(userFormValidator.validate(userForm)).thenReturn(true);
        String path = registerCommand.performPost(request);
        verify(request).setAttribute("errors", true);
        assertEquals("WEB-INF/register.jsp", path);
    }

    @Test
    void performPostSuccessfulValidate() throws EntityAlreadyExistException {
        when(request.getParameter("name")).thenReturn(NAME);
        when(request.getParameter("pass")).thenReturn(PASSWORD);
        UserForm userForm = new UserForm(NAME, PASSWORD);
        when(userFormValidator.validate(userForm)).thenReturn(false);
        String path = registerCommand.performPost(request);
        verify(userService).create(NAME, PASSWORD);
        assertEquals("redirect:index.jsp", path);
    }

    @Test
    void performPostSuccessfulCreateException() throws EntityAlreadyExistException {
        when(request.getParameter("name")).thenReturn(NAME);
        when(request.getParameter("pass")).thenReturn(PASSWORD);
        UserForm userForm = new UserForm(NAME, PASSWORD);
        when(userFormValidator.validate(userForm)).thenReturn(false);
        when(userService.create(NAME, PASSWORD)).thenThrow(EntityAlreadyExistException.class);
        when(request.getSession()).thenReturn(httpSession);
        httpSession.setAttribute("exception", true);
        String path = registerCommand.performPost(request);
        assertEquals("redirect:register", path);
    }
}