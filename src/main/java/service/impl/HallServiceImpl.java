package service.impl;

import dao.HallDao;
import entities.Hall;
import exceptions.DBException;
import service.HallService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class HallServiceImpl implements HallService {
    private HallDao hallDao;

    public HallServiceImpl(HallDao hallDao) {
        this.hallDao = hallDao;
    }

    @Override
    public List<Hall> findAll() {
        return hallDao.findAll();
    }

    @Override
    public Hall findEntityById(Integer id) {
        return hallDao.findEntityById(id);
    }

    @Override
    public boolean delete(Hall entity) {
        return hallDao.delete(entity);
    }

    @Override
    public boolean create(Hall entity) throws DBException {
        return hallDao.create(entity);
    }

    @Override
    public boolean update(Hall entity) {
        return hallDao.update(entity);
    }

    @Override
    public Hall changeHallCapacity(Hall hallToChange, int newCapacity) {
        int numberOfSoldSeats = hallToChange.getNumberOfSoldSeats();
        int numberOfAvailableSeats = newCapacity - numberOfSoldSeats;

        BigDecimal attendance = new BigDecimal((float) numberOfSoldSeats / newCapacity * 100);
        attendance = attendance.setScale(2, RoundingMode.HALF_UP);
        // todo calculate hall params based on new capacity
        Hall hall = Hall.builder()
                .id(hallToChange.getId())
                .numberSeats(newCapacity)
                .numberAvailableSeats(numberOfAvailableSeats)
                .numberOfSoldSeats(numberOfSoldSeats).attendance(attendance).build();
        return hall;
    }

    @Override
    public Hall changeHallNumberOfAvailableSeats(Hall hallToChange, int newAvailableSeats) {
        int capacity = hallToChange.getCapacity();
        int numberOfSoldSeats = capacity - newAvailableSeats;

        BigDecimal attendance = new BigDecimal((float) numberOfSoldSeats / capacity * 100);
        attendance = attendance.setScale(2, RoundingMode.HALF_UP);
        // todo calculate hall params based on new capacity
        Hall hall = Hall.builder()
                .id(hallToChange.getId())
                .numberSeats(capacity)
                .numberAvailableSeats(newAvailableSeats)
                .numberOfSoldSeats(numberOfSoldSeats).attendance(attendance).build();
        return hall;
    }

    @Override
    //todo check exceptions
    public Hall createWithCapacity(int seatsCapacity) throws DBException {
        Hall hall = Hall.builder().numberSeats(seatsCapacity).numberAvailableSeats(seatsCapacity).build();
        hall = hallDao.createAndReturnWithId(hall);
        return hall;
    }

}

