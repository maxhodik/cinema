package dao.impl;

import dao.Constants;
import dao.HallDao;
import dao.MovieDao;
import dao.maper.HallMapper;
import dao.maper.UserMapper;
import entities.Hall;
import entities.Role;
import entities.User;
import exceptions.EntityAlreadyExistException;
import org.junit.jupiter.api.*;
import persistance.ConnectionWrapper;
import persistance.TransactionManagerWrapper;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SqlHallDaoTest {
    private static final Hall HALL = Hall.builder()
            .id(1)
            .numberSeats(100)
            .numberAvailableSeats(90)
            .numberOfSoldSeats(10).attendance(BigDecimal.valueOf(10.00)).build();
    private ConnectionWrapper con = mock(ConnectionWrapper.class);
    private PreparedStatement stmt = mock(PreparedStatement.class);
    private Statement statement = mock(Statement.class);
    HallMapper mapper = mock(HallMapper.class);
    private HallDao hallDao = new SqlHallDao();


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
    void findAllException() throws SQLException {
        when(con.statement()).thenReturn(stmt);
        when(stmt.executeQuery(Constants.FIND_ALL_HALLS)).thenThrow(RuntimeException.class);
        Assertions.assertThrows(RuntimeException.class, () ->
                hallDao.findAll());
    }

    @Test
    void findAll() throws SQLException {
        when(con.statement()).thenReturn(stmt);
        ResultSet rs = mock(ResultSet.class);
        when(stmt.executeQuery(Constants.FIND_ALL_HALLS)).thenReturn(rs);
        prepareResultSet(rs);
        when(mapper.extractFromResultSet(rs)).thenReturn(HALL);
        List<Hall> actual = hallDao.findAll();
        assertEquals(1, actual.size());
        assertNotNull(rs);
        assertEquals(HALL, actual.get(0));
    }

    @Test
    void findEntityById() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(con.prepareStatement(Constants.FIND_HALL_BY_ID)).thenReturn(stmt);
        when(stmt.executeQuery()).thenReturn(rs);
        prepareResultSet(rs);
        when(mapper.extractFromResultSet(rs)).thenReturn(HALL);
        Hall actual = hallDao.findEntityById(1);
        assertNotNull(rs);
        assertEquals(HALL, actual);
    }

    @Test
    void findEntityByIdException() throws SQLException {
        when(con.prepareStatement(Constants.FIND_HALL_BY_ID)).thenReturn(stmt);
        when(stmt.executeQuery()).thenThrow(RuntimeException.class);
        Assertions.assertThrows(RuntimeException.class, () -> hallDao.findEntityById(1));

    }

    @Test
    void deleteSuccess() throws SQLException {
        when(con.prepareStatement(Constants.DELETE_HALL_BY_ID)).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(1);
        boolean actual = hallDao.delete(HALL);
        assertTrue(actual);
    }

    @Test
    void deleteInSuccess() throws SQLException {
        when(con.prepareStatement(Constants.DELETE_HALL_BY_ID)).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(0);
        boolean actual = hallDao.delete(HALL);
        assertFalse(actual);
    }

    @Test
    void deleteException() throws SQLException {
        when(con.prepareStatement(Constants.DELETE_HALL_BY_ID)).thenReturn(stmt);
        when(stmt.executeUpdate()).thenThrow(RuntimeException.class);
        Assertions.assertThrows(RuntimeException.class, () -> hallDao.delete(HALL));
    }


    @Test
    void create() throws SQLException, EntityAlreadyExistException {
        when(con.prepareStatement(Constants.INSERT_INTO_HALLS)).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(1);
        boolean actual = hallDao.create(HALL);
        assertTrue(actual);
    }

    @Test
    void createInSuccess() throws SQLException, EntityAlreadyExistException {
        when(con.prepareStatement(Constants.INSERT_INTO_HALLS)).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(0);
        boolean actual = hallDao.create(HALL);
        assertFalse(actual);
    }

    @Test
    void createHallException() throws SQLException {
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenThrow(RuntimeException.class);
        Assertions.assertThrows(RuntimeException.class, () ->
                hallDao.create(HALL));

    }

    @Test
    void createAndReturnWithId() throws SQLException {
        when(con.prepareStatement(Constants.INSERT_INTO_HALLS, Statement.RETURN_GENERATED_KEYS)).thenReturn(stmt);
        ResultSet rs = mock(ResultSet.class);
        stmt.executeUpdate();
        when(stmt.getGeneratedKeys()).thenReturn(rs);
        prepareResultSet(rs);
        Hall actual = hallDao.createAndReturnWithId(HALL);
        assertEquals(HALL, actual);
    }

    @Test
    void createAndReturnWithIdSQLException() throws SQLException {
        when(con.prepareStatement(Constants.INSERT_INTO_HALLS, Statement.RETURN_GENERATED_KEYS)).thenReturn(stmt);
        stmt.executeUpdate();
        ResultSet rs = mock(ResultSet.class);
        when(stmt.getGeneratedKeys()).thenReturn(rs);
        when(rs.next()).thenReturn(false);
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> hallDao.createAndReturnWithId(HALL));
    }

    @Test
    void createAndReturnWithIdRunTimeException() throws SQLException {
        when(con.prepareStatement(Constants.INSERT_INTO_HALLS, Statement.RETURN_GENERATED_KEYS)).thenReturn(stmt);
        when(stmt.executeUpdate()).thenThrow(RuntimeException.class);
        Assertions.assertThrows(RuntimeException.class, () -> hallDao.createAndReturnWithId(HALL));

    }



    @Test
    void updateHallRunTimeException() throws SQLException {
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenThrow(RuntimeException.class);
        Assertions.assertThrows(RuntimeException.class, () ->
                hallDao.update(HALL));
    }

    @Test
    void updateHallSuccess() throws SQLException, EntityAlreadyExistException {
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(1);
        assertEquals(true, hallDao.update(HALL));
    }

    @Test
    void updateHallInSuccess() throws SQLException, EntityAlreadyExistException {
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(0);
        assertEquals(false, hallDao.update(HALL));
    }
    private static void prepareResultSet(ResultSet resultSet) throws SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("halls.id")).thenReturn(1);
        when(resultSet.getInt("number_available_seats")).thenReturn(90);
        when(resultSet.getInt("number_seats")).thenReturn(100);
        when(resultSet.getInt("number_sold_seats")).thenReturn(10);
        when(resultSet.getBigDecimal("attendance")).thenReturn(BigDecimal.valueOf(10.00));
    }
}
