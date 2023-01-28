package command;

import dto.SessionDto;
import entities.Order;
import exceptions.EntityAlreadyExistException;
import exceptions.NotEnoughAvailableSeats;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.OrderService;
import service.ScheduleService;
import service.UserService;
import web.form.IdForm;
import web.form.OrderForm;
import web.form.validation.IdValidator;
import web.form.validation.OrderValidator;

import javax.servlet.http.HttpServletRequest;

public class OrderCommand extends MultipleMethodCommand {
    private static final Logger LOGGER = LogManager.getLogger(OrderCommand.class);
    private OrderService orderService;
    private ScheduleService scheduleService;
    private UserService userService;
    private OrderValidator orderValidator;
    private IdValidator idValidator;


    public OrderCommand(OrderService orderService, ScheduleService scheduleService,
                        UserService userService, OrderValidator orderValidator, IdValidator idValidator) {
        this.orderService = orderService;
        this.scheduleService = scheduleService;
        this.userService = userService;
        this.orderValidator = orderValidator;
        this.idValidator = idValidator;
    }


    @Override
    public String performGet(HttpServletRequest request) {
        int orderId = getId(request);
        SessionDto sessionDto = scheduleService.getSessionDto(orderId);
        request.setAttribute("sessionDto", sessionDto);
        return "WEB-INF/order.jsp";
    }

    private int getId(HttpServletRequest request) {
        String id = request.getParameter("id");
        IdForm idForm = new IdForm(id);
        if (idValidator.validate(idForm)) {
            LOGGER.error("Illegal order id");
            throw new IllegalArgumentException();
        }
        int entityId = Integer.parseInt(id);
        return entityId;
    }

    @Override
    public String performPost(HttpServletRequest request) {
        int sessionId = getId(request);
        String seats = (request.getParameter("seats"));
        OrderForm orderForm = new OrderForm(seats);
        if (orderValidator.validate(orderForm)) {
            LOGGER.info("Order form not valid");
            request.getSession().setAttribute("errors", true);
            return "redirect:order?id=" + sessionId;
        }
        String userLogin = (String) request.getSession().getAttribute("name");
        int numberOfSeats = Integer.parseInt(seats);
        Order order;
        try {
            order = orderService.submitOrder(sessionId, numberOfSeats, userLogin);

        } catch (NotEnoughAvailableSeats | EntityAlreadyExistException e) {
            LOGGER.info("Not enough available seats");
            request.getSession().setAttribute("noPlaces", true);
            return "redirect:order?id=" + sessionId;
        }
        int orderId = order.getId();
        return "redirect:ticket?id=" + orderId;

    }


}
