package command;

import entities.Role;
import entities.User;
import exceptions.UserNotFoundException;
import org.junit.jupiter.api.Test;
import service.UserService;
import web.form.UserForm;
import web.form.validation.UserFormValidator;
import web.handler.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.net.http.HttpRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginCommandTest {
    private static final String NAME = "test";
    private static final String PASSWORD = "password";
    private final static User USER = User.builder().id(0).login("test").password(PASSWORD).role(Role.USER).build();
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final UserService userService = mock(UserService.class);
    private final User user= mock(User.class);
    private final HttpSession httpSession = mock(HttpSession.class);
    private final ExceptionHandler handler = mock(ExceptionHandler.class);
    private final LoginCommand loginCommand = new LoginCommand(userService);
    @Test
    void performGet() {
        String path = loginCommand.performGet(request);
        assertEquals("/WEB-INF/login.jsp", path);
    }

    @Test
    void performPost() throws UserNotFoundException {
        //given
        when(request.getSession()).thenReturn(httpSession);
        when(request.getParameter("name")).thenReturn(NAME);
        when(request.getParameter("pass")).thenReturn(PASSWORD);
        when(userService.login(NAME,PASSWORD)).thenReturn(USER);
        when(userService.findEntityByLogin(NAME)).thenReturn(USER);
        when(user.getLogin()).thenReturn(NAME);
        when(user.getRole()).thenReturn(Role.USER);

        //when
        String path= loginCommand.performPost(request);

        //then
        verify(httpSession).invalidate();
        verify(httpSession).setAttribute("name",NAME);
        verify(httpSession).setAttribute("role", Role.USER);
        verify(httpSession).setAttribute("success", true);
        assertEquals("redirect:index.jsp", path);
    }    @Test
    void performPostNotFound() throws UserNotFoundException {
        //given
        when(request.getSession()).thenReturn(httpSession);
        when(request.getParameter("name")).thenReturn(NAME);
        when(request.getParameter("pass")).thenReturn(PASSWORD);
        when(userService.login(NAME,PASSWORD)).thenThrow(UserNotFoundException.class);
            //when
        String path= loginCommand.performPost(request);
        //then
        verify(httpSession).invalidate();
        assertEquals("redirect:login", path);
    }
}