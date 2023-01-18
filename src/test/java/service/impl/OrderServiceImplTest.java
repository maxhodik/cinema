package service.impl;

import dao.OrderDao;
import dto.MovieDto;
import dto.OrderDto;
import entities.*;
import exceptions.EntityAlreadyExistException;
import exceptions.SaveOrderException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

class OrderServiceImplTest {
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
    private final static OrderDto ORDER_DTO = new OrderDto(0, 5, 100, "test");
    private final static List<Order> ORDERS = List.of(EXPECTED_ORDER);

    private static OrderDao orderDao = mock(OrderDao.class);
    private static OrderServiceImpl orderService = new OrderServiceImpl(orderDao);


    @Test
    void submitOrder() {

    }

    @Test
    void delete() {
        when(orderDao.delete(any(Order.class))).thenReturn(false);
        verify(orderDao, never()).delete(EXPECTED_ORDER);
        boolean actual = orderService.delete(EXPECTED_ORDER);
        assertEquals(actual, false);
    }

    @Test
    void createTransactionException() throws SaveOrderException, EntityAlreadyExistException {
        when(orderDao.create(any(Order.class))).thenThrow(SaveOrderException.class);
        SaveOrderException thrown = Assertions.assertThrows(SaveOrderException.class, () ->
                orderService.create(EXPECTED_ORDER));

    }

    @Test
    void createSQLException() throws SaveOrderException, EntityAlreadyExistException {
        when(orderDao.create(any(Order.class))).thenThrow(RuntimeException.class);
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () ->
                orderService.create(EXPECTED_ORDER));

    }

    @Test
    void updateSQLException() throws EntityAlreadyExistException {
        when(orderDao.update(any(Order.class))).thenThrow(RuntimeException.class);
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class,
                () -> orderService.update(EXPECTED_ORDER));

    }

    @Test
    void getOrderDto() {
        OrderDto actual = orderService.getOrderDto(EXPECTED_ORDER);
        assertEquals(ORDER_DTO, actual);
    }

    @Test
    void findAllBySessionId() {
        when(orderDao.findAllBySessionId(anyInt())).thenReturn(Collections.singletonList(EXPECTED_ORDER));
        List<Order> actual = orderService.findAllBySessionId(0);
        verify(orderDao).findAllBySessionId(0);
        assertEquals(ORDERS, actual);


    }

    @Test
    void findEntityById() {
        when(orderDao.findEntityById(anyInt())).thenReturn(EXPECTED_ORDER);
        Order actual = orderService.findEntityById(0);
        verify(orderDao).findEntityById(0);
        assertEquals(EXPECTED_ORDER, actual);
    }
}