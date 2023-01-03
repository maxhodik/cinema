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
        int id = Integer.parseInt(request.getParameter("id"));
        SessionDto sessionDto = scheduleService.getSessionDto(id);
        request.setAttribute("sessionDto", sessionDto);

        return "WEB-INF/order.jsp";
    }

    @Override
    public String performPost(HttpServletRequest request) {
        int seats = Integer.parseInt(request.getParameter("seats"));
        OrderForm orderForm= new OrderForm(seats);
        if (orderValidator.validate(orderForm)) {
            request.setAttribute("errors", true);
            return "WEB-INF/order.jsp";
        }
        int id = Integer.parseInt(request.getParameter("id"));
        String userLogin= (String) request.getSession().getAttribute("name");
        Order order;
        try {
            order = orderService.submitOrder(id, seats, userLogin);
            if (order == null) {
                return "WEB-INF/order.jsp";
            }
        } catch (DBException | NotEnoughAvailableSeats e) {

            throw new RuntimeException(e);
        }
        request.setAttribute("sessionDto", scheduleService.getSessionDto(id));
        request.setAttribute("orderDto", orderService.getOrderDto(order));
        return "WEB-INF/ticket.jsp";
    }


}
