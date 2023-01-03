package command;

import dto.SessionDto;
import entities.Movie;
import entities.Status;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.HallService;
import service.MovieService;
import service.ScheduleService;
import web.form.SessionForm;
import web.form.validation.SessionFormValidator;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class ScheduleUpdateCommand extends MultipleMethodCommand {
    private static final Logger LOGGER = LogManager.getLogger(MovieCommand.class);
    private ScheduleService scheduleService;
    private MovieService movieService;
    private HallService hallService;
    private SessionFormValidator sessionValidator;

    public ScheduleUpdateCommand(ScheduleService scheduleService, MovieService movieService, HallService hallService, SessionFormValidator sessionValidator) {
        this.scheduleService = scheduleService;
        this.movieService = movieService;
        this.hallService = hallService;
        this.sessionValidator = sessionValidator;
    }

    @Override
    protected String performGet(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        SessionDto sessionDto = scheduleService.getSessionDto(id);
        request.setAttribute("sessionDto", sessionDto);
        List<String> movieDtoList = getMovieDtoList();
        request.setAttribute("movieDto", movieDtoList);
        return "/WEB-INF/admin/update-session.jsp";
    }

    @Override
    protected String performPost(HttpServletRequest request) {
        //todo need transaction with halls
        int id = Integer.parseInt(request.getParameter("id"));
        LocalDate date = LocalDate.parse(request.getParameter("date"));
        LocalTime time = LocalTime.parse(request.getParameter("time"));
        String movieName = request.getParameter("movieName");
        Status status = scheduleService.findEntityById(id).getStatus();
        if (status.equals(Status.CANCELED)){
            LOGGER.info("Can't update this session");
            throw new IllegalArgumentException();
        }
        int numberOfSeats = Integer.parseInt(request.getParameter("seats"));
        SessionForm sessionForm = new SessionForm(movieName, numberOfSeats, date, time);
        if (sessionValidator.validate(sessionForm)) {
            request.setAttribute("errors", true);
            return "redirect:admin/update-session";
        }
        SessionDto sessionDto = new SessionDto(id, movieName, date, time, status, numberOfSeats);
        scheduleService.update(sessionDto);
        return "redirect:schedule?admin=true";
    }

    private List<String> getMovieDtoList() {
        List<Movie> movies = movieService.findAll();
        List<String> movieDtoList = movies.stream().map(x -> x.getName())
                .distinct()
                .collect(Collectors.toList());
        return movieDtoList;
    }
}
