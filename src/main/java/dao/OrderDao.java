package dao;

import entities.Order;

import java.util.List;

public interface OrderDao extends GenericDao<Integer, Order> {

    List<Order> findEntityBySessionId(Integer id);
}
