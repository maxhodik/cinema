package dao.impl;

import dao.Constants;
import dao.OrderDao;
import dao.SessionDao;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SqlSessionDaoTest {
    private final static LocalTime TIME = LocalTime.parse("09:00");
    private final static LocalDate DATE = LocalDate.parse("2023-02-02");
    private static final int SEATS_MORE = 91;
    private static final int SEATS_LESS = 80;
    private static final int SEATS_EQUALS = 90;
    private final static int NUMBER_OF_RECORDS = 100;
    private final static String ORDER_BY = "name";
    private final static String FILTER_BY = "name";
    private final static String LIMIT = " LIMIT 5 OFFSET 0";


    ;
    private final static User USER = User.builder().id(0).login("test").password("password").role(Role.USER).build();
    private final static Movie EXPECTED_MOVIE = new Movie.Builder().id(0).name("test").build();
    private static final Hall HALL = Hall.builder()
            .id(1)
            .numberSeats(100)
            .numberAvailableSeats(90)
            .numberOfSoldSeats(10).attendance(BigDecimal.valueOf(10.00)).build();
    private final static Session SESSION = Session.builder().id(0).hall(HALL)
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


    private SessionMapper sessionMapper = mock(SessionMapper.class);
    private UserMapper userMapper = mock(UserMapper.class);
    private MovieMapper movieMapper = mock(MovieMapper.class);
    private HallMapper hallMapper = mock(HallMapper.class);
    private SessionDao sessionDao = new SqlSessionDao();


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
        when(stmt.executeQuery(Constants.FIND_ALL_SESSIONS_SORTED_BY_NUMBER_OF_SEATS)).thenReturn(rs);
        prepareResultSet(rs);
        prepareMovieResultSet(rs);
        prepareHallResultSet(rs);
        when(sessionMapper.extractFromResultSet(rs)).thenReturn(SESSION);
        when(hallMapper.extractFromResultSet(rs)).thenReturn(HALL);
        when(movieMapper.extractFromResultSet(rs)).thenReturn(EXPECTED_MOVIE);
        List<Session> actual = sessionDao.findAll();
        assertEquals(1, actual.size());
        assertNotNull(rs);
        assertEquals(SESSION, actual.get(0));
    }

    @Test
    void findAllException() throws SQLException {
        when(con.statement()).thenReturn(stmt);
        when(stmt.executeQuery(Constants.FIND_ALL_SESSIONS_SORTED_BY_NUMBER_OF_SEATS)).thenThrow(RuntimeException.class);
        Assertions.assertThrows(RuntimeException.class, () ->
                sessionDao.findAll());
    }


    @Test
    void findEntityById() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(con.prepareStatement(Constants.FIND_SESSION_BY_ID)).thenReturn(stmt);
        when(stmt.executeQuery()).thenReturn(rs);
        prepareResultSet(rs);
        prepareMovieResultSet(rs);
        prepareHallResultSet(rs);
        when(sessionMapper.extractFromResultSet(rs)).thenReturn(SESSION);
        when(hallMapper.extractFromResultSet(rs)).thenReturn(HALL);
        when(movieMapper.extractFromResultSet(rs)).thenReturn(EXPECTED_MOVIE);
        Session actual = sessionDao.findEntityById(0);
        assertNotNull(rs);
        assertEquals(SESSION, actual);
    }

    @Test
    void findEntityByIdException() throws SQLException {
        when(con.prepareStatement(Constants.FIND_SESSION_BY_ID)).thenReturn(stmt);
        when(stmt.executeQuery()).thenThrow(RuntimeException.class);
        Assertions.assertThrows(RuntimeException.class, () -> sessionDao.findEntityById(0));
    }

    @Test
    void findByMovieSuccess() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(con.prepareStatement(Constants.FIND_ALL_SESSIONS_BY_MOVIE_NAME)).thenReturn(stmt);
        prepareResultSet(rs);
        when(stmt.executeQuery()).thenReturn(rs);
        assertNotNull(rs);
        assertTrue(sessionDao.findByMovie("test"));
    }

    @Test
    void findByMovieInSuccess() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(con.prepareStatement(Constants.FIND_ALL_SESSIONS_BY_MOVIE_NAME)).thenReturn(stmt);
        when(stmt.executeQuery()).thenReturn(rs);
        assertNotNull(rs);
        assertFalse(sessionDao.findByMovie("insuccess"));
    }
    @Test
    void findByMovieException() throws SQLException {
        when(con.prepareStatement(Constants.FIND_ALL_SESSIONS_BY_MOVIE_NAME)).thenReturn(stmt);
        when(stmt.executeQuery()).thenThrow(RuntimeException.class);
        Assertions.assertThrows(RuntimeException.class, () -> sessionDao.findByMovie("test"));
    }

    @Test
    void deleteSuccess() throws SQLException {
        when(con.prepareStatement(Constants.DELETE_SESSION_BY_ID)).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(1);
        boolean actual = sessionDao.delete(SESSION);
        assertTrue(actual);
    }

    @Test
    void deleteInSuccess() throws SQLException {
        when(con.prepareStatement(Constants.DELETE_SESSION_BY_ID)).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(0);
        boolean actual = sessionDao.delete(SESSION);
        assertFalse(actual);
    }

    @Test
    void deleteException() throws SQLException {
        when(con.prepareStatement(Constants.DELETE_SESSION_BY_ID)).thenReturn(stmt);
        when(stmt.executeUpdate()).thenThrow(RuntimeException.class);
        Assertions.assertThrows(RuntimeException.class, () -> sessionDao.delete(SESSION));
    }

    @Test
    void createSuccess() throws SQLException, EntityAlreadyExistException {
        when(con.prepareStatement(Constants.INSERT_INTO_SESSIONS, Statement.RETURN_GENERATED_KEYS)).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(1);
        boolean actual = sessionDao.create(SESSION);
        assertTrue(actual);
    }

    @Test
    void createInSuccess() throws SQLException, EntityAlreadyExistException {
        when(con.prepareStatement(Constants.INSERT_INTO_SESSIONS, Statement.RETURN_GENERATED_KEYS)).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(0);
        boolean actual = sessionDao.create(SESSION);
        assertFalse(actual);
    }

    @Test
    void createException() throws SQLException {
        when(con.prepareStatement(Constants.INSERT_INTO_SESSIONS, Statement.RETURN_GENERATED_KEYS)).thenReturn(stmt);
        when(stmt.executeUpdate()).thenThrow(RuntimeException.class);
        Assertions.assertThrows(RuntimeException.class, () -> sessionDao.create(SESSION));
    }
 @Test
    void updateSuccess() throws SQLException, EntityAlreadyExistException {
        when(con.prepareStatement(Constants.UPDATE_SESSIONS)).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(1);
        boolean actual = sessionDao.update(SESSION);
        assertTrue(actual);
    }

    @Test
    void updateInSuccess() throws SQLException, EntityAlreadyExistException {
        when(con.prepareStatement(Constants.UPDATE_SESSIONS)).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(0);
        boolean actual = sessionDao.update(SESSION);
        assertFalse(actual);
    }

    @Test
    void updateException() throws SQLException {
        when(con.prepareStatement(Constants.UPDATE_SESSIONS)).thenReturn(stmt);
        when(stmt.executeUpdate()).thenThrow(RuntimeException.class);
        Assertions.assertThrows(RuntimeException.class, () -> sessionDao.update(SESSION));
    }




    @Test
    void findAllFilterByAvailableViewing() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(con.prepareStatement(Constants.FIND_ALL_SESSIONS_FILTER_BY_SORTED_BY + FILTER_BY + " ORDER BY " + ORDER_BY + LIMIT)).thenReturn(stmt);
        when(stmt.executeQuery()).thenReturn(rs);
        prepareResultSet(rs);
        prepareMovieResultSet(rs);
        prepareHallResultSet(rs);
        when(sessionMapper.extractFromResultSet(rs)).thenReturn(SESSION);
        when(hallMapper.extractFromResultSet(rs)).thenReturn(HALL);
        when(movieMapper.extractFromResultSet(rs)).thenReturn(EXPECTED_MOVIE);
        List<Session> actual = sessionDao.findAllFilterByAvailableViewing(FILTER_BY,ORDER_BY,LIMIT);
        assertEquals(1, actual.size());
        assertNotNull(rs);
        assertEquals(SESSION, actual.get(0));
    }
    @Test
    void findAllFilterByAvailableViewingException() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(con.prepareStatement(Constants.FIND_ALL_SESSIONS_FILTER_BY_SORTED_BY + FILTER_BY + " ORDER BY " + ORDER_BY + LIMIT)).thenReturn(stmt);
        when(stmt.executeQuery()).thenThrow(RuntimeException.class);
        Assertions.assertThrows(RuntimeException.class, () -> sessionDao.findAllFilterByAvailableViewing(FILTER_BY,ORDER_BY,LIMIT));
    }

    @Test
    void testFindAllFilterByAvailableViewing() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(con.prepareStatement(Constants.FIND_ALL_SESSIONS_FILTER_BY_SORTED_BY + FILTER_BY)).thenReturn(stmt);
        when(stmt.executeQuery()).thenReturn(rs);
        prepareResultSet(rs);
        prepareMovieResultSet(rs);
        prepareHallResultSet(rs);
        when(sessionMapper.extractFromResultSet(rs)).thenReturn(SESSION);
        when(hallMapper.extractFromResultSet(rs)).thenReturn(HALL);
        when(movieMapper.extractFromResultSet(rs)).thenReturn(EXPECTED_MOVIE);
        List<Session> actual = sessionDao.findAllFilterByAvailableViewing(FILTER_BY);
        assertEquals(1, actual.size());
        assertNotNull(rs);
        assertEquals(SESSION, actual.get(0));
    }
    @Test
    void testFindAllFilterByAvailableViewingException() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(con.prepareStatement(Constants.FIND_ALL_SESSIONS_FILTER_BY_SORTED_BY + FILTER_BY)).thenReturn(stmt);
        when(stmt.executeQuery()).thenThrow(RuntimeException.class);
        Assertions.assertThrows(RuntimeException.class, () -> sessionDao.findAllFilterByAvailableViewing(FILTER_BY));
    }

    @Test
    void findAllOrderBy() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(con.prepareStatement(Constants.FIND_ALL_SESSIONS_SORTED_ORDER_BY + ORDER_BY)).thenReturn(stmt);
        when(stmt.executeQuery()).thenReturn(rs);
        prepareResultSet(rs);
        prepareMovieResultSet(rs);
        prepareHallResultSet(rs);
        when(sessionMapper.extractFromResultSet(rs)).thenReturn(SESSION);
        when(hallMapper.extractFromResultSet(rs)).thenReturn(HALL);
        when(movieMapper.extractFromResultSet(rs)).thenReturn(EXPECTED_MOVIE);
        List<Session> actual = sessionDao.findAllOrderBy(FILTER_BY);
        assertEquals(1, actual.size());
        assertNotNull(rs);
        assertEquals(SESSION, actual.get(0));
    }
    @Test
    void findAllOrderByException() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(con.prepareStatement(Constants.FIND_ALL_SESSIONS_SORTED_ORDER_BY + ORDER_BY)).thenReturn(stmt);
        when(stmt.executeQuery()).thenThrow(RuntimeException.class);
        Assertions.assertThrows(RuntimeException.class, () -> sessionDao.findAllOrderBy(ORDER_BY));
    }

    @Test
    void getNumberOfRecordsException() throws SQLException {
        when(con.prepareStatement(Constants.COUNT_ALL_SESSIONS_FILTER_BY_SORTED_BY + FILTER_BY)).thenReturn(stmt);
        when(stmt.executeUpdate()).thenThrow(RuntimeException.class);
        Assertions.assertThrows(RuntimeException.class, () ->
                sessionDao.getNumberOfRecords(FILTER_BY));
    }

    @Test
    void getNumberOfRecords() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(con.prepareStatement(Constants.COUNT_ALL_SESSIONS_FILTER_BY_SORTED_BY + FILTER_BY)).thenReturn(stmt);
        when(stmt.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt(1)).thenReturn(NUMBER_OF_RECORDS);
        int records = sessionDao.getNumberOfRecords(FILTER_BY);
        assertEquals(NUMBER_OF_RECORDS, records);
    }

    private static void prepareResultSet(ResultSet resultSet) throws SQLException {
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