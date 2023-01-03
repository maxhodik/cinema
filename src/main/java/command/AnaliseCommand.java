package command;

import dto.Filter;
import dto.SessionAdminDto;
import entities.Session;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.HallService;
import service.MovieService;
import service.ScheduleService;
import web.form.validation.AnaliseFormValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static command.Operation.*;

public class AnaliseCommand extends MultipleMethodCommand {
    private static final Logger LOGGER = LogManager.getLogger(ScheduleCommand.class);

    private ScheduleService scheduleService;
    private MovieService movieService;
    private HallService hallService;
    private AnaliseFormValidator analiseValidator;

    public AnaliseCommand(ScheduleService scheduleService, MovieService movieService,
                          HallService hallService, AnaliseFormValidator analiseValidator) {
        this.scheduleService = scheduleService;
        this.hallService = hallService;
        this.movieService = movieService;
        this.analiseValidator = analiseValidator;
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

        if (request.getParameter("reset")!= null && request.getParameter("reset").equals("true")){
            request.getSession().setAttribute("filters" , null );
        }
        List<Filter> filters = (List<Filter>) request.getSession().getAttribute("filters");
        if (filters == null || filters.isEmpty()) {
            filters = new ArrayList<>();
        }
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
//        AnaliseForm analiseForm = new AnaliseForm(LocalDate.parse(dates[0]), LocalDate.parse(dates[1]),
//                LocalTime.parse(times[0]), LocalTime.parse(times[1]));
//        if (analiseValidator.validate(analiseForm)) {
//            request.setAttribute("errors", true);
//            return "/WEB-INF/admin/analise.jsp";
//        }
        request.getSession().setAttribute("filters", filters);
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
