package command;

import entities.*;
import exceptions.EntityAlreadyExistException;
import exceptions.NotEnoughAvailableSeats;
import org.junit.jupiter.api.Test;
import service.OrderService;
import service.UserService;
import service.impl.ScheduleServiceImpl;
import web.form.validation.IdValidator;
import web.form.validation.OrderValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class OrderCommandTest {
    private final static String ID = "0";
    private final static LocalTime TIME = LocalTime.parse("09:00");
    private final static LocalDate DATE = LocalDate.parse("2023-02-02");
    private final static String SEATS = "5";

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
            .dayOfWeek(DayOfWeek.SATURDAY)
            .build();
    private final static Order EXPECTED_ORDER = Order.builder().id(0).state(State.NEW)
            .numberOfSeats(5).user(USER).price(100).session(SESSION).build();
    private final HttpServletRequest request = mock(HttpServletRequest.class);

    private final HttpSession httpSession = mock(HttpSession.class);
    private OrderService orderService = mock(OrderService.class);
    private final OrderValidator orderValidator = mock(OrderValidator.class);
    private UserService userService = mock(UserService.class);
    private ScheduleServiceImpl scheduleService = mock(ScheduleServiceImpl.class);
    private IdValidator idValidator = mock(IdValidator.class);
    private final OrderCommand orderCommand = new OrderCommand(orderService, scheduleService, userService, orderValidator, idValidator);


    @Test
    void performGet() {
        //given
        when(request.getSession()).thenReturn(httpSession);
        when(request.getParameter("id")).thenReturn(ID);
        when(idValidator.validate(any())).thenReturn(false);
        //when
        String path = orderCommand.performGet(request);
        //then
        verify(request).setAttribute("sessionDto", scheduleService.getSessionDto(0));
        assertEquals("WEB-INF/order.jsp", path);

    }


    @Test
    void performPost() throws EntityAlreadyExistException, NotEnoughAvailableSeats {
        //given
        when(request.getSession()).thenReturn(httpSession);
        when(request.getParameter("id")).thenReturn(ID);
        when(request.getParameter("seats")).thenReturn(SEATS);
        when(idValidator.validate(any())).thenReturn(false);
        when(orderValidator.validate(any())).thenReturn(false);
        when((String) httpSession.getAttribute("name")).thenReturn(USER.getLogin());
        when(orderService.submitOrder(Integer.parseInt(ID), Integer.parseInt(SEATS), USER.getLogin())).thenReturn(EXPECTED_ORDER);
        //when
        String path = orderCommand.performPost(request);
        //then
        assertEquals("redirect:ticket?id=0", path);

    }

    @Test
    void performPostNotEnoughSeats() throws EntityAlreadyExistException, NotEnoughAvailableSeats {
        //given
        when(request.getSession()).thenReturn(httpSession);
        when(request.getParameter("id")).thenReturn(ID);
        when(request.getParameter("seats")).thenReturn(SEATS);
        when(idValidator.validate(any())).thenReturn(false);
        when(orderValidator.validate(any())).thenReturn(false);
        when((String) httpSession.getAttribute("name")).thenReturn(USER.getLogin());
        when(orderService.submitOrder(Integer.parseInt(ID), Integer.parseInt(SEATS), USER.getLogin())).thenThrow(NotEnoughAvailableSeats.class);
        //when
        String path = orderCommand.performPost(request);
        //then

        verify(httpSession).setAttribute("noPlaces", true);
        assertEquals("redirect:order?id=0", path);

    }

    @Test
    void performPostWrongID() throws EntityAlreadyExistException, NotEnoughAvailableSeats {
        //given
        when(request.getSession()).thenReturn(httpSession);
        when(request.getParameter("id")).thenReturn(ID);
        when(request.getParameter("seats")).thenReturn(SEATS);
        when(idValidator.validate(any())).thenReturn(true);
        //then
        assertThrows(IllegalArgumentException.class, () -> orderCommand.performPost(request));
    }

    @Test
    void performPostWrongForm() throws EntityAlreadyExistException, NotEnoughAvailableSeats {
        //given
        when(request.getSession()).thenReturn(httpSession);
        when(request.getParameter("id")).thenReturn(ID);
        when(request.getParameter("seats")).thenReturn(SEATS);
        when(idValidator.validate(any())).thenReturn(false);
        when(orderValidator.validate(any())).thenReturn(true);
        //when
        String path = orderCommand.performPost(request);
        //then
        verify(httpSession).setAttribute("errors", true);
        assertEquals("redirect:order?id=0", path);
    }
}