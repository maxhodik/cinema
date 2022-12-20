package service.impl;

import dao.HallDao;
import dao.OrderDao;
import dao.SessionDao;
import dao.UserDao;
import dto.OrderDto;
import entities.*;
import exceptions.DBException;
import exceptions.SaveOrderException;
import service.OrderService;

public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao;
    private HallDao hallDao;
    private SessionDao sessionDao;
    private UserDao userDao;

    public OrderServiceImpl(OrderDao orderDao, HallDao hallDao, SessionDao sessionDao, UserDao userDao) {
        this.orderDao = orderDao;
        this.sessionDao = sessionDao;
        this.hallDao = hallDao;
        this.userDao = userDao;

    }

    @Override
    public Order submitOrder(int sessionId, int seats, String userLogin) throws DBException {
        Session session = sessionDao.findEntityById(sessionId);
        Hall hall = session.getHall();
       int hallId = hall.getId();
// todo create user by Session
        User user = userDao.findEntityByLogin(userLogin);
        int availableSeats = hall.getNumberAvailableSeats();
        int numberOfAvailableSeats = availableSeats - seats;
        if (numberOfAvailableSeats < 0) {
            return null;
        }
        Order order = Order.builder()
                .state(State.getByNameIgnoringCase("NEW"))
                .session(session)
                .numberOfSeats(seats)
                .user(user)
                .price(100).build();
        if (create(order)) {
            hall.setNumberAvailableSeats(numberOfAvailableSeats);
            hallDao.update(hall);
        }
return order;
    }

    @Override
    public boolean delete(Order entity) {
        return false;
    }

    @Override
    public boolean create(Order entity) throws SaveOrderException, DBException {
        return orderDao.create(entity);
    }

    @Override
    public boolean update(Order entity) {
        return false;
    }
@Override
    public OrderDto getOrderDto(Order order) {

        int orderId = order.getId();
        String login = order.getUser().getLogin();
        int numberOfSeats = order.getNumberOfSeats();
        int price = order.getPrice();
        return new OrderDto(orderId, numberOfSeats, price, login);
    }
}
