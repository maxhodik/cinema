package dao;

import entities.Order;
import exceptions.DBException;

import java.util.List;

public interface OrderDao extends GenericDao<Integer, Order> {

    List<Order> findAllBySessionId(Integer id);
    Order findEntityById(Integer id);
    Order createAndReturnWithId(Order entity);
}
