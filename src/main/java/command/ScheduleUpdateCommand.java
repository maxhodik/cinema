package command;

import dto.SessionDto;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.HallService;
import service.MovieService;
import service.ScheduleService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;

public class ScheduleUpdateCommand extends MultipleMethodCommand {
    private static final Logger LOGGER = LogManager.getLogger(MovieCommand.class);
    private ScheduleService scheduleService;
    private HallService hallService;
    private MovieService movieService;

    public ScheduleUpdateCommand(ScheduleService scheduleService, MovieService movieService, HallService hallService) {
        this.scheduleService = scheduleService;
        this.hallService = hallService;
        this.movieService = movieService;
    }

    @Override
    protected String performGet(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        SessionDto sessionDto = scheduleService.getSessionDto(id);
        request.setAttribute("sessionDto", sessionDto);
        return "/WEB-INF/admin/update-session.jsp";
    }

    @Override
    protected String performPost(HttpServletRequest request) {
        //todo need transaction with halls
        int id = Integer.parseInt(request.getParameter("id"));
        LocalDate date = LocalDate.parse(request.getParameter("date"));
        LocalTime time = LocalTime.parse(request.getParameter("time"));
        String movieName = request.getParameter("movieName");
        int numberOfSeats = Integer.parseInt(request.getParameter("seats"));
        SessionDto sessionDto = new SessionDto(id, movieName, date, time, numberOfSeats);
        scheduleService.update(sessionDto);
        return "redirect:schedule?admin=true";
    }
}
