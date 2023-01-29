package command;

import dto.Filter;
import dto.MovieDto;
import dto.SessionAdminDto;
import dto.SessionDto;
import entities.*;
import org.junit.jupiter.api.Test;
import service.HallService;
import service.MovieService;
import service.ScheduleService;
import web.form.validation.IdValidator;
import web.form.validation.SessionFormValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ScheduleAddCommandTest {
    private final static LocalTime TIME = LocalTime.parse("09:00");
    private final static LocalDate DATE = LocalDate.parse("2023-02-02");
    private static final boolean TRUE = true;
    private static final String ID = "0";

    private static final List<String> MOVIE_NAMES = List.of("test");
    private final static Movie EXPECTED_MOVIE = new Movie.Builder().id(0).name("test").build();
    private final static List<Movie> MOVIES = List.of(EXPECTED_MOVIE);
    private static final Hall HALL = Hall.builder()
            .id(1)
            .numberSeats(100)
            .numberAvailableSeats(90)
            .numberOfSoldSeats(10).attendance(BigDecimal.valueOf(10.00)).build();
    private static final SessionDto SESSION_DTO = new SessionDto(0, EXPECTED_MOVIE.getName(),
            DATE, TIME, Status.ACTIVE, HALL.getNumberAvailableSeats());

    private final ScheduleService scheduleService = mock(ScheduleService.class);
    private final MovieService movieService = mock(MovieService.class);
    private final HallService hallService = mock(HallService.class);
    private final SessionFormValidator sessionValidator = mock(SessionFormValidator.class);



    private final HttpServletRequest request = mock(HttpServletRequest.class);

    private final HttpSession httpSession = mock(HttpSession.class);
    private final ScheduleAddCommand scheduleAddCommand = new ScheduleAddCommand(scheduleService, movieService, hallService, sessionValidator);

    @Test
    void performGet() {
        //given
        when(movieService.findAll()).thenReturn(MOVIES);
        //when
        String path = scheduleAddCommand.performGet(request);
        //then
        verify(request).setAttribute("movieDto", MOVIE_NAMES);
        assertEquals("/WEB-INF/admin/add-session.jsp", path);
    }

    @Test
    void performPostNotValid() {
        //given
        when(request.getSession()).thenReturn(httpSession);
        when(request.getParameter("id")).thenReturn(ID);
        when(request.getParameter("date")).thenReturn(String.valueOf(DATE));
        when(request.getParameter("time")).thenReturn(String.valueOf(TIME));
        when(request.getParameter("movieName")).thenReturn(String.valueOf(EXPECTED_MOVIE.getName()));
        when(request.getParameter("seats")).thenReturn(String.valueOf(HALL.getCapacity()));
        when(sessionValidator.validate(any())).thenReturn(TRUE);
        //when
        String path = scheduleAddCommand.performPost(request);
        //then
        verify(httpSession).setAttribute("errors", true);
        assertEquals("redirect:admin/add-session", path);
    }

    @Test
    void perform() {
        //given
        when(request.getSession()).thenReturn(httpSession);
        when(request.getParameter("id")).thenReturn(ID);
        when(request.getParameter("date")).thenReturn(String.valueOf(DATE));
        when(request.getParameter("time")).thenReturn(String.valueOf(TIME));
        when(request.getParameter("movieName")).thenReturn(String.valueOf(EXPECTED_MOVIE.getName()));
        when(request.getParameter("seats")).thenReturn(String.valueOf(HALL.getCapacity()));
        when(sessionValidator.validate(any())).thenReturn(false);
        when(scheduleService.create(SESSION_DTO)).thenReturn(true);
        //when
        String path = scheduleAddCommand.performPost(request);
        //then
        assertEquals("redirect:schedule", path);
    }

}