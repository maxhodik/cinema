package service;

import dto.OrderDto;
import entities.Order;
import exceptions.DBException;
import exceptions.EntityAlreadyExistException;
import exceptions.NotEnoughAvailableSeats;
import exceptions.SaveOrderException;

import java.util.List;

public interface OrderService {
    public boolean delete(Order entity);
    public boolean create(Order entity) throws SaveOrderException, EntityAlreadyExistException;
    public boolean update(Order entity) throws  EntityAlreadyExistException;
    Order submitOrder (int sessionId, int seats, String userLogin) throws  NotEnoughAvailableSeats, EntityAlreadyExistException;
    OrderDto getOrderDto(Order order);
    List<Order> findAllBySessionId (Integer id) ;
    Order findEntityById(Integer id);
}
