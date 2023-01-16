package command;

import dto.SessionDto;
import entities.Order;
import exceptions.DBException;
import exceptions.NotEnoughAvailableSeats;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.OrderService;
import service.ScheduleService;
import service.UserService;
import service.impl.OrderServiceImpl;
import service.impl.ScheduleServiceImpl;
import service.impl.UserServiceImpl;
import web.form.OrderForm;
import web.form.validation.OrderFormValidator;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public class OrderCommand extends MultipleMethodCommand {
    private static final Logger LOGGER = LogManager.getLogger(OrderCommand.class);
    private OrderService orderService;
    private ScheduleService scheduleService;
    private UserService userService;
    private OrderFormValidator orderValidator;


    public OrderCommand(OrderServiceImpl orderService, ScheduleServiceImpl scheduleService, UserServiceImpl userService, OrderFormValidator orderValidator) {
        this.orderService = orderService;
        this.scheduleService = scheduleService;
        this.userService = userService;
        this.orderValidator = orderValidator;
    }


    @Override
    public String performGet(HttpServletRequest request) {
        int id;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            LOGGER.info("Order form not valid");
            request.setAttribute("errors", true);
            return "WEB-INF/order.jsp";
        }
        SessionDto sessionDto = scheduleService.getSessionDto(id);
        request.setAttribute("sessionDto", sessionDto);

        return "WEB-INF/order.jsp";
    }

    @Override
    public String performPost(HttpServletRequest request) {
        //todo redirect
        String seats = (request.getParameter("seats"));
        OrderForm orderForm = new OrderForm(seats);
        if (orderValidator.validate(orderForm)) {
            LOGGER.info("Order form not valid");
            request.setAttribute("errors", true);
            return "WEB-INF/order.jsp";
        }
        int id = Integer.parseInt(request.getParameter("id"));
        String userLogin = (String) request.getSession().getAttribute("name");
        int numberOfSeats = Integer.parseInt(seats);
        Order order;
        try {
            order = orderService.submitOrder(id, numberOfSeats, userLogin);
            if (order == null) {
                return "WEB-INF/order.jsp";
            }
        } catch (DBException | NotEnoughAvailableSeats e) {
            LOGGER.info("Not enough available seats");
            request.setAttribute("noPlaces", true);
                      return "WEB-INF/order.jsp";
        }
        request.setAttribute("sessionDto", scheduleService.getSessionDto(id));
        request.setAttribute("orderDto", orderService.getOrderDto(order));
        return "WEB-INF/ticket.jsp";
    }


}
