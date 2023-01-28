package dao.impl;

import dao.Constants;
import dao.UserDao;
import dao.maper.UserMapper;
import entities.Role;
import entities.User;
import exceptions.EntityAlreadyExistException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistance.ConnectionWrapper;
import persistance.TransactionManagerWrapper;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SqlUserDaoTest {
    private static final User EXPECTED_USER = User.builder().id(0).login("test").password("password").role(Role.USER).build();
    private static final List<User> USERS = List.of(EXPECTED_USER);
    private ConnectionWrapper con = mock(ConnectionWrapper.class);
    private PreparedStatement stmt = mock(PreparedStatement.class);
    private Statement statement = mock(Statement.class);

    private UserDao userDao = new SqlUserDao();

    @BeforeEach
    public void setUp() {
        ThreadLocal<ConnectionWrapper> th = new ThreadLocal<>();
        th.set(con);
        TransactionManagerWrapper.setThreadLocal(th);
    }

    @AfterAll
    static void afterAll() {
        TransactionManagerWrapper.setThreadLocal(new ThreadLocal<>());
    }

    @Test
    void createUserException() throws SQLException {
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenThrow(SQLIntegrityConstraintViolationException.class);
        EntityAlreadyExistException thrown = Assertions.assertThrows(EntityAlreadyExistException.class, () ->
                userDao.create(EXPECTED_USER));
    }

    @Test
    void createUserSuccess() throws SQLException, EntityAlreadyExistException {
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(1);
        assertEquals(true, userDao.create(EXPECTED_USER));
    }

    @Test
    void createUserInSuccess() throws SQLException, EntityAlreadyExistException {
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(0);

        assertEquals(false, userDao.create(EXPECTED_USER));
    }

    @Test
    void deleteUserException() throws SQLException {
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenThrow(RuntimeException.class);
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () ->
                userDao.delete(EXPECTED_USER));
    }

    @Test
    void deleteUserSuccess() throws SQLException, EntityAlreadyExistException {
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(1);
        assertEquals(true, userDao.delete(EXPECTED_USER));
    }

    @Test
    void deleteUserInSuccess() throws SQLException, EntityAlreadyExistException {
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(0);
        assertEquals(false, userDao.delete(EXPECTED_USER));
    }

    @Test
    void updateUserException() throws SQLException {
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenThrow(SQLIntegrityConstraintViolationException.class);
        EntityAlreadyExistException thrown = Assertions.assertThrows(EntityAlreadyExistException.class, () ->
                userDao.update(EXPECTED_USER));
    }

    @Test
    void updateUserRunTimeException() throws SQLException {
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenThrow(RuntimeException.class);
        Assertions.assertThrows(RuntimeException.class, () ->
                userDao.update(EXPECTED_USER));
    }

    @Test
    void updateUserSuccess() throws SQLException, EntityAlreadyExistException {
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(1);
        assertEquals(true, userDao.update(EXPECTED_USER));
    }

    @Test
    void updateUserInSuccess() throws SQLException, EntityAlreadyExistException {
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(0);
        assertEquals(false, userDao.update(EXPECTED_USER));
    }

    @Test
    void findAllException() throws SQLException {
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenThrow(RuntimeException.class);
        Assertions.assertThrows(RuntimeException.class, () ->
                userDao.findAll());
    }

    @Test
    void findAll() throws SQLException {
        when(con.statement()).thenReturn(stmt);
        ResultSet rs = mock(ResultSet.class);
        UserMapper mapper = mock(UserMapper.class);
        when(stmt.executeQuery(Constants.FIND_ALL_USERS)).thenReturn(rs);
        prepareResultSet(rs);
        when(mapper.extractFromResultSet(rs)).thenReturn(EXPECTED_USER);
        List<User> actual = userDao.findAll();
        assertEquals(1, actual.size());
        assertNotNull(rs);
        assertEquals(EXPECTED_USER, actual.get(0));
    }


    @Test
    void findAllByRole() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(con.prepareStatement(Constants.FIND_ALL_USERS_BY_ROLE)).thenReturn(stmt);
        UserMapper mapper = mock(UserMapper.class);
        when(stmt.executeQuery()).thenReturn(rs);
        prepareResultSet(rs);
        when(mapper.extractFromResultSet(rs)).thenReturn(EXPECTED_USER);
        List<User> actual = userDao.findAllByRole(Role.USER);
        assertEquals(1, actual.size());
        assertNotNull(rs);
        assertEquals(EXPECTED_USER, actual.get(0));
    }

    @Test
    void findAllByRoleException() throws SQLException {
        when(con.prepareStatement(Constants.FIND_ALL_USERS_BY_ROLE)).thenReturn(stmt);
        when(stmt.executeQuery()).thenThrow(RuntimeException.class);
        Assertions.assertThrows(RuntimeException.class, () ->
                userDao.findAllByRole(Role.USER));
    }

    @Test
    void findEntityById() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(con.prepareStatement(Constants.FIND_USERS_BY_ID)).thenReturn(stmt);
        UserMapper mapper = mock(UserMapper.class);
        when(stmt.executeQuery()).thenReturn(rs);
        prepareResultSet(rs);
        when(mapper.extractFromResultSet(rs)).thenReturn(EXPECTED_USER);
        User actual = userDao.findEntityById(0);
        assertNotNull(rs);
        assertEquals(EXPECTED_USER, actual);
    }

    @Test
    void findEntityByIdException() throws SQLException {
        when(con.prepareStatement(Constants.FIND_USERS_BY_ID)).thenReturn(stmt);
        when(stmt.executeQuery()).thenThrow(RuntimeException.class);
        Assertions.assertThrows(RuntimeException.class, () ->
                userDao.findEntityById(0));
    }

    @Test
    void findEntityByLogin() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(con.prepareStatement(Constants.FIND_USER_BY_LOGIN)).thenReturn(stmt);
        UserMapper mapper = mock(UserMapper.class);
        when(stmt.executeQuery()).thenReturn(rs);
        prepareResultSet(rs);
        when(mapper.extractFromResultSet(rs)).thenReturn(EXPECTED_USER);
        User actual = userDao.findEntityByLogin("test");
        assertNotNull(rs);
        assertEquals(EXPECTED_USER, actual);
    }

    @Test
    void findEntityByLoginException() throws SQLException {
        when(con.prepareStatement(Constants.FIND_USER_BY_LOGIN)).thenReturn(stmt);
        when(stmt.executeQuery()).thenThrow(RuntimeException.class);
        Assertions.assertThrows(RuntimeException.class, () ->
                userDao.findEntityByLogin("test"));
    }




    private static void prepareResultSet(ResultSet resultSet) throws SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("users.id")).thenReturn(0);
        when(resultSet.getString("login")).thenReturn("test");
        when(resultSet.getString("password")).thenReturn("password");
        when(resultSet.getString("role")).thenReturn(String.valueOf(Role.USER));
    }
}