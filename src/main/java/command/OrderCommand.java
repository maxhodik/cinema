package command;

import dto.OrderDto;
import dto.SessionDto;
import entities.Order;
import entities.Session;
import exceptions.DBException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.OrderService;
import service.ScheduleService;
import service.UserService;
import service.impl.OrderServiceImpl;
import service.impl.ScheduleServiceImpl;
import service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;

public class OrderCommand extends MultipleMethodCommand {
    private static final Logger LOGGER = LogManager.getLogger(OrderCommand.class);
    private OrderService orderService;
    private ScheduleService scheduleService;
    private UserService userService;


    public OrderCommand(OrderServiceImpl orderService, ScheduleServiceImpl scheduleService, UserServiceImpl userService) {
        this.orderService = orderService;
        this.scheduleService = scheduleService;
        this.userService = userService;
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
        int id = Integer.parseInt(request.getParameter("id"));
        Order order;
        try {
            order = orderService.submitOrder(id, seats);
            if (order == null) {
                return "WEB-INF/order.jsp";
            }
        } catch (DBException e) {

            throw new RuntimeException(e);
        }
        request.setAttribute("sessionDto", scheduleService.getSessionDto(id));
        request.setAttribute("orderDto", orderService.getOrderDto(order));
        return "WEB-INF/ticket.jsp";
    }


}
