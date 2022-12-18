package command;

import entities.Session;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.ScheduleService;

import javax.servlet.http.HttpServletRequest;

public class ScheduleDeleteCommand extends MultipleMethodCommand {

        private static final Logger LOGGER = LogManager.getLogger(MovieCommand.class);
       private ScheduleService scheduleService;

    public ScheduleDeleteCommand(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @Override
    protected String performGet(HttpServletRequest request) {
        return "WEB-INF/admin/schedule-admin.jsp";
    }

    @Override
    protected String performPost(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Session entityById = scheduleService.findEntityById(id);
        scheduleService.delete(entityById);
        return "redirect:schedule?admin=true";
    }
}
