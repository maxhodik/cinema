package command;

import dto.SessionDto;
import entities.Hall;
import entities.Movie;
import entities.Session;
import exceptions.DBException;
import exceptions.SaveHallException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.HallService;
import service.MovieService;
import service.ScheduleService;
import service.impl.ScheduleServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

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

            return "/WEB-INF/admin/add-session.jsp";
    }

    @Override
    protected String performPost(HttpServletRequest request) {
        LocalDate date = LocalDate.parse(request.getParameter("date"));
        LocalTime time = LocalTime.parse(request.getParameter("time"));
        String movieName = request.getParameter("movieName");
        int numberOfSeats = Integer.parseInt(request.getParameter("seats"));
        int id=-1;
        SessionDto sessionDto = new SessionDto(id, movieName, date, time, numberOfSeats);
        try {
            scheduleService.create(sessionDto);
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
        return "/WEB-INF/admin/add-session.jsp";
    }
}
