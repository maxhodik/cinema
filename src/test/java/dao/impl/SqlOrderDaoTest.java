package dao.impl;

import dao.Constants;
import dao.MovieDao;
import dao.OrderDao;
import dao.maper.*;
import entities.*;
import exceptions.EntityAlreadyExistException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistance.ConnectionWrapper;
import persistance.TransactionManagerWrapper;

import java.math.BigDecimal;
import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class SqlOrderDaoTest {
    private final static LocalTime TIME = LocalTime.parse("09:00");
    private final static LocalDate DATE = LocalDate.parse("2023-02-02");
    private static final int SEATS_MORE = 91;
    private static final int SEATS_LESS = 80;
    private static final int SEATS_EQUALS = 90;

    ;
    private final static User USER = User.builder().id(0).login("test").password("password").role(Role.USER).build();
    private final static Movie EXPECTED_MOVIE = new Movie.Builder().id(0).name("test").build();
    private static final Hall HALL = Hall.builder()
            .id(1)
            .numberSeats(100)
            .numberAvailableSeats(90)
            .numberOfSoldSeats(10).attendance(BigDecimal.valueOf(10.00)).build();
    private final static Session SESSION = Session.builder()
            .id(0)
            .hall(HALL)
            .movie(EXPECTED_MOVIE)
            .time(TIME)
            .data(DATE)
            .status(Status.ACTIVE)
            .dayOfWeek(DayOfWeek.SATURDAY)
            .build();
    private final static Order EXPECTED_FULL_ORDER = Order.builder().id(0).state(State.NEW)
            .numberOfSeats(5).user(USER).price(100).session(SESSION).build();
    private final static Order EXPECTED_ORDER = Order.builder().id(0).state(State.NEW)
            .numberOfSeats(5).price(100).build();
    private ConnectionWrapper con = mock(ConnectionWrapper.class);
    private PreparedStatement stmt = mock(PreparedStatement.class);
    private Statement statement = mock(Statement.class);
    private OrderMapper mapper = mock(OrderMapper.class);
    private SessionMapper sessionMapper = mock(SessionMapper.class);
    private UserMapper userMapper = mock(UserMapper.class);
    private HallMapper hallMapper= mock(HallMapper.class);
    private MovieMapper movieMapper = mock(MovieMapper.class);


    private OrderDao orderDao = new SqlOrderDao();

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
    void findAll() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(con.statement()).thenReturn(stmt);
        when(stmt.executeQuery(Constants.FIND_ALL_ORDERS)).thenReturn(rs);
        prepareResultSet(rs);
        when(mapper.extractFromResultSet(rs)).thenReturn(EXPECTED_ORDER);
        List<Order> actual = orderDao.findAll();
        assertEquals(1, actual.size());
        assertNotNull(rs);
        assertEquals(EXPECTED_ORDER, actual.get(0));
    }

    @Test
    void findAllException() throws SQLException {
        when(con.statement()).thenReturn(stmt);
        when(stmt.executeQuery(Constants.FIND_ALL_ORDERS)).thenThrow(RuntimeException.class);
        Assertions.assertThrows(RuntimeException.class, () ->
                orderDao.findAll());
    }

    @Test
    void findEntityById() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(con.prepareStatement(Constants.FIND_ORDER_BY_ID)).thenReturn(stmt);
        when(stmt.executeQuery()).thenReturn(rs);
        prepareResultSet(rs);
        prepareSessionResultSet(rs);
        prepareUserResultSet(rs);
        prepareMovieResultSet(rs);
        prepareHallResultSet(rs);
        when(mapper.extractFromResultSet(any(ResultSet.class))).thenReturn(EXPECTED_FULL_ORDER);
        when(sessionMapper.extractFromResultSet(any(ResultSet.class))).thenReturn(SESSION);
        when(movieMapper.extractFromResultSet(rs)).thenReturn(EXPECTED_MOVIE);
        when(hallMapper.extractFromResultSet(rs)).thenReturn(HALL);
        when(userMapper.extractFromResultSet(any(ResultSet.class))).thenReturn(USER);
        Order actual = orderDao.findEntityById(0);
        assertNotNull(rs);
        assertEquals(EXPECTED_FULL_ORDER, actual);
    }

    @Test
    void findAllBySessionId() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(con.prepareStatement(Constants.FIND_ORDER_BY_SESSION_ID)).thenReturn(stmt);
        when(stmt.executeQuery()).thenReturn(rs);
        prepareResultSet(rs);
        prepareSessionResultSet(rs);
        prepareUserResultSet(rs);
        prepareMovieResultSet(rs);
        prepareHallResultSet(rs);
        when(mapper.extractFromResultSet(any(ResultSet.class))).thenReturn(EXPECTED_FULL_ORDER);
        when(sessionMapper.extractFromResultSet(any(ResultSet.class))).thenReturn(SESSION);
        when(movieMapper.extractFromResultSet(rs)).thenReturn(EXPECTED_MOVIE);
        when(hallMapper.extractFromResultSet(rs)).thenReturn(HALL);
        when(userMapper.extractFromResultSet(any(ResultSet.class))).thenReturn(USER);
        List<Order> actual = orderDao.findAllBySessionId(0);
        assertEquals(1, actual.size());
        assertNotNull(rs);
        assertEquals(EXPECTED_FULL_ORDER, actual.get(0));
    }
    @Test
    void findAllBySessionIdException() throws SQLException {
        when(con.prepareStatement(Constants.FIND_ORDER_BY_SESSION_ID)).thenReturn(stmt);
        when(stmt.executeQuery()).thenThrow(RuntimeException.class);
        Assertions.assertThrows(RuntimeException.class, () ->
                orderDao.findEntityById(0));
    }
    @Test
    void deleteSuccess() throws SQLException {
        when(con.prepareStatement(Constants.DELETE_ORDER_BY_ID)).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(1);
        boolean actual = orderDao.delete(EXPECTED_ORDER);
        assertTrue(actual);
    }

    @Test
    void deleteInSuccess() throws SQLException {
        when(con.prepareStatement(Constants.DELETE_ORDER_BY_ID)).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(0);
        boolean actual = orderDao.delete(EXPECTED_ORDER);
        assertFalse(actual);
    }

    @Test
    void deleteException() throws SQLException {
        when(con.prepareStatement(Constants.DELETE_ORDER_BY_ID)).thenReturn(stmt);
        when(stmt.executeUpdate()).thenThrow(RuntimeException.class);
        Assertions.assertThrows(RuntimeException.class, () -> orderDao.delete(EXPECTED_ORDER));
    }

    @Test
    void create() throws SQLException, EntityAlreadyExistException {
        when(con.prepareStatement(Constants.INSERT_INTO_ORDER)).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(1);
        boolean actual = orderDao.create(EXPECTED_FULL_ORDER);
        assertTrue(actual);
    }

    @Test
    void createInSuccess() throws SQLException {
        when(con.prepareStatement(Constants.INSERT_INTO_ORDER)).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(0);
        Assertions.assertThrows(RuntimeException.class, () -> orderDao.create(EXPECTED_ORDER));
    }

    @Test
    void createOrderException() throws SQLException {
        when(con.prepareStatement(Constants.INSERT_INTO_ORDER)).thenReturn(stmt);
        when(stmt.executeUpdate()).thenThrow(RuntimeException.class);
        Assertions.assertThrows(RuntimeException.class, () ->
                orderDao.create(EXPECTED_ORDER));

    }

    @Test
    void updateOrderRunTimeException() throws SQLException {
        when(con.prepareStatement(Constants.UPDATE_ORDERS)).thenReturn(stmt);
        when(stmt.executeUpdate()).thenThrow(RuntimeException.class);
        Assertions.assertThrows(RuntimeException.class, () ->
                orderDao.update(EXPECTED_ORDER));
    }

    @Test
    void updateOrderSuccess() throws SQLException, EntityAlreadyExistException {
        when(con.prepareStatement(Constants.UPDATE_ORDERS)).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(1);
        assertEquals(true, orderDao.update(EXPECTED_FULL_ORDER));
    }

    @Test
    void updateOrderInSuccess() throws SQLException, EntityAlreadyExistException {
        when(con.prepareStatement(Constants.UPDATE_ORDERS)).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(0);
        assertEquals(false, orderDao.update(EXPECTED_FULL_ORDER));
    }

    private static void prepareResultSet(ResultSet resultSet) throws SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("orders.id")).thenReturn(0);
        when(resultSet.getString("orders.state")).thenReturn(String.valueOf(State.NEW));
        when(resultSet.getInt("number_of_seats")).thenReturn(5);
        when(resultSet.getInt("price")).thenReturn(100);
    }
    private static void prepareUserResultSet(ResultSet resultSet) throws SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("users.id")).thenReturn(0);
        when(resultSet.getString("login")).thenReturn("test");
        when(resultSet.getString("password")).thenReturn("password");
        when(resultSet.getString("role")).thenReturn(String.valueOf(Role.USER));
    }
    private static void prepareSessionResultSet(ResultSet resultSet) throws SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("sessions.id")).thenReturn(0);
        when(resultSet.getDate("date")).thenReturn(Date.valueOf(DATE));
        when(resultSet.getTime("time")).thenReturn(Time.valueOf(TIME));
        when(resultSet.getString("status")).thenReturn(String.valueOf(Status.ACTIVE));
    }
    private static void prepareMovieResultSet(ResultSet resultSet) throws SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("movies.id")).thenReturn(0);
        when(resultSet.getString("name")).thenReturn("test");
    }

    private static void prepareHallResultSet(ResultSet resultSet) throws SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("halls.id")).thenReturn(1);
        when(resultSet.getInt("number_available_seats")).thenReturn(90);
        when(resultSet.getInt("number_seats")).thenReturn(100);
        when(resultSet.getInt("number_sold_seats")).thenReturn(10);
        when(resultSet.getBigDecimal("attendance")).thenReturn(BigDecimal.valueOf(10.00));
    }
}
