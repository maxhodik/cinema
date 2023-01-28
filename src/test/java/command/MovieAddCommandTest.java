package command;

import dto.MovieDto;
import entities.Movie;
import exceptions.EntityAlreadyExistException;
import org.junit.jupiter.api.Test;
import service.MovieService;
import web.form.validation.IdValidator;
import web.form.validation.MovieFormValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MovieAddCommandTest {
    private final static String NAME = "test";

    private final static Movie EXPECTED_MOVIE = new Movie.Builder().id(0).name("test").build();

    private final HttpServletRequest request = mock(HttpServletRequest.class);

    private final MovieFormValidator movieFormValidator = mock(MovieFormValidator.class);
    private final HttpSession httpSession = mock(HttpSession.class);

    private final MovieService movieService = mock(MovieService.class);
    private final MovieAddCommand movieAddCommand= new MovieAddCommand(movieService,movieFormValidator);

    @Test
    void performGet() {
        //given
        when(request.getSession()).thenReturn(httpSession);
        //when
        String path = movieAddCommand.performGet(request);
        //then
        assertEquals("/WEB-INF/admin/add-movie.jsp", path);
    }

    @Test
    void performPost() throws EntityAlreadyExistException {
        //given
        when(request.getSession()).thenReturn(httpSession);
        when(movieFormValidator.validate(any())).thenReturn(false);
        when(movieService.create(NAME)).thenReturn(EXPECTED_MOVIE);
        //when
        String path = movieAddCommand.performPost(request);
        //then
        assertEquals("redirect:admin/movie", path);
    }
    @Test
    void performPostWrongMovie() throws EntityAlreadyExistException {
        //given
        when(request.getSession()).thenReturn(httpSession);
        when(movieFormValidator.validate(any())).thenReturn(true);
        when(movieService.create(NAME)).thenThrow(EntityAlreadyExistException.class);
        //when
        String path = movieAddCommand.performPost(request);
        //then
        assertEquals("/WEB-INF/admin/add-movie.jsp", path);
    }
}