package command;

import dto.Filter;
import dto.SessionAdminDto;
import entities.Session;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.ScheduleService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static command.Operation.IS;

public class ScheduleCommand extends MultipleMethodCommand {
    private static final Logger LOGGER = LogManager.getLogger(ScheduleCommand.class);

    private ScheduleService scheduleService;

    public ScheduleCommand(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @Override
    public String performGet(HttpServletRequest request) {
        List<Session> allSortedSessions;
        String orderBy = request.getParameter("orderBy");
        String admin = request.getParameter("admin");
        String[] select = request.getParameterValues("number_available_seats");
        List<Filter> filters = new ArrayList<>();
        if (select != null && select.length != 0) {
            filters.add(new Filter("number_available_seats", List.of(select), IS));
        }
        List<SessionAdminDto> sessionDtoList = scheduleService.findAllFilterByAndOrderBy(filters, orderBy);
//        } else {
//            allSortedSessions = scheduleService.findAllOrderBy(orderBy);
//        }
//        List<SessionAdminDto> sessionDtoList = scheduleService.getSessionAdminDtoList(allSortedSessions);
   //     List<String> movieDtoList = getMovieDtoList(sessionDtoList);
        request.setAttribute("sessionAdminDto", sessionDtoList);
//        User user=User.builder().build();
        if(Boolean.parseBoolean(admin)){
        return"WEB-INF/admin/schedule-admin.jsp";
        }
        return"WEB-INF/schedule.jsp";
        }

@Override
public String performPost(HttpServletRequest request){
        return"admin/schedule-admin.jsp";
        }

        }
