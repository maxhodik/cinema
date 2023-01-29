package command;

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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ScheduleDeleteCommandTest {
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
    private static final String[] MOVIE_DTOS = {"test"};
    private final static User USER = User.builder().id(0).login("test").password("password").role(Role.USER).build();
    private final static Session SESSION = Session.builder().id(0).hall(HALL)
            .movie(EXPECTED_MOVIE)
            .time(TIME)
            .data(DATE)
            .status(Status.ACTIVE)
            .dayOfWeek(DayOfWeek.THURSDAY)
            .build();
    private final ScheduleService scheduleService = mock(ScheduleService.class);
    private final MovieService movieService = mock(MovieService.class);
    private final HallService hallService = mock(HallService.class);
    private final SessionFormValidator sessionValidator = mock(SessionFormValidator.class);
    private final IdValidator idValidator = mock(IdValidator.class);


    private final HttpServletRequest request = mock(HttpServletRequest.class);

    private final HttpSession httpSession = mock(HttpSession.class);
    private final ScheduleDeleteCommand scheduleDeleteCommand = new ScheduleDeleteCommand(scheduleService, idValidator);

    @Test
    void performGet() {
        String path = scheduleDeleteCommand.performGet(request);
        assertEquals("WEB-INF/admin/schedule-admin.jsp", path);
    }

    @Test
    void performPostWrongID() {
        //given
        when(request.getSession()).thenReturn(httpSession);
        when(request.getParameter("id")).thenReturn(ID);
        when(idValidator.validate(any())).thenReturn(true);
        //then
        assertThrows(IllegalArgumentException.class, () -> scheduleDeleteCommand.performPost(request));
    }

    @Test
    void performPost() {
        //given
        when(request.getSession()).thenReturn(httpSession);
        when(request.getParameter("id")).thenReturn(ID);
        when(idValidator.validate(any())).thenReturn(false);
        when(scheduleService.findEntityById(Integer.parseInt(ID))).thenReturn(SESSION);
        //when
        String path = scheduleDeleteCommand.performPost(request);
        //then
        assertEquals("redirect:schedule", path);


    }


}