package command;

import dto.SessionDto;
import entities.Movie;
import entities.Status;
import exceptions.DBException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.HallService;
import service.MovieService;
import service.ScheduleService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static entities.Status.ACTIVE;

public class ScheduleAddCommand extends MultipleMethodCommand {

    private static final Logger LOGGER = LogManager.getLogger(MovieCommand.class);
    private ScheduleService scheduleService;
    private HallService hallService;
    private MovieService movieService;

    public ScheduleAddCommand(ScheduleService scheduleService, MovieService movieService, HallService hallService) {
        this.scheduleService = scheduleService;
        this.hallService = hallService;
        this.movieService = movieService;
    }
    @Override
    protected String performGet(HttpServletRequest request) {
        List<String> movieDtoList = getMovieDtoList();
        request.setAttribute("movieDto", movieDtoList);
            return "/WEB-INF/admin/add-session.jsp";
    }

    @Override
    protected String performPost(HttpServletRequest request) {
        LocalDate date = LocalDate.parse(request.getParameter("date"));
        LocalTime time = LocalTime.parse(request.getParameter("time"));
        String movieName = request.getParameter("movieName");
        int numberOfSeats = Integer.parseInt(request.getParameter("seats"));
        Status status=ACTIVE;
        int id=-1;
        SessionDto sessionDto = new SessionDto(id, movieName, date, time, status, numberOfSeats);
        try {
            scheduleService.create(sessionDto);
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
        return "/WEB-INF/admin/add-session.jsp";
    }
    private  List<String> getMovieDtoList() {
        List<Movie> movies= movieService.findAll();
        List<String> movieDtoList = movies.stream().map(x -> x.getName())
                .distinct()
                .collect(Collectors.toList());
        return movieDtoList;
    }
}
