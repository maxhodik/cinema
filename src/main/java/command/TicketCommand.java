package command;

import entities.Order;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.OrderService;
import service.ScheduleService;

import javax.servlet.http.HttpServletRequest;

public class TicketCommand extends MultipleMethodCommand {

    private static final Logger LOGGER = LogManager.getLogger(TicketCommand.class);
    private OrderService orderService;
    private ScheduleService scheduleService;


    public TicketCommand(OrderService orderService, ScheduleService scheduleService) {
        this.orderService = orderService;
        this.scheduleService = scheduleService;
    }

    @Override
    protected String performGet(HttpServletRequest request) {
        String id = request.getParameter("id");
        int orderId = Integer.parseInt(id);
        Order order = orderService.findEntityById(orderId);
        int sessionId = order.getSession().getId();
        request.setAttribute("sessionDto", scheduleService.getSessionDto(sessionId));
        request.setAttribute("orderDto", orderService.getOrderDto(order));
        LOGGER.info(String.format("Order %s submit", orderId));
        return "WEB-INF/ticket.jsp";
    }

    @Override
    protected String performPost(HttpServletRequest request) {
        return "WEB-INF/ticket.jsp";
    }
}
