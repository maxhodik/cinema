package command;

import dto.Filter;
import dto.MovieDto;
import dto.SessionAdminDto;
import entities.*;
import exceptions.EntityAlreadyExistException;
import exceptions.NotEnoughAvailableSeats;
import org.junit.jupiter.api.Test;
import service.ScheduleService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ScheduleCommandTest {
    private final static LocalTime TIME = LocalTime.parse("09:00");
    private final static LocalDate DATE = LocalDate.parse("2023-02-02");
    private static final String LIMIT = " LIMIT 5 OFFSET 0";

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
    private final HttpServletRequest request = mock(HttpServletRequest.class);

    private final HttpSession httpSession = mock(HttpSession.class);
    private final ScheduleCommand scheduleCommand = new ScheduleCommand(scheduleService, pagination);

    @Test
    void performGet() {
        //given
        when(request.getSession()).thenReturn(httpSession);
        when(request.getParameter("reset")).thenReturn(null);
        when(request.getSession().getAttribute("filters")).thenReturn(null);
        when(request.getParameter("orderBy")).thenReturn(ORDER_BY);
        when(request.getParameterValues("number_available_seats")).thenReturn(null);
        when(request.getParameterValues("movie")).thenReturn(MOVIE_DTOS);
        when(scheduleService.findAllFilterByAndOrderBy(any(), eq(ORDER_BY), eq(LIMIT))).thenReturn(SESSION_ADMIN_DTO_LIST);
        //when
        String path = scheduleCommand.performGet(request);
        //then
        verify(request).setAttribute("sessionAdminDto", SESSION_ADMIN_DTO_LIST);
        verify(request).setAttribute("movieDto", MOVIE_NAMES);
        assertEquals("WEB-INF/schedule.jsp", path);
    }

    @Test
    void performGetFilterReset() {
        //given
        when(request.getSession()).thenReturn(httpSession);
        when(request.getParameter("reset")).thenReturn("true");
        //when
        String path = scheduleCommand.performGet(request);
        //then
        verify(httpSession).setAttribute("filters", null);
      }

    @Test
    void performGetAdmin() {
        //given
        when(request.getSession()).thenReturn(httpSession);
        when(request.getParameter("reset")).thenReturn(null);
        when(request.getSession().getAttribute("filters")).thenReturn(null);
        when(request.getParameter("orderBy")).thenReturn(ORDER_BY);
        when(request.getParameterValues("number_available_seats")).thenReturn(null);
        when(request.getParameterValues("movie")).thenReturn(MOVIE_DTOS);
        when(scheduleService.findAllFilterByAndOrderBy(any(), eq(ORDER_BY), eq(LIMIT))).thenReturn(SESSION_ADMIN_DTO_LIST);
        when(httpSession.getAttribute("role")).thenReturn(Role.ADMIN);
        //when
        String path = scheduleCommand.performGet(request);
        //then
        verify(request).setAttribute("sessionAdminDto", SESSION_ADMIN_DTO_LIST);
        verify(request).setAttribute("movieDto", MOVIE_NAMES);
        assertEquals("WEB-INF/admin/schedule-admin.jsp", path);
    }

    @Test
    void performGetNoAdmin() {
        //given
        when(request.getSession()).thenReturn(httpSession);
        when(request.getParameter("reset")).thenReturn(null);
        when(request.getSession().getAttribute("filters")).thenReturn(null);
        when(request.getParameter("orderBy")).thenReturn(ORDER_BY);
        when(request.getParameterValues("number_available_seats")).thenReturn(null);
        when(request.getParameterValues("movie")).thenReturn(MOVIE_DTOS);
        when(scheduleService.findAllFilterByAndOrderBy(any(), eq(ORDER_BY), eq(LIMIT))).thenReturn(SESSION_ADMIN_DTO_LIST);
        when(httpSession.getAttribute("role")).thenReturn(Role.USER);
        //when
        String path = scheduleCommand.performGet(request);
        //then
        verify(request).setAttribute("sessionAdminDto", SESSION_ADMIN_DTO_LIST);
        verify(request).setAttribute("movieDto", MOVIE_NAMES);
        assertEquals("WEB-INF/schedule.jsp", path);
    }

    @Test
    void performPost() {
        //given
        when(request.getSession()).thenReturn(httpSession);
        //when
        String path = scheduleCommand.performPost(request);
        //then
        assertEquals("admin/schedule-admin.jsp", path);
    }
}