package command;

import dto.MovieDto;
import entities.Movie;
import exceptions.EntityAlreadyExistException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import service.MovieService;
import web.form.IdForm;
import web.form.MovieForm;
import web.form.validation.IdValidator;
import web.form.validation.MovieFormValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovieUpdateCommandTest {

    private final static String NAME = "test";
    private final static String ID = "0";

    private final static Movie EXPECTED_MOVIE = new Movie.Builder().id(0).name("test").build();


    private final HttpServletRequest request = mock(HttpServletRequest.class);

    private final MovieFormValidator movieFormValidator = mock(MovieFormValidator.class);


    private final MovieService movieService = mock(MovieService.class);
    private final IdValidator idValidator = mock(IdValidator.class);


    private MovieUpdateCommand movieUpdateCommand = new MovieUpdateCommand(movieService, movieFormValidator, idValidator);

    @Test
    void performGet() {
        //given
        when(request.getParameter("id")).thenReturn(ID);
        when(movieService.findEntityById(Integer.parseInt(ID))).thenReturn(EXPECTED_MOVIE);
        //when
        String path = movieUpdateCommand.performGet(request);
        //when
        verify(request).setAttribute("movie", EXPECTED_MOVIE);
        assertEquals("/WEB-INF/admin/update-movie.jsp", path);
    }

    @Test
    void performGetWrongId() {
        //given
        when(idValidator.validate(any())).thenReturn(true);
        //then
        assertThrows(IllegalArgumentException.class, () -> movieUpdateCommand.performGet(request));
    }


    @Test
    void performPost() throws EntityAlreadyExistException {
        //given
        when(request.getParameter("name")).thenReturn(NAME);
        when(request.getParameter("id")).thenReturn(ID);
        when(idValidator.validate(any())).thenReturn(false);
        when(movieFormValidator.validate(any())).thenReturn(false);
        when(movieService.update(EXPECTED_MOVIE)).thenReturn(true);
        when(movieService.findEntityById(Integer.parseInt(ID))).thenReturn(EXPECTED_MOVIE);
        //when
        String path = movieUpdateCommand.performPost(request);
        //then
        assertEquals("redirect:admin/movie", path);
    }

    @Test
    void performPostWrongID() throws EntityAlreadyExistException {
        //given
        when(request.getParameter("name")).thenReturn(NAME);
        when(request.getParameter("id")).thenReturn(ID);
        when(idValidator.validate(any())).thenReturn(true);
        //then
        assertThrows(IllegalArgumentException.class, () -> movieUpdateCommand.performGet(request));
    }

    @Test
    void performPostMovieWrong() throws EntityAlreadyExistException {
        //given
        when(request.getParameter("name")).thenReturn(NAME);
        when(request.getParameter("id")).thenReturn(ID);
        when(idValidator.validate(any())).thenReturn(false);
        when(movieFormValidator.validate(any())).thenReturn(true);
        when(movieService.update(EXPECTED_MOVIE)).thenReturn(true);
        when(movieService.findEntityById(Integer.parseInt(ID))).thenReturn(EXPECTED_MOVIE);
        //when
        String path = movieUpdateCommand.performPost(request);
        verify(request).setAttribute("errors", true);
        //then
        assertEquals("/WEB-INF/admin/update-movie.jsp", path);
    }

    @Test
    void performPostException() throws EntityAlreadyExistException {
        //given
        when(request.getParameter("name")).thenReturn(NAME);
        when(request.getParameter("id")).thenReturn(ID);
        when(idValidator.validate(any())).thenReturn(false);
        when(movieFormValidator.validate(any())).thenReturn(true);
        when(movieService.update(EXPECTED_MOVIE)).thenThrow(EntityAlreadyExistException.class);

        //when
        String path = movieUpdateCommand.performPost(request);

        //then
        assertEquals("/WEB-INF/admin/update-movie.jsp", path);
    }

}