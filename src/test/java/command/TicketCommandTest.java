package command;

import dto.OrderDto;
import entities.*;
import org.junit.jupiter.api.Test;
import service.OrderService;
import service.ScheduleService;
import web.form.validation.IdValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TicketCommandTest {
    private final static String ID = "0";
    private final static LocalTime TIME = LocalTime.parse("09:00");
    private final static LocalDate DATE = LocalDate.parse("2023-02-02");

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
    private ScheduleService scheduleService = mock(ScheduleService.class);
    private IdValidator idValidator= mock(IdValidator.class);
    private final TicketCommand ticketCommand = new TicketCommand(orderService, scheduleService,idValidator);


    @Test
    void performGet() {
        //given
        when(request.getSession()).thenReturn(httpSession);
        when(request.getParameter("id")).thenReturn(ID);
        when(idValidator.validate(any())).thenReturn(false);
        when(orderService.findEntityById(Integer.parseInt(ID))).thenReturn(EXPECTED_ORDER);
        //when
        String path = ticketCommand.performGet(request);
        //then
        verify(request).setAttribute("sessionDto", scheduleService.getSessionDto(0));
        verify(request).setAttribute("orderDto", orderService.getOrderDto(EXPECTED_ORDER));
        assertEquals("WEB-INF/ticket.jsp",path);
    }
    @Test
    void performGetWrongId() {
        //given
        when(idValidator.validate(any())).thenReturn(true);
        //then
        assertThrows(IllegalArgumentException.class, () -> ticketCommand.performGet(request));
    }

    @Test
    void performPost() {
        String path = ticketCommand.performPost(request);
        assertEquals("WEB-INF/ticket.jsp", path);
    }
}