package service.impl;

import dao.HallDao;
import entities.Hall;
import exceptions.EntityAlreadyExistException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.HallService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class HallServiceImpl implements HallService {
    private static final Logger LOGGER = LogManager.getLogger(HallService.class);
    private HallDao hallDao;


    public HallServiceImpl(HallDao hallDao) {
        this.hallDao = hallDao;
    }

    @Override
    public List<Hall> findAll()  {
        return hallDao.findAll();
    }

    @Override
    public Hall findEntityById(Integer id)  {
        return hallDao.findEntityById(id);
    }

    @Override
    public boolean delete(Hall entity)  {
        return hallDao.delete(entity);
    }

    @Override
    public boolean create(Hall entity) throws  EntityAlreadyExistException {
        return hallDao.create(entity);
    }

    @Override
    public boolean update(Hall entity) throws EntityAlreadyExistException {
        return hallDao.update(entity);
    }

    @Override
    public Hall changeHallCapacity(Hall hallToChange, int newCapacity) throws EntityAlreadyExistException {
        int numberOfSoldSeats = hallToChange.getNumberOfSoldSeats();
        int numberOfAvailableSeats = newCapacity - numberOfSoldSeats;
        Hall hall = getHall(hallToChange, numberOfAvailableSeats, newCapacity, numberOfSoldSeats);
        return hall;
    }

    @Override
    public Hall changeHallNumberOfAvailableSeats(Hall hallToChange, int newAvailableSeats) throws EntityAlreadyExistException {
        int capacity = hallToChange.getCapacity();
        int numberOfSoldSeats = capacity - newAvailableSeats;
        Hall hall = getHall(hallToChange, newAvailableSeats, capacity, numberOfSoldSeats);
        return hall;
    }

    private Hall getHall(Hall hallToChange, int newAvailableSeats, int capacity, int numberOfSoldSeats) throws EntityAlreadyExistException {
        BigDecimal attendance = new BigDecimal((float) numberOfSoldSeats / capacity * 100);
        attendance = attendance.setScale(2, RoundingMode.HALF_UP);
        Hall hall = Hall.builder()
                .id(hallToChange.getId())
                .numberSeats(capacity)
                .numberAvailableSeats(newAvailableSeats)
                .numberOfSoldSeats(numberOfSoldSeats).attendance(attendance).build();
        hallDao.update(hall);
        return hall;
    }

    @Override
    public Hall createWithCapacity(int seatsCapacity)  {
        Hall hall = Hall.builder().numberSeats(seatsCapacity).numberAvailableSeats(seatsCapacity).build();
        hall = hallDao.createAndReturnWithId(hall);
        return hall;
    }

}

