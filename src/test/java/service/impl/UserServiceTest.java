package service.impl;


import dao.impl.SqlUserDao;
import entities.Role;
import entities.User;
import exceptions.DBException;
import exceptions.EntityAlreadyExistException;
import exceptions.UserNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import service.impl.PasswordEncoderService;
import service.impl.UserServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    private final static User USER = User.builder().id(0).login("test").role(Role.USER).build();
    public static final String ENCODED_PASSWORD = "password";
    private final static User EXPECTED_USER = User.builder().id(0).login("test").password(ENCODED_PASSWORD).role(Role.USER).build();

    private static final String NAME = "test";
    private static final String PASSWORD = "test";
    private final SqlUserDao userDAO = mock(SqlUserDao.class);
    private final PasswordEncoderService passwordEncoderService = mock(PasswordEncoderService.class);
    private final UserServiceImpl userService = new UserServiceImpl(userDAO, passwordEncoderService);

    @Test
    public void saveNewUser() throws EntityAlreadyExistException, DBException {
        USER.setPassword(PASSWORD);
        when(userDAO.create(any(User.class))).thenReturn(true);
        when(userDAO.findEntityByLogin(anyString())).thenReturn(null);
        when(passwordEncoderService.encode(anyString())).thenReturn(ENCODED_PASSWORD);

        User actual = userService.create(USER.getLogin(), USER.getPassword());

        verify(userDAO, times(1)).findEntityByLogin(USER.getLogin());
        verify(userDAO, times(1)).create(EXPECTED_USER);
        verify(passwordEncoderService, times(1)).encode(PASSWORD);
        assertEquals(EXPECTED_USER.getPassword(), actual.getPassword());
        assertEquals(EXPECTED_USER, actual);
    }

    @Test
    public void createNewUserIfExistInDataBase() throws EntityAlreadyExistException {
        when(userDAO.create(any(User.class))).thenThrow(EntityAlreadyExistException.class);
        EntityAlreadyExistException thrown = Assertions.assertThrows(EntityAlreadyExistException.class, () ->
                userService.create(NAME, PASSWORD));

    }

    @Test
    public void createNewUserIfExistInDataBaseControlInService() throws EntityAlreadyExistException {

        when(userDAO.findEntityByLogin(anyString())).thenReturn(USER);
        EntityAlreadyExistException thrown = Assertions.assertThrows(EntityAlreadyExistException.class, () ->
                userService.create(NAME, PASSWORD));

        Assertions.assertEquals("User already exists", thrown.getMessage());
    }

    @Test
    public void loginSuccess () throws UserNotFoundException {
        when(userDAO.findEntityByLogin(anyString())).thenReturn(EXPECTED_USER);
        when(passwordEncoderService.encode(anyString())).thenReturn(ENCODED_PASSWORD);

        User actual = userService.login(EXPECTED_USER.getLogin(), EXPECTED_USER.getPassword());
        verify(userDAO, times(1)).findEntityByLogin(EXPECTED_USER.getLogin());
        verify(passwordEncoderService, times(1)).encode(ENCODED_PASSWORD);
        assertEquals(EXPECTED_USER, actual);
    }

@Test
    public void loginUnSuccessWrongName () {
    when(userDAO.findEntityByLogin(anyString())).thenReturn(null);
    when(passwordEncoderService.encode(anyString())).thenReturn(ENCODED_PASSWORD);
    UserNotFoundException thrown = Assertions.assertThrows(UserNotFoundException.class, () ->
            userService.login(NAME, PASSWORD));
}
    @Test
    public void loginUnSuccessWrongPassword () {
        when(userDAO.findEntityByLogin(anyString())).thenReturn(EXPECTED_USER);
        when(passwordEncoderService.encode(anyString())).thenReturn(PASSWORD);
        UserNotFoundException thrown = Assertions.assertThrows(UserNotFoundException.class, () ->
                userService.login(NAME, PASSWORD));
    }
}