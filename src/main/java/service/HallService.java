package service;

import entities.Hall;
import exceptions.DBException;

import java.util.List;

public interface HallService {
    List<Hall> findAll();
    Hall findEntityById(Integer id);
     boolean delete(Hall entity);
     boolean create(Hall entity) throws DBException;
    boolean update(Hall entity);
}