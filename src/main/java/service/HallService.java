package service;

import entities.Hall;
import exceptions.DBException;
import exceptions.EntityAlreadyExistException;

import java.util.List;

public interface HallService {
    List<Hall> findAll();

    Hall findEntityById(Integer id);

    boolean delete(Hall entity);

    boolean create(Hall entity) throws EntityAlreadyExistException;

    boolean update(Hall entity) throws EntityAlreadyExistException;

    Hall changeHallCapacity(Hall hallToChange, int newCapacity) throws EntityAlreadyExistException;

    Hall changeHallNumberOfAvailableSeats(Hall hallToChange, int newAvailableSeats) throws EntityAlreadyExistException;

    Hall createWithCapacity(int seatsCapacity);

}
