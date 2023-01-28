package command;

import entities.Order;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.OrderService;
import service.ScheduleService;
import web.form.IdForm;
import web.form.validation.IdValidator;

import javax.servlet.http.HttpServletRequest;

public class TicketCommand extends MultipleMethodCommand {

    private static final Logger LOGGER = LogManager.getLogger(TicketCommand.class);
    private OrderService orderService;
    private ScheduleService scheduleService;
    private IdValidator idValidator;


    public TicketCommand(OrderService orderService, ScheduleService scheduleService, IdValidator idValidator) {
        this.orderService = orderService;
        this.scheduleService = scheduleService;
        this.idValidator=idValidator;
    }

    @Override
    protected String performGet(HttpServletRequest request) {
        String movieId = request.getParameter("id");
        IdForm idForm  = new IdForm(movieId);
        if (idValidator.validate(idForm)){
            LOGGER.error("Illegal movie id");
            throw new IllegalArgumentException();
        }
        int orderId= Integer.parseInt(movieId);
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
