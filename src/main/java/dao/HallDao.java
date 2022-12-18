package dao;

import entities.Hall;
import entities.Order;
import exceptions.DBException;

import java.util.List;

public interface HallDao extends GenericDao<Integer,Hall> {
    int createId (Hall entity) throws DBException;

}
