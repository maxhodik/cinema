package service.impl;

import command.Operation;
import dao.SessionDao;
import dto.Filter;
import dto.MovieDto;
import dto.SessionAdminDto;
import entities.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.HallService;
import service.MovieService;
import service.OrderService;
import service.ScheduleService;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ScheduleServiceImplTest {
    private final static LocalTime TIME = LocalTime.parse("09:00");
    private final static LocalDate DATE = LocalDate.parse("2023-02-02");
    private static final List<String> FILTER_VALUES = List.of(TIME.toString(), TIME.toString());
    private static final String COLUMN = "time";
    private static final String LIMIT = "LIMIT 5 OFFSET 0";
    private static final Filter FILTER = new Filter(COLUMN, FILTER_VALUES, Operation.BETWEEN);
    private static final List<Filter> FILTERS = List.of(FILTER);

    private final static User USER = User.builder().id(0).login("test").password("password").role(Role.USER).build();
    private final static Movie EXPECTED_MOVIE = new Movie.Builder().id(0).name("test").build();
    private static final Hall HALL = Hall.builder()
            .id(1)
            .numberSeats(100)
            .numberAvailableSeats(90)
            .numberOfSoldSeats(10).attendance(BigDecimal.valueOf(10.00)).build();
    private final static Session SESSION = Session.builder().id(0).hall(HALL)
            .movie(EXPECTED_MOVIE)
            .time(TIME)
            .data(DATE)
            .status(Status.ACTIVE)
            .dayOfWeek(DayOfWeek.THURSDAY)
            .build();
    private static final SessionAdminDto SESSION_ADMIN_DTO = new SessionAdminDto(0, EXPECTED_MOVIE.getName(),
            DATE, TIME, HALL.getNumberAvailableSeats(),
            HALL.getNumberOfSoldSeats(),
            HALL.getCapacity(),
            HALL.getAttendance(),
            Status.ACTIVE);

    private static final List<SessionAdminDto> SESSION_ADMIN_DTO_LIST = List.of(SESSION_ADMIN_DTO);
    private static final List<Session> SESSIONS = List.of(SESSION);
    private static final SessionDao sessionDao = mock(SessionDao.class);
    private static final HallService hallService = mock(HallService.class);
    private static final MovieService movieService = mock(MovieService.class);
    private static final OrderService orderService = mock(OrderService.class);
    private static final ScheduleServiceImpl scheduleService = new ScheduleServiceImpl(sessionDao, hallService, movieService, orderService);
    public static final String COLUMN_NAME = "time";
    @BeforeEach
    public void setUp(){
        reset(sessionDao);
    }

    @Test
    void findAll() {
        when(sessionDao.findAll()).thenReturn(Collections.singletonList(SESSION));
        List<Session> actual = scheduleService.findAll();
        verify(sessionDao).findAll();
//        verifyNoMoreInteractions(sessionDao);
        assertEquals(SESSIONS, actual);
    }

    @Test
    void findAllThrowException() {
        when(sessionDao.findAll()).thenThrow(RuntimeException.class);
        Assertions.assertThrows(RuntimeException.class, () -> scheduleService.findAll());
    }


    @Test
    void findAllFilterByAndOrderBy() {
        when(sessionDao.findAllFilterByAvailableViewing(anyString(), anyString(), anyString())).thenReturn(Collections.singletonList(SESSION));
        List<SessionAdminDto> actual = scheduleService.findAllFilterByAndOrderBy(FILTERS, COLUMN, LIMIT);
        verify(sessionDao).findAllFilterByAvailableViewing(" WHERE sessions.time BETWEEN '09:00' AND '09:00'", "sessions.time", LIMIT);
        assertEquals(SESSION_ADMIN_DTO_LIST, actual);
    }

    @Test
    void findAllFilterByAndOrderByThrowException() {
        when(sessionDao.findAllFilterByAvailableViewing(anyString(), anyString(), anyString())).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> scheduleService.findAllFilterByAndOrderBy(FILTERS, COLUMN, LIMIT));
    }


    @Test
    void findAllFilterBy() {
        when(sessionDao.findAllFilterByAvailableViewing(anyString())).thenReturn(Collections.singletonList(SESSION));
        List<SessionAdminDto> actual = scheduleService.findAllFilterBy(FILTERS);
        verify(sessionDao).findAllFilterByAvailableViewing(" WHERE sessions.time BETWEEN '09:00' AND '09:00'");
        assertEquals(SESSION_ADMIN_DTO_LIST, actual);
    }
    @Test
    void findAllFilterByThrowException() {
        when(sessionDao.findAllFilterByAvailableViewing(anyString())).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, ()-> scheduleService.findAllFilterBy(FILTERS));

    }
    @Test
    void findAllOrderByThrowException() {
        when(sessionDao.findAllOrderBy(anyString())).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, ()-> scheduleService.findAllOrderBy(COLUMN_NAME));
    }

    @Test
    void findEntityById() {

    }

    @Test
    void delete() {
    }

    @Test
    void updateStatus() {

    }

    @Test
    void update() {
    }

    @Test
    void create() {
    }

    @Test
    void getSessionDto() {
    }

    @Test
    void getSessionAdminDtoList() {
    }

    @Test
    void getNumberOfRecords() {
    }
}