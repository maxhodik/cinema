package service.impl;

import command.ScheduleCommand;
import dao.HallDao;
import dao.OrderDao;
import dto.OrderDto;
import entities.*;
import exceptions.EntityAlreadyExistException;
import exceptions.NotEnoughAvailableSeats;
import exceptions.SaveOrderException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import persistance.TransactionManagerWrapper;
import service.HallService;
import service.OrderService;
import service.ScheduleService;
import service.UserService;

import java.sql.SQLException;
import java.util.List;


public class OrderServiceImpl implements OrderService {

    private static final Logger LOGGER = LogManager.getLogger(ScheduleCommand.class);
    private OrderDao orderDao;

    private HallService hallService;
    private ScheduleService scheduleService;
    private UserService userService;

    public OrderServiceImpl(OrderDao orderDao, HallService hallService, ScheduleService scheduleService, UserService userService) {
        this.orderDao = orderDao;
        this.hallService = hallService;
        this.scheduleService = scheduleService;
        this.userService = userService;
    }

    public OrderServiceImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }


    @Override
    public Order submitOrder(int sessionId, int seats, String userLogin) throws NotEnoughAvailableSeats, EntityAlreadyExistException {

        try {
            TransactionManagerWrapper.startTransaction();
            Session session = scheduleService.findEntityById(sessionId);
            Hall hall = session.getHall();

            int availableSeats = hall.getNumberAvailableSeats();
            int numberOfAvailableSeats = availableSeats - seats;
            if (numberOfAvailableSeats < 0) {
                TransactionManagerWrapper.rollback();
                LOGGER.info("Transaction rollback, cause: Not enough available seats");
                throw new NotEnoughAvailableSeats("Not enough available seats");
            }
            User user = userService.findEntityByLogin(userLogin);
            Order order = Order.builder()
                    .state(State.getByNameIgnoringCase("NEW"))
                    .session(session)
                    .numberOfSeats(seats)
                    .user(user)
                    .price(100).build();
            if (createAndReturnWithId(order) != null) {
                hall = hallService.changeHallNumberOfAvailableSeats(hall, numberOfAvailableSeats);
                hallService.update(hall);
            }
            TransactionManagerWrapper.commit();
            LOGGER.info("Order submit success");
            return order;
        } catch (SQLException e) {

            try {
                TransactionManagerWrapper.rollback();
                LOGGER.info("Transaction rollback", e);
                throw new RuntimeException("Transaction rollback");
            } catch (SQLException ex) {
                throw new RuntimeException("Not rollback transaction");
            }

        }
    }

    @Override
    public boolean delete(Order entity) {
        return false;
    }

    @Override
    public boolean create(Order entity) throws SaveOrderException, EntityAlreadyExistException {
        return orderDao.create(entity);
    }

    @Override
    public Order createAndReturnWithId(Order entity) {
        return orderDao.createAndReturnWithId(entity);
    }

    @Override
    public boolean update(Order entity) throws EntityAlreadyExistException {
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

    @Override
    public Boolean canceledOrder(Integer id) throws EntityAlreadyExistException {
        List<Order> orders = findAllBySessionId(id);
        for (Order o : orders) {
            Order updatedOrder = o;
            updatedOrder.setState(State.CANCELED);

            update(updatedOrder);

        }
        return true;

    }
}
