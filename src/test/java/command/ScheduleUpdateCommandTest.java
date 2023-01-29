package command;

import dto.Filter;
import dto.MovieDto;
import dto.SessionAdminDto;
import dto.SessionDto;
import entities.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.NotNull;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ScheduleUpdateCommandTest {
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
    private final IdValidator idValidator = mock(IdValidator.class);
    private final SessionFormValidator sessionValidator = mock(SessionFormValidator.class);

    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final Session session = mock(Session.class);
    private final Hall hall = mock(Hall.class);
    private final HttpSession httpSession = mock(HttpSession.class);
    private final ScheduleUpdateCommand scheduleUpdateCommand = new ScheduleUpdateCommand(scheduleService, movieService,
            hallService, sessionValidator, idValidator);

    @Test
    void performGetStatusCanceled() {
        //given
        when(request.getParameter("id")).thenReturn(ID);
        when(request.getSession()).thenReturn(httpSession);
        when(scheduleService.findEntityById(any())).thenReturn(session);
        when(session.getStatus()).thenReturn(Status.CANCELED);
        //when
        String path = scheduleUpdateCommand.performGet(request);
        //then
        verify(request).setAttribute("cantUpdate", true);
        assertEquals("/WEB-INF/admin/unsuccess-update-session.jsp", path);
    }

    @Test
    void performGetCantEdit() {
        //given
        when(request.getParameter("id")).thenReturn(ID);
        when(request.getSession()).thenReturn(httpSession);
        when(scheduleService.findEntityById(any())).thenReturn(session);
        when(session.getStatus()).thenReturn(Status.ACTIVE);
        when(scheduleService.findEntityById(Integer.parseInt(ID))).thenReturn(session);
        when(session.getHall()).thenReturn(hall);
        when(hall.getNumberOfSoldSeats()).thenReturn(HALL.getNumberOfSoldSeats());
        //when
        String path = scheduleUpdateCommand.performGet(request);
        //then
        verify(request).setAttribute("cantEdit", true);
        assertEquals("/WEB-INF/admin/unsuccess-update-session.jsp", path);
    }

    @Test
    void performGet() {
        //given
        when(request.getParameter("id")).thenReturn(ID);
        when(request.getSession()).thenReturn(httpSession);
        when(scheduleService.findEntityById(any())).thenReturn(session);
        when(session.getStatus()).thenReturn(Status.ACTIVE);
        when(scheduleService.findEntityById(Integer.parseInt(ID))).thenReturn(session);
        when(session.getHall()).thenReturn(hall);
        when(hall.getNumberOfSoldSeats()).thenReturn(0);
        when(scheduleService.getSessionDto(Integer.parseInt(ID))).thenReturn(SESSION_DTO);
        when(movieService.findAll()).thenReturn(MOVIES);
        //when
        String path = scheduleUpdateCommand.performGet(request);
        //then
        verify(request).setAttribute("sessionDto", SESSION_DTO);
        verify(request).setAttribute("movieDto", MOVIE_NAMES);
        assertEquals("/WEB-INF/admin/update-session.jsp", path);
    }

    @Test
    void performGetWrongID() {
        //given
        when(request.getSession()).thenReturn(httpSession);
        when(request.getParameter("id")).thenReturn(ID);
        when(idValidator.validate(any())).thenReturn(true);
        //then
        assertThrows(IllegalArgumentException.class, () -> scheduleUpdateCommand.performGet(request));
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
        String path = scheduleUpdateCommand.performPost(request);
        //then
        verify(httpSession).setAttribute("errors", true);
        assertEquals("redirect:admin/update-session?id=0", path);
    }

    @Test
    void performPostMovieDoesntExist() {
        //given
        when(request.getSession()).thenReturn(httpSession);
        when(request.getParameter("id")).thenReturn(ID);
        when(request.getParameter("date")).thenReturn(String.valueOf(DATE));
        when(request.getParameter("time")).thenReturn(String.valueOf(TIME));
        when(request.getParameter("movieName")).thenReturn(String.valueOf(EXPECTED_MOVIE.getName()));
        when(request.getParameter("seats")).thenReturn(String.valueOf(HALL.getCapacity()));
        when(sessionValidator.validate(any())).thenReturn(false);
        when(movieService.findEntityByName(EXPECTED_MOVIE.getName())).thenReturn(null);
        //when
        String path = scheduleUpdateCommand.performPost(request);
        //then
        verify(request).setAttribute("movieDoesntExist", true);
        assertEquals("/WEB-INF/admin/update-session.jsp", path);
    }

    @Test
    void performPostSoldTicketsExist() {
        //given
        when(request.getSession()).thenReturn(httpSession);
        when(request.getParameter("id")).thenReturn(ID);
        when(request.getParameter("date")).thenReturn(String.valueOf(DATE));
        when(request.getParameter("time")).thenReturn(String.valueOf(TIME));
        when(request.getParameter("movieName")).thenReturn(String.valueOf(EXPECTED_MOVIE.getName()));
        when(request.getParameter("seats")).thenReturn(String.valueOf(HALL.getCapacity()));
        when(sessionValidator.validate(any())).thenReturn(false);
        when(movieService.findEntityByName(EXPECTED_MOVIE.getName())).thenReturn(EXPECTED_MOVIE);
        when(scheduleService.findEntityById(Integer.parseInt(ID))).thenReturn(session);
        when(session.getHall()).thenReturn(hall);
        when(hall.getNumberOfSoldSeats()).thenReturn(HALL.getNumberOfSoldSeats());
        //when
        String path = scheduleUpdateCommand.performPost(request);
        //then
        verify(request).setAttribute("cantEdit", true);
        assertEquals("/WEB-INF/admin/unsuccess-update-session.jsp", path);
    }

    @Test
    void performPostCanceled() {
        //given
        when(request.getSession()).thenReturn(httpSession);
        when(request.getParameter("id")).thenReturn(ID);
        when(request.getParameter("date")).thenReturn(String.valueOf(DATE));
        when(request.getParameter("time")).thenReturn(String.valueOf(TIME));
        when(request.getParameter("movieName")).thenReturn(String.valueOf(EXPECTED_MOVIE.getName()));
        when(request.getParameter("seats")).thenReturn(String.valueOf(HALL.getCapacity()));
        when(sessionValidator.validate(any())).thenReturn(false);
        when(movieService.findEntityByName(EXPECTED_MOVIE.getName())).thenReturn(EXPECTED_MOVIE);
        when(scheduleService.findEntityById(Integer.parseInt(ID))).thenReturn(session);
        when(session.getHall()).thenReturn(hall);
        when(hall.getNumberOfSoldSeats()).thenReturn(0);
        when(session.getStatus()).thenReturn(Status.CANCELED);
        //then
      Assertions.assertThrows(IllegalArgumentException.class, ()->scheduleUpdateCommand.performPost(request));
    }@Test
    void performPost() {
        //given
        when(request.getSession()).thenReturn(httpSession);
        when(request.getParameter("id")).thenReturn(ID);
        when(request.getParameter("date")).thenReturn(String.valueOf(DATE));
        when(request.getParameter("time")).thenReturn(String.valueOf(TIME));
        when(request.getParameter("movieName")).thenReturn(String.valueOf(EXPECTED_MOVIE.getName()));
        when(request.getParameter("seats")).thenReturn(String.valueOf(HALL.getCapacity()));
        when(sessionValidator.validate(any())).thenReturn(false);
        when(movieService.findEntityByName(EXPECTED_MOVIE.getName())).thenReturn(EXPECTED_MOVIE);
        when(scheduleService.findEntityById(Integer.parseInt(ID))).thenReturn(session);
        when(session.getHall()).thenReturn(hall);
        when(hall.getNumberOfSoldSeats()).thenReturn(0);
        when(session.getStatus()).thenReturn(Status.ACTIVE);
        when(scheduleService.update(SESSION_DTO)).thenReturn(TRUE);
        //when
        String path = scheduleUpdateCommand.performPost(request);
        //then
        assertEquals("redirect:schedule", path);
    }
}

