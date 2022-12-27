package service.impl;

import dao.HallDao;
import dao.OrderDao;
import dao.SessionDao;
import dao.UserDao;
import dto.OrderDto;
import entities.*;
import exceptions.DBException;
import exceptions.NotEnoughAvailableSeats;
import exceptions.SaveOrderException;
import service.HallService;
import service.OrderService;

import java.util.List;

public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao;
    private HallDao hallDao;
    private HallService hallService;
    private SessionDao sessionDao;
    private UserDao userDao;

    public OrderServiceImpl(OrderDao orderDao, HallDao hallDao, HallService hallService, SessionDao sessionDao, UserDao userDao) {
        this.orderDao = orderDao;
        this.hallDao = hallDao;
        this.hallService = hallService;
        this.sessionDao = sessionDao;
        this.userDao = userDao;
    }

    public OrderServiceImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }


    @Override
    public Order submitOrder(int sessionId, int seats, String userLogin) throws DBException, NotEnoughAvailableSeats {
   // todo transaction
        Session session = sessionDao.findEntityById(sessionId);
        Hall hall = session.getHall();
        int hallId = hall.getId();
        User user = userDao.findEntityByLogin(userLogin);
        int availableSeats = hall.getNumberAvailableSeats();
        int numberOfAvailableSeats = availableSeats - seats;
        if (numberOfAvailableSeats < 0) {
                throw new NotEnoughAvailableSeats("Not enough available seats");
            }

        Order order = Order.builder()
                .state(State.getByNameIgnoringCase("NEW"))
                .session(session)
                .numberOfSeats(seats)
                .user(user)
                .price(100).build();
        if (create(order)) {
            hall = hallService.changeHallNumberOfAvailableSeats(hall, numberOfAvailableSeats);
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
        return orderDao.update(entity);
    }

    @Override
    public OrderDto getOrderDto(Order order) {

        int orderId = order.getId();
        String login = order.getUser().getLogin();
        int numberOfSeats = order.getNumberOfSeats();
        int price = order.getPrice();
        return new OrderDto(orderId, numberOfSeats, price, login);
    }

    @Override
    public List<Order> findAllBySessionId(Integer id) {
        return orderDao.findAllBySessionId(id);
    }

    @Override
    public Order findEntityById(Integer id) {
        return orderDao.findEntityById(id);
    }
}
