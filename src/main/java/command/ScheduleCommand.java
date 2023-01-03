package command;

import dto.Filter;
import dto.SessionAdminDto;
import entities.Role;
import entities.Session;
import entities.Status;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.ScheduleService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static command.Operation.IN;
import static command.Operation.IS;

public class ScheduleCommand extends MultipleMethodCommand {
    private static final Logger LOGGER = LogManager.getLogger(ScheduleCommand.class);

    private ScheduleService scheduleService;

    public ScheduleCommand(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @Override
    public String performGet(HttpServletRequest request) {
        if (request.getParameter("reset")!= null && request.getParameter("reset").equals("true")){
            request.getSession().setAttribute("filters" , null );
        }
        List<Filter> filters = (List<Filter>) request.getSession().getAttribute("filters");
        if (filters == null || filters.isEmpty()) {
            filters = new ArrayList<>();
        }
        String orderBy = request.getParameter("orderBy");

//        String admin = request.getParameter("admin");
        String[] select = request.getParameterValues("number_available_seats");
        String[] status = request.getParameterValues("status");
        if (request.getSession().getAttribute("role") != Role.ADMIN) {
            select = new String[] {"true"};
            status = new String[] {Status.ACTIVE.name()};
        }

        addFilterIfNeeded(status, filters, "status", IN);
        if (select != null && select.length != 0) {
            filters.add(new Filter("number_available_seats", List.of(select), IS));
        }
        request.getSession().setAttribute("filters", filters);
        List<SessionAdminDto> sessionDtoList = scheduleService.findAllFilterByAndOrderBy(filters, orderBy);
//        } else {
//            allSortedSessions = scheduleService.findAllOrderBy(orderBy);
//        }
//        List<SessionAdminDto> sessionDtoList = scheduleService.getSessionAdminDtoList(allSortedSessions);
        //     List<String> movieDtoList = getMovieDtoList(sessionDtoList);
        request.setAttribute("sessionAdminDto", sessionDtoList);
//        User user=User.builder().build();
        if (request.getSession().getAttribute("role") == Role.ADMIN) {
            return "WEB-INF/admin/schedule-admin.jsp";
        }
        return "WEB-INF/schedule.jsp";
    }

    @Override
    public String performPost(HttpServletRequest request) {
        return "admin/schedule-admin.jsp";
    }

    private void addFilterIfNeeded(String[] filterValues, List<Filter> filters, String columnName, Operation operation) {
        // 1 find filter by column in existed filters
        // 2 if exist
        //    replace on filterValues
        //    otherwise add filterValues

        if (filterValues != null) {

            // todo recheck if this for needed
            for (String d : filterValues) {
                if (d.isEmpty()) {

                    return;
                }
            }
            replaceFiltersIfPresent(filterValues, filters, columnName);
            filters.add(new Filter(columnName, List.of(filterValues), operation));
        }
    }

    private void replaceFiltersIfPresent(String[] filterValues, List<Filter> filters, String columnName) {
        Optional<Filter> filterByColumn = filters.stream().filter(filter -> filter.getColumn().equals(columnName)).findFirst();
        filterByColumn.ifPresent(filter -> filter.setValues(List.of(filterValues)));
    }
}
