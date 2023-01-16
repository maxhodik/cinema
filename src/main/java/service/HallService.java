package service;

import entities.Hall;
import exceptions.DBException;

import java.util.List;

public interface HallService {
    List<Hall> findAll();
    Hall findEntityById(Integer id);
     boolean delete(Hall entity);
     boolean create(Hall entity) throws DBException;
    boolean update(Hall entity) throws DBException;
    Hall changeHallCapacity(Hall hallToChange, int newCapacity) throws DBException;
    Hall changeHallNumberOfAvailableSeats(Hall hallToChange, int newAvailableSeats) throws DBException;

    Hall createWithCapacity(int seatsCapacity) throws DBException;

}
