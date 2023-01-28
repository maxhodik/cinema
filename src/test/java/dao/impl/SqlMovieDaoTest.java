package dao.impl;

import dao.Constants;
import dao.MovieDao;
import dao.UserDao;
import dao.maper.MovieMapper;
import dao.maper.UserMapper;
import entities.Movie;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class SqlMovieDaoTest {
    private final static int NUMBER_OF_RECORDS = 100;
    private final static String NAME = "test";
    private final static String ORDER_BY = "name";
    private final static String LIMIT = " LIMIT 5 OFFSET 0";
    private final static Movie EXPECTED_MOVIE = new Movie.Builder().id(0).name("test").build();
    private ConnectionWrapper con = mock(ConnectionWrapper.class);
    private PreparedStatement stmt = mock(PreparedStatement.class);
    private Statement statement = mock(Statement.class);

    private MovieDao movieDao = new SqlMovieDao();

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
    void findAllOrderBy() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(con.prepareStatement(Constants.FIND_ALL_MOVIES_SORTED_BY_NAME + " ORDER BY " + ORDER_BY)).thenReturn(stmt);
        MovieMapper mapper = mock(MovieMapper.class);
        when(stmt.executeQuery()).thenReturn(rs);
        prepareResultSet(rs);
        when(mapper.extractFromResultSet(rs)).thenReturn(EXPECTED_MOVIE);
        List<Movie> actual = movieDao.findAllOrderBy(ORDER_BY);
        assertEquals(1, actual.size());
        assertNotNull(rs);
        assertEquals(EXPECTED_MOVIE, actual.get(0));
    }

    @Test
    void findAllOrderByException() throws SQLException {
        when(con.prepareStatement(Constants.FIND_ALL_MOVIES_SORTED_BY_NAME + " ORDER BY " + ORDER_BY)).thenReturn(stmt);
        when(stmt.executeQuery()).thenThrow(RuntimeException.class);
        Assertions.assertThrows(RuntimeException.class, () ->
                movieDao.findAllOrderBy(ORDER_BY));
    }

    @Test
    void findAllSortedBy() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(con.prepareStatement(Constants.FIND_ALL_MOVIES_SORTED_BY_NAME + " ORDER BY " + ORDER_BY + LIMIT)).thenReturn(stmt);
        MovieMapper mapper = mock(MovieMapper.class);
        when(stmt.executeQuery()).thenReturn(rs);
        prepareResultSet(rs);
        when(mapper.extractFromResultSet(rs)).thenReturn(EXPECTED_MOVIE);
        List<Movie> actual = movieDao.findAllSortedBy(ORDER_BY, LIMIT);
        assertEquals(1, actual.size());
        assertNotNull(rs);
        assertEquals(EXPECTED_MOVIE, actual.get(0));
    }

    @Test
    void findAllSortedByException() throws SQLException {
        when(con.prepareStatement(Constants.FIND_ALL_MOVIES_SORTED_BY_NAME + " ORDER BY " + ORDER_BY + LIMIT)).thenReturn(stmt);
        when(stmt.executeQuery()).thenThrow(RuntimeException.class);
        Assertions.assertThrows(RuntimeException.class, () ->
                movieDao.findAllSortedBy(ORDER_BY, LIMIT));
    }

    @Test
    void findAll() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(con.prepareStatement(Constants.FIND_ALL_MOVIES_SORTED_BY_NAME)).thenReturn(stmt);
        MovieMapper mapper = mock(MovieMapper.class);
        when(stmt.executeQuery()).thenReturn(rs);
        prepareResultSet(rs);
        when(mapper.extractFromResultSet(rs)).thenReturn(EXPECTED_MOVIE);
        List<Movie> actual = movieDao.findAll();
        assertEquals(1, actual.size());
        assertNotNull(rs);
        assertEquals(EXPECTED_MOVIE, actual.get(0));
    }

    @Test
    void findAllException() throws SQLException {
        when(con.prepareStatement(Constants.FIND_ALL_MOVIES_SORTED_BY_NAME)).thenReturn(stmt);
        when(stmt.executeQuery()).thenThrow(RuntimeException.class);
        Assertions.assertThrows(RuntimeException.class, () ->
                movieDao.findAll());
    }

    @Test
    void findEntityById() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(con.prepareStatement(Constants.FIND_MOVIE_BY_ID)).thenReturn(stmt);
        MovieMapper mapper = mock(MovieMapper.class);
        when(stmt.executeQuery()).thenReturn(rs);
        prepareResultSet(rs);
        when(mapper.extractFromResultSet(rs)).thenReturn(EXPECTED_MOVIE);
        Movie actual = movieDao.findEntityById(0);
        assertNotNull(rs);
        assertEquals(EXPECTED_MOVIE, actual);
    }

    @Test
    void findEntityByIdException() throws SQLException {
        when(con.prepareStatement(Constants.FIND_MOVIE_BY_ID)).thenReturn(stmt);
        when(stmt.executeQuery()).thenThrow(RuntimeException.class);
        Assertions.assertThrows(RuntimeException.class, () ->
                movieDao.findEntityById(0));
    }


    @Test
    void findEntityByName() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(con.prepareStatement(Constants.FIND_MOVIE_BY_NAME)).thenReturn(stmt);
        MovieMapper mapper = mock(MovieMapper.class);
        when(stmt.executeQuery()).thenReturn(rs);
        prepareResultSet(rs);
        when(mapper.extractFromResultSet(rs)).thenReturn(EXPECTED_MOVIE);
        Movie actual = movieDao.findEntityByName(NAME);
        assertNotNull(rs);
        assertEquals(EXPECTED_MOVIE, actual);
    }

    @Test
    void findEntityByNameException() throws SQLException {
        when(con.prepareStatement(Constants.FIND_MOVIE_BY_ID)).thenReturn(stmt);
        when(stmt.executeQuery()).thenThrow(RuntimeException.class);
        Assertions.assertThrows(RuntimeException.class, () ->
                movieDao.findEntityByName(NAME));
    }


    @Test
    void deleteMovieException() throws SQLException {
        when(con.prepareStatement(Constants.DELETE_MOVIE_BY_NAME)).thenReturn(stmt);
        when(stmt.executeUpdate()).thenThrow(RuntimeException.class);
        Assertions.assertThrows(RuntimeException.class, () ->
                movieDao.delete(EXPECTED_MOVIE));
    }

    @Test
    void deleteUserSuccess() throws SQLException {
        when(con.prepareStatement(Constants.DELETE_MOVIE_BY_NAME)).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(1);
        boolean actual = movieDao.delete(EXPECTED_MOVIE);
        assertTrue(actual);
    }

    @Test
    void deleteMovieInSuccess() throws SQLException {
        when(con.prepareStatement(Constants.DELETE_MOVIE_BY_NAME)).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(0);
        boolean actual = movieDao.delete(EXPECTED_MOVIE);
        assertFalse(actual);
    }

    @Test
    void createMovieException() throws SQLException {
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenThrow(SQLIntegrityConstraintViolationException.class);
        EntityAlreadyExistException thrown = Assertions.assertThrows(EntityAlreadyExistException.class, () ->
                movieDao.create(EXPECTED_MOVIE));
    }

    @Test
    void createMovieSuccess() throws SQLException, EntityAlreadyExistException {
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(1);
        assertEquals(true, movieDao.create(EXPECTED_MOVIE));
    }

    @Test
    void createMovieInSuccess() throws SQLException, EntityAlreadyExistException {
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(0);

        assertEquals(false, movieDao.create(EXPECTED_MOVIE));
    }

    @Test
    void updateUserException() throws SQLException {
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenThrow(SQLIntegrityConstraintViolationException.class);
        EntityAlreadyExistException thrown = Assertions.assertThrows(EntityAlreadyExistException.class, () ->
                movieDao.update(EXPECTED_MOVIE));

    }

    @Test
    void updateUserRunTimeException() throws SQLException {
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenThrow(RuntimeException.class);
        Assertions.assertThrows(RuntimeException.class, () ->
                movieDao.update(EXPECTED_MOVIE));
    }

    @Test
    void updateUserSuccess() throws SQLException, EntityAlreadyExistException {
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(1);
        assertEquals(true, movieDao.update(EXPECTED_MOVIE));
    }

    @Test
    void updateUserInSuccess() throws SQLException, EntityAlreadyExistException {
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(0);
        assertEquals(false, movieDao.update(EXPECTED_MOVIE));
    }

    @Test
    void getNumberOfRecordsException() throws SQLException {
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenThrow(RuntimeException.class);
        Assertions.assertThrows(RuntimeException.class, () ->
                movieDao.getNumberOfRecords());
    }

    @Test
    void getNumberOfRecords() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(con.prepareStatement(Constants.COUNT_ALL_MOVIES)).thenReturn(stmt);
        when(stmt.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt(1)).thenReturn(NUMBER_OF_RECORDS);
        int records = movieDao.getNumberOfRecords();
        assertEquals(NUMBER_OF_RECORDS, records);
    }

    private static void prepareResultSet(ResultSet resultSet) throws SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("movies.id")).thenReturn(0);
        when(resultSet.getString("name")).thenReturn("test");
    }
}