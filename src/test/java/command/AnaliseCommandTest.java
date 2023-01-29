package command;

import dto.Filter;
import dto.MovieDto;
import dto.SessionAdminDto;
import entities.*;
import org.junit.jupiter.api.Test;
import service.HallService;
import service.MovieService;
import service.ScheduleService;
import web.form.validation.AnaliseFormValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class AnaliseCommandTest {
    private final static LocalTime TIME = LocalTime.parse("09:00");
    private final static LocalTime TIME2 = LocalTime.parse("18:00");

    private final static int NUMBER_OF_RECORDS = 1;

    private final static LocalDate DATE = LocalDate.parse("2023-02-02");
    private static final List<String> FILTER_VALUES = List.of("test");
    private static final Filter FILTER = new Filter("movie", FILTER_VALUES, Operation.IN);
    private static final List<Filter> FILTERS = List.of(FILTER);
    private static final String LIMIT = " LIMIT 5 OFFSET 0";
    private static final boolean TRUE = true;

    private static final String[] MOVIE_DTOS = {"test"};
    private static final List<String> MOVIE_NAMES = List.of("test");
    private final static Movie EXPECTED_MOVIE = new Movie.Builder().id(0).name("test").build();
    private static final Hall HALL = Hall.builder()
            .id(1)
            .numberSeats(100)
            .numberAvailableSeats(90)
            .numberOfSoldSeats(10).attendance(BigDecimal.valueOf(10.00)).build();
    private static final SessionAdminDto SESSION_ADMIN_DTO = new SessionAdminDto(0, EXPECTED_MOVIE.getName(),
            DATE, TIME, HALL.getNumberAvailableSeats(),
            HALL.getNumberOfSoldSeats(),
            HALL.getCapacity(),
            HALL.getAttendance(),
            Status.ACTIVE);
    private static final List<SessionAdminDto> SESSION_ADMIN_DTO_LIST = List.of(SESSION_ADMIN_DTO);


    private final static String ORDER_BY = "date";
    private final ScheduleService scheduleService = mock(ScheduleService.class);
    private final Pagination pagination = mock(Pagination.class);
    private final MovieService movieService = mock(MovieService.class);
    private final HallService hallService = mock(HallService.class);
    private final AnaliseFormValidator analiseValidator = mock(AnaliseFormValidator.class);

    private final HttpServletRequest request = mock(HttpServletRequest.class);

    private final HttpSession httpSession = mock(HttpSession.class);
    private final AnaliseCommand analiseCommand = new AnaliseCommand(scheduleService, movieService, hallService, analiseValidator, pagination);

    @Test
    void performGet() {
        //given
        when(request.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute("filters")).thenReturn(FILTERS);
        when(request.getParameter("orderBy")).thenReturn(ORDER_BY);
        when(request.getParameterValues("number_available_seats")).thenReturn(null);
        when(scheduleService.getNumberOfRecords(FILTERS)).thenReturn(NUMBER_OF_RECORDS);
        when(scheduleService.findAllFilterByAndOrderBy(any(), eq(ORDER_BY), eq(LIMIT))).thenReturn(SESSION_ADMIN_DTO_LIST);
        when(scheduleService.findAllFilterBy(FILTERS)).thenReturn(SESSION_ADMIN_DTO_LIST);
        //when
        String path = analiseCommand.performGet(request);
        //then
        verify(request).setAttribute("sessionAdminDto", SESSION_ADMIN_DTO_LIST);
        verify(request).setAttribute("movieDto", MOVIE_NAMES);
        assertEquals("/WEB-INF/admin/analise.jsp", path);
    }

    @Test
    void performGetFilterNull() {
        //given
        when(request.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute("filters")).thenReturn(null);
        when(request.getParameter("orderBy")).thenReturn(ORDER_BY);
        when(request.getParameterValues("number_available_seats")).thenReturn(null);
        when(scheduleService.getNumberOfRecords(null)).thenReturn(NUMBER_OF_RECORDS);
        when(scheduleService.findAllFilterByAndOrderBy(any(), eq(ORDER_BY), eq(LIMIT))).thenReturn(SESSION_ADMIN_DTO_LIST);
        when(scheduleService.findAllFilterBy(null)).thenReturn(SESSION_ADMIN_DTO_LIST);
        //when
        String path = analiseCommand.performGet(request);
        //then
        verify(request).setAttribute("sessionAdminDto", SESSION_ADMIN_DTO_LIST);
        verify(request).setAttribute("movieDto", MOVIE_NAMES);
        assertEquals("/WEB-INF/admin/analise.jsp", path);

    }

    @Test
    void performPostFilterReset() {
        //given
        when(request.getSession()).thenReturn(httpSession);
        when(request.getParameter("reset")).thenReturn("true");
        //when
        String path = analiseCommand.performPost(request);
        //then
        verify(httpSession).setAttribute("filters", null);
    }

    @Test
    void performPost() {
        //given
        when(request.getSession()).thenReturn(httpSession);
        when(request.getParameter("reset")).thenReturn(null);
        when(httpSession.getAttribute("filters")).thenReturn(null);
        when(request.getParameterValues("date")).thenReturn(null);
        when(request.getParameterValues("movie")).thenReturn(MOVIE_DTOS);
        when(request.getParameterValues("status")).thenReturn(null);
        when(request.getParameterValues("day")).thenReturn(null);
        when(request.getParameterValues("time")).thenReturn(null);
        when(request.getParameter("orderBy")).thenReturn(ORDER_BY);
        when(analiseValidator.validate(any())).thenReturn(false);
        when(scheduleService.getNumberOfRecords(FILTERS)).thenReturn(1);
        when(scheduleService.getNumberOfRecords(FILTERS)).thenReturn(NUMBER_OF_RECORDS);
        when(scheduleService.findAllFilterByAndOrderBy(any(), eq(ORDER_BY), eq(LIMIT))).thenReturn(SESSION_ADMIN_DTO_LIST);
        when(scheduleService.findAllFilterBy(any())).thenReturn(SESSION_ADMIN_DTO_LIST);
        //when
        String path = analiseCommand.performPost(request);
        //then
        verify(httpSession).setAttribute("filters", FILTERS);
        verify(request).setAttribute("sessionAdminDto", SESSION_ADMIN_DTO_LIST);
        verify(request).setAttribute("movieDto", MOVIE_NAMES);
        assertEquals("/WEB-INF/admin/analise.jsp", path);
    }

    @Test
    void performPostFormNotValid() {
        //given
        when(request.getSession()).thenReturn(httpSession);
        when(request.getParameter("reset")).thenReturn(null);
        when(httpSession.getAttribute("filters")).thenReturn(null);
        when(request.getParameterValues("date")).thenReturn(null);
        when(request.getParameterValues("movie")).thenReturn(MOVIE_DTOS);
        when(request.getParameterValues("status")).thenReturn(null);
        when(request.getParameterValues("day")).thenReturn(null);
        when(request.getParameterValues("time")).thenReturn(null);
        when(request.getParameter("orderBy")).thenReturn(ORDER_BY);
        when(analiseValidator.validate(any())).thenReturn(TRUE);
        //when
        String path = analiseCommand.performPost(request);
        //then
        verify(request).setAttribute("errors", true);
        assertEquals("/WEB-INF/admin/analise.jsp", path);
    }
}