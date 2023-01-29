package command;

import dto.MovieDto;
import entities.Movie;
import exceptions.EntityAlreadyExistException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import service.MovieService;
import web.form.validation.IdValidator;
import web.form.validation.MovieFormValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovieDeleteCommandTest {
    private final static String ID = "0";

    private final static Movie EXPECTED_MOVIE = new Movie.Builder().id(0).name("test").build();

    private final static MovieDto EXPECTED_MOVIE_DTO = new MovieDto(0, "test");
    private final static List<MovieDto> MOVIE_DTOS = List.of(EXPECTED_MOVIE_DTO);

    private final HttpServletRequest request = mock(HttpServletRequest.class);

    private final HttpSession httpSession= mock(HttpSession.class);


    private final MovieService movieService = mock(MovieService.class);
    private final IdValidator idValidator = mock(IdValidator.class);
    private final MovieDeleteCommand movieDeleteCommand = new MovieDeleteCommand(movieService, idValidator);

    @Test
    void performGet() {
        //given
        when(movieService.findAllOrderBy("id")).thenReturn(MOVIE_DTOS);
        //when
        String path = movieDeleteCommand.performGet(request);
        //then
        verify(request).setAttribute("movie", MOVIE_DTOS);
        assertEquals("/WEB-INF/admin/movie.jsp", path);
    }

    @Test
    void performGetException() {
        when(movieService.findAllOrderBy("id")).thenThrow(RuntimeException.class);
        Assertions.assertThrows(RuntimeException.class, () -> movieDeleteCommand.performGet(request));

    }


    @Test
    void performPost() throws EntityAlreadyExistException {
        //given
        when(request.getParameter("id")).thenReturn(ID);
        when(idValidator.validate(any())).thenReturn(false);
        when(movieService.findEntityById(Integer.parseInt(ID))).thenReturn(EXPECTED_MOVIE);
        when(movieService.delete(EXPECTED_MOVIE)).thenReturn(true);
        //when
        String path = movieDeleteCommand.performPost(request);
        //then
        assertEquals("redirect:admin/movie", path);
    }
    @Test
    void performPostWrongID() throws EntityAlreadyExistException {
        //given
        when(request.getParameter("id")).thenReturn(ID);
        when(idValidator.validate(any())).thenReturn(true);
        //then
        assertThrows(IllegalArgumentException.class, () -> movieDeleteCommand.performPost(request));
    }
    @Test
    void performPostException() throws EntityAlreadyExistException {
        //given
        when(request.getSession()).thenReturn(httpSession);
        when(request.getParameter("id")).thenReturn(ID);
        when(idValidator.validate(any())).thenReturn(false);
        when(movieService.findEntityById(Integer.parseInt(ID))).thenReturn(EXPECTED_MOVIE);
        when(movieService.delete(EXPECTED_MOVIE)).thenThrow(EntityAlreadyExistException.class);
        //when
        String path = movieDeleteCommand.performPost(request);
        //then
        assertEquals("redirect:admin/movie", path);
    }

}