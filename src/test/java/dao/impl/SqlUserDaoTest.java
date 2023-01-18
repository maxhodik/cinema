package dao.impl;

import dao.UserDao;
import entities.Role;
import entities.User;
import exceptions.EntityAlreadyExistException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistance.ConnectionWrapper;
import persistance.TransactionManagerWrapper;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SqlUserDaoTest {
    private final static User USER = User.builder().id(0).login("test").role(Role.USER).build();
    private ConnectionWrapper con = mock(ConnectionWrapper.class);
    private PreparedStatement stmt = mock(PreparedStatement.class);
    private UserDao userDao = new SqlUserDao();

    @BeforeEach
    public void setUp() {
        ThreadLocal<ConnectionWrapper> th = new ThreadLocal<>();
        th.set(con);
        TransactionManagerWrapper.setThreadLocal(th);
    }


    @Test
    void createUserException() throws SQLException {
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenThrow(SQLIntegrityConstraintViolationException.class);
        EntityAlreadyExistException thrown = Assertions.assertThrows(EntityAlreadyExistException.class, () ->
                userDao.create(USER));
        assertEquals("User already exists, login: test", thrown.getMessage());
    }
}