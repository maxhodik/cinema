package command;

import dto.Filter;
import dto.SessionAdminDto;
import entities.Session;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.HallService;
import service.MovieService;
import service.ScheduleService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static command.Operation.*;

public class AnaliseCommand extends MultipleMethodCommand {
    private static final Logger LOGGER = LogManager.getLogger(ScheduleCommand.class);

    private ScheduleService scheduleService;
    private MovieService movieService;
    private HallService hallService;

    public AnaliseCommand(ScheduleService scheduleService, MovieService movieService, HallService hallService) {
        this.scheduleService = scheduleService;
        this.hallService = hallService;
        this.movieService = movieService;
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
        List<String> movieDtoList = getMovieDtoList(sessionDtoList);
        request.setAttribute("sessionAdminDto", sessionDtoList);
        request.setAttribute("movieDto", movieDtoList);

//        User user = User.builder().build();
        if (Boolean.parseBoolean(admin)) {
            return "WEB-INF/admin/schedule-admin.jsp";
        }

        return "/WEB-INF/admin/analise.jsp";

    }


    @Override
    public String performPost(HttpServletRequest request) {
        List<Filter> filters = new ArrayList<>();
        String[] dates = request.getParameterValues("date");
        addFilterIfNeeded(dates, filters, "date", BETWEEN);
        String[] movies = request.getParameterValues("movie");
        addFilterIfNeeded(movies, filters, "movie", IN);
        String[] statuses = request.getParameterValues("status");
        addFilterIfNeeded(statuses, filters, "status", IN);
        String[] days = request.getParameterValues("day");
        addFilterIfNeeded(days, filters, "day", IN);
        String[] times = request.getParameterValues("time");
        addFilterIfNeeded(times, filters, "time", BETWEEN);
        String orderBy = request.getParameter("orderBy");
        //todo string validation
        List<SessionAdminDto> sessionDtoList = scheduleService.findAllFilterByAndOrderBy(filters, orderBy);
//            List<Session> allSortedSessions;
//        if (web.filters.size() != 0) {
//        } else {
//            allSortedSessions = scheduleService.findAllOrderBy(orderBy);
//        }
//        List<SessionAdminDto> sessionDtoList = scheduleService.getSessionAdminDtoList(allSortedSessions);
        List<String> movieDtoList = getMovieDtoList(sessionDtoList);
        request.setAttribute("sessionAdminDto", sessionDtoList);
        request.setAttribute("movieDto", movieDtoList);
        return "/WEB-INF/admin/analise.jsp";
    }

    private static List<String> getMovieDtoList(List<SessionAdminDto> sessionDtoList) {
        List<String> movieDtoList = sessionDtoList.stream().map(x -> x.getMovieName())
                .distinct()
                .collect(Collectors.toList());
        return movieDtoList;
    }

    private static void addFilterIfNeeded(String[] dates, List<Filter> filters, String date, Operation operation) {
        if (dates != null) {
            for (String d : dates) {
                if (d.isEmpty()) {
                    return;
                }
            }
            filters.add(new Filter(date, List.of(dates), operation));
        }
    }

}
