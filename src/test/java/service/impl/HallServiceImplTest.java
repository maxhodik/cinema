package service.impl;

import dao.HallDao;
import dao.impl.SqlHallDao;
import entities.Hall;
import exceptions.EntityAlreadyExistException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import persistance.TransactionManager;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HallServiceImplTest {

    private static final int NEW_CAPACITY = 50;
    private static final int NEW_AVAILABLE_SEATS = 50;
    private static final Hall HALL = Hall.builder()
            .id(1)
            .numberSeats(100)
            .numberAvailableSeats(90)
            .numberOfSoldSeats(10).attendance(BigDecimal.valueOf(10.00)).build();
    private static final Hall EXPECTED_HALL_AVAILABLE_SEATS = Hall.builder()
            .id(1)
            .numberSeats(100)
            .numberAvailableSeats(50)
            .numberOfSoldSeats(50).attendance(BigDecimal.valueOf(5000,2)).build();

    private static final Hall EXPECTED_HALL = Hall.builder()
            .id(1)
            .numberSeats(50)
            .numberAvailableSeats(40)
            .numberOfSoldSeats(10).attendance(BigDecimal.valueOf(2000,2)).build();
    private final HallDao hallDao = mock(HallDao.class);
    private final HallServiceImpl hallService = new HallServiceImpl(hallDao);

    @Test
    void changeHallCapacity() throws EntityAlreadyExistException {
        when(hallDao.update(any(Hall.class))).thenReturn(true);
        Hall hall = hallService.changeHallCapacity(HALL, NEW_CAPACITY);
        assertEquals(EXPECTED_HALL, hall);

    }

    @Test
    void changeHallNumberOfAvailableSeats() throws EntityAlreadyExistException {
        when(hallDao.update(any(Hall.class))).thenReturn(true);
        Hall hall = hallService.changeHallNumberOfAvailableSeats(HALL, NEW_AVAILABLE_SEATS);
        assertEquals(EXPECTED_HALL_AVAILABLE_SEATS, hall);
    }


    @Test
    public void changeHallCapacityTrowsException() throws EntityAlreadyExistException {


     when(hallDao.update(any(Hall.class))).thenThrow(EntityAlreadyExistException.class);
        EntityAlreadyExistException thrown = Assertions.assertThrows(EntityAlreadyExistException.class, () ->
                hallService.changeHallCapacity(HALL, NEW_CAPACITY));

    }
}