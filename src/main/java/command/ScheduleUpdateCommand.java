package command;

import dto.SessionDto;
import entities.Movie;
import entities.Status;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import service.HallService;
import service.MovieService;
import service.ScheduleService;
import web.form.IdForm;
import web.form.SessionForm;
import web.form.validation.IdValidator;
import web.form.validation.SessionFormValidator;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class ScheduleUpdateCommand extends MultipleMethodCommand {
    private static final Logger LOGGER = LogManager.getLogger(ScheduleUpdateCommand.class);
    private ScheduleService scheduleService;
    private MovieService movieService;
    private HallService hallService;
    private SessionFormValidator sessionValidator;
    private IdValidator idValidator;

    public ScheduleUpdateCommand(ScheduleService scheduleService, MovieService movieService, HallService hallService,
                                 SessionFormValidator sessionValidator, IdValidator idValidator) {
        this.scheduleService = scheduleService;
        this.movieService = movieService;
        this.hallService = hallService;
        this.sessionValidator = sessionValidator;
        this.idValidator=idValidator;
    }

    @Override
    protected String performGet(HttpServletRequest request) {
        int id = getId(request);
        Status status = scheduleService.findEntityById(id).getStatus();
        if (status.equals(Status.CANCELED)) {
            request.setAttribute("cantUpdate", true);
            LOGGER.info(String.format("Cannot update session %s cause status is canceled", id));
            return "/WEB-INF/admin/unsuccess-update-session.jsp";
        }
        String x = IsSoldTickets(request, id);
        if (x != null) return x;
        SessionDto sessionDto = scheduleService.getSessionDto(id);
        request.setAttribute("sessionDto", sessionDto);
        List<String> movieDtoList = getMovieDtoList();
        request.setAttribute("movieDto", movieDtoList);
        return "/WEB-INF/admin/update-session.jsp";
    }

    private int getId(HttpServletRequest request) {
        String sessionId = request.getParameter("id");
        IdForm idForm  = new IdForm(sessionId);
        if (idValidator.validate(idForm)){
            LOGGER.error("Illegal movie id");
            throw new IllegalArgumentException();
        }
        int id= Integer.parseInt(sessionId);
        return id;
    }

    @Override
    protected String performPost(HttpServletRequest request) {
        String sessionId = request.getParameter("id");
        String sessionDate = request.getParameter("date");
        String sessionTime = request.getParameter("time");
        String movieName = request.getParameter("movieName");
        String capacity = request.getParameter("seats");
        SessionForm sessionForm = new SessionForm(sessionId, sessionDate, sessionTime, movieName, capacity);
        if (sessionValidator.validate(sessionForm)) {
            request.getSession().setAttribute("errors", true);
            return "redirect:admin/update-session?id=" + sessionId;
        }
        int id = Integer.parseInt(sessionId);
        LocalDate date = LocalDate.parse(sessionDate);
        LocalTime time = LocalTime.parse(sessionTime);
        if (movieService.findEntityByName(movieName) == null) {
            request.setAttribute("movieDoesntExist", true);
            LOGGER.info("Can't update this session this movie name doesn't exist");
            return "redirect:admin/update-session?id=" + sessionId;
        }
        String x = IsSoldTickets(request, id);
        if (x != null) return x;
        Status status = scheduleService.findEntityById(id).getStatus();
        if (status.equals(Status.CANCELED)) {
            LOGGER.error("Can't update. This session canceled");
            throw new IllegalArgumentException();
        }
        int numberSeats = Integer.parseInt(capacity);
        SessionDto sessionDto = new SessionDto(id, movieName, date, time, status, numberSeats);
        scheduleService.update(sessionDto);
        LOGGER.info(String.format("Session %s updated", id));
        return "redirect:schedule";
    }

    @Nullable
    private String IsSoldTickets(HttpServletRequest request, int id) {
        int numberOfSoldSeats = scheduleService.findEntityById(id).getHall().getNumberOfSoldSeats();
        if (numberOfSoldSeats > 0) {
            request.setAttribute("cantEdit", true);
            LOGGER.info(String.format("You have tickets sold on this session %s", id));
            return "/WEB-INF/admin/unsuccess-update-session.jsp";
        }
        return null;
    }

    private List<String> getMovieDtoList() {
        List<Movie> movies = movieService.findAll();
        List<String> movieDtoList = movies.stream().map(x -> x.getName())
                .distinct()
                .collect(Collectors.toList());
        return movieDtoList;
    }
}
