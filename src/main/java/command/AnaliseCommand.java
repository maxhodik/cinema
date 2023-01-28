package command;

import dto.Filter;
import dto.SessionAdminDto;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.HallService;
import service.MovieService;
import service.ScheduleService;
import web.form.AnaliseForm;
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
    private Pagination pagination;

    public AnaliseCommand(ScheduleService scheduleService, MovieService movieService, HallService hallService, AnaliseFormValidator analiseValidator, Pagination pagination) {
        this.scheduleService = scheduleService;
        this.movieService = movieService;
        this.hallService = hallService;
        this.analiseValidator = analiseValidator;
        this.pagination = pagination;
    }

    @Override
    public String performGet(HttpServletRequest request) {

        String orderBy = request.getParameter("orderBy");
        String admin = request.getParameter("admin");
        String[] select = request.getParameterValues("number_available_seats");
        List<Filter> filters = new ArrayList<>();
        filters = (List<Filter>) request.getSession().getAttribute("filters");
        if (select != null && select.length != 0) {
            filters.add(new Filter("number_available_seats", List.of(select), IS));
        }
        int numberOfRecords = 0;
        numberOfRecords = scheduleService.getNumberOfRecords(filters);
        pagination.paginate(numberOfRecords, request);
        String limits = setLimits(request);
        List<SessionAdminDto> sessionDtoList = scheduleService.findAllFilterByAndOrderBy(filters, orderBy, limits);
        List<SessionAdminDto> sessionDtoListAll = scheduleService.findAllFilterBy(filters);
        List<String> movieDtoList = getMovieDtoList(sessionDtoListAll);
        request.setAttribute("sessionAdminDto", sessionDtoList);
        request.setAttribute("movieDto", movieDtoList);
        return "/WEB-INF/admin/analise.jsp";
    }

    @Override
    public String performPost(HttpServletRequest request) {

        if (request.getParameter("reset") != null && request.getParameter("reset").equals("true")) {
            request.getSession().setAttribute("filters", null);
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
        AnaliseForm analiseForm = new AnaliseForm(dates, times, movies, statuses, days);
        if (analiseValidator.validate(analiseForm)) {
            request.setAttribute("errors", true);
            return "/WEB-INF/admin/analise.jsp";
        }
        request.getSession().setAttribute("filters", filters);
        int numberOfRecords = 0;
        numberOfRecords = scheduleService.getNumberOfRecords(filters);
        pagination.paginate(numberOfRecords, request);
        String limits = setLimits(request);
        List<SessionAdminDto> sessionDtoList = scheduleService.findAllFilterByAndOrderBy(filters, orderBy, limits);
        List<SessionAdminDto> sessionDtoListAll = scheduleService.findAllFilterBy(filters);
        List<String> movieDtoList = getMovieDtoList(sessionDtoListAll);
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

    private String setLimits(HttpServletRequest request) {
        String offset = request.getParameter("offset");
        String records = request.getParameter("records");
        if (offset != null && records != null) {
            return " LIMIT " + records + " OFFSET " + offset;
        } else return " LIMIT 5 OFFSET 0";
    }

}
