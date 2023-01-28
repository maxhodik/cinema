package command;

import dto.MovieDto;
import entities.Movie;
import org.junit.jupiter.api.Test;
import service.MovieService;
import service.UserService;
import web.handler.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovieCommandTest {
    private static final String LIMIT = " LIMIT 5 OFFSET 0";

    private final static String ORDER_BY = "id";


    private final static MovieDto EXPECTED_MOVIE_DTO = new MovieDto(0, "test");
    private final static List<MovieDto> MOVIE_DTOS = List.of(EXPECTED_MOVIE_DTO);
    private final HttpServletRequest request = mock(HttpServletRequest.class);

    private final Pagination pagination = mock(Pagination.class);
    private final HttpSession httpSession = mock(HttpSession.class);

    private final MovieService movieService = mock(MovieService.class);
    private MovieCommand movieCommand = new MovieCommand(movieService, pagination);

    @Test
    void performGet() {
        //given
        when(request.getSession()).thenReturn(httpSession);
        when(request.getParameter("orderBy")).thenReturn(ORDER_BY);
        when(movieService.getNumberOfRecords()).thenReturn(1);
        when(movieService.findAllSortedByWithLimit(ORDER_BY, LIMIT)).thenReturn(MOVIE_DTOS);
        //when
        String path = movieCommand.performGet(request);
        //then
        verify(request).setAttribute("movieDto", MOVIE_DTOS);
        assertEquals("/WEB-INF/admin/movie.jsp", path);
    }


    @Test
    void performPost() {
        //given
        when(request.getSession()).thenReturn(httpSession);
        //when
        String path = movieCommand.performPost(request);
        //then
        assertEquals("/WEB-INF/admin/movie.jsp", path);
    }
}