package service.impl;

import dao.MovieDao;
import dto.MovieDto;
import entities.Movie;
import entities.User;
import exceptions.DBException;
import exceptions.EntityAlreadyExistException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class MovieServiceImplTest {
    private final static String NAME = "test";
    private final static String ORDER_BY = "OrderBy";
    private final static String LIMIT = "Limit";
    private final static Movie EXPECTED_MOVIE = new Movie.Builder().id(0).name("test").build();
    private final static MovieDto EXPECTED_MOVIE_DTO = new MovieDto(0, "test");
    private final static List<MovieDto> movieDtos = List.of(EXPECTED_MOVIE_DTO);
    private MovieDao movieDao = mock(MovieDao.class);
    private MovieServiceImpl movieService = new MovieServiceImpl(movieDao);

    @Test
    void create() throws EntityAlreadyExistException {
        when(movieDao.create(any(Movie.class))).thenReturn(true);
        when(movieDao.findEntityByName(anyString())).thenReturn(null);
        Movie actual = movieService.create(NAME);
        verify(movieDao, times(1)).findEntityByName(NAME);
        verify(movieDao, times(1)).create(actual);
        assertEquals(EXPECTED_MOVIE, actual);
    }

    @Test
    public void createIfExistInDataBase() throws EntityAlreadyExistException {
        when(movieDao.create(any(Movie.class))).thenThrow(EntityAlreadyExistException.class);
        EntityAlreadyExistException thrown = Assertions.assertThrows(EntityAlreadyExistException.class, () ->
                movieService.create(NAME));

    }

    @Test
    public void createNewUserIfExistInDataBaseControlInService() throws EntityAlreadyExistException {
        when(movieDao.findEntityByName(anyString())).thenReturn(EXPECTED_MOVIE);
        EntityAlreadyExistException thrown = Assertions.assertThrows(EntityAlreadyExistException.class, () ->
                movieService.create(NAME));
        Assertions.assertEquals("Movie already exists", thrown.getMessage());
    }

    @Test
    void findAllSortedByWithLimit() {
        when(movieDao.findAllSortedBy(anyString(), anyString())).thenReturn(Collections.singletonList(EXPECTED_MOVIE));
        List<MovieDto> actual = movieService.findAllSortedByWithLimit(ORDER_BY, LIMIT);
        verify(movieDao).findAllSortedBy(ORDER_BY, LIMIT);
        verifyNoMoreInteractions(movieDao);
        assertEquals(movieDtos, actual);
    }
    @Test
    void findAllSortedByWithLimitTrowException() {
        when(movieDao.findAllSortedBy(anyString(), anyString())).thenThrow(RuntimeException.class);
              assertThrows(RuntimeException.class,() -> movieService.findAllSortedByWithLimit(ORDER_BY,LIMIT));
    }
    @Test
    void findAllOrderByTrowException() {
        when(movieDao.findAllOrderBy(anyString())).thenThrow(RuntimeException.class);
              assertThrows(RuntimeException.class,() -> movieService.findAllOrderBy(ORDER_BY));
    }

    @Test
    void findAllOrderBy() { when(movieDao.findAllOrderBy(anyString())).thenReturn(Collections.singletonList(EXPECTED_MOVIE));
        List<MovieDto> actual = movieService.findAllOrderBy(ORDER_BY);
        verify(movieDao).findAllOrderBy(ORDER_BY);
        verifyNoMoreInteractions(movieDao);
        assertEquals(movieDtos, actual);
    }

    @Test
    void getNumberOfRecords() {
        when(movieDao.getNumberOfRecords()).thenReturn(1);
        assertEquals(1, movieService.getNumberOfRecords());
    }

    @Test
    void testSQLErrorNumberOfRecords()  {
        when(movieDao.getNumberOfRecords()).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class,() -> movieService.getNumberOfRecords());
    }
}
