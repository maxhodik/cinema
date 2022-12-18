package command;

import exceptions.DBException;
import exceptions.UserAlreadyExistException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.MovieService;

import javax.servlet.http.HttpServletRequest;

public class MovieAddCommand extends MultipleMethodCommand {
    private static final Logger LOGGER = LogManager.getLogger(MovieCommand.class);
    private MovieService movieService;

    public MovieAddCommand(MovieService movieService) {
        this.movieService = movieService;
    }

    @Override
    protected String performGet(HttpServletRequest request) {
        return "/WEB-INF/admin/add-movie.jsp";
    }

    @Override
    protected String performPost(HttpServletRequest request) {
        String name = request.getParameter("name");
        try {
            movieService.create(name);
        } catch (DBException | UserAlreadyExistException e) {
            throw new RuntimeException(e);
        }
        return "/WEB-INF/admin/add-movie.jsp";
    }
}
