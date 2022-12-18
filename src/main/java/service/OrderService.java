package service;

import dto.OrderDto;
import entities.Order;
import exceptions.DBException;
import exceptions.SaveOrderException;

public interface OrderService {
    public boolean delete(Order entity);
    public boolean create(Order entity) throws SaveOrderException, DBException;
    public boolean update(Order entity);
    Order submitOrder (int sessionId, int seats) throws DBException;
    OrderDto getOrderDto(Order order);
}
