package command;

import entities.Session;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.ScheduleService;
import web.form.IdForm;
import web.form.validation.IdValidator;

import javax.servlet.http.HttpServletRequest;

public class ScheduleDeleteCommand extends MultipleMethodCommand {

    private static final Logger LOGGER = LogManager.getLogger(MovieCommand.class);
    private ScheduleService scheduleService;
    private IdValidator idValidator;

    public ScheduleDeleteCommand(ScheduleService scheduleService, IdValidator idValidator) {
        this.scheduleService = scheduleService;
        this.idValidator = idValidator;
    }

    @Override
    protected String performGet(HttpServletRequest request) {
        return "WEB-INF/admin/schedule-admin.jsp";
    }

    @Override
    protected String performPost(HttpServletRequest request) {
        getId(request);
        int id = Integer.parseInt(request.getParameter("id"));
        Session session = scheduleService.findEntityById(id);
        scheduleService.canceledSession(session);
        return "redirect:schedule";
    }

    private void getId(HttpServletRequest request) {
        String sessionId = request.getParameter("id");
        IdForm idForm = new IdForm(sessionId);
        if (idValidator.validate(idForm)) {
            LOGGER.error("Illegal movie id");
            throw new IllegalArgumentException();
        }
    }


}
