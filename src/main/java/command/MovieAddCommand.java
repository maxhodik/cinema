package command;

import exceptions.DBException;
import exceptions.EntityAlreadyExistException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.MovieService;
import web.form.MovieForm;
import web.form.validation.MovieFormValidator;

import javax.servlet.http.HttpServletRequest;

public class MovieAddCommand extends MultipleMethodCommand {
    private static final Logger LOGGER = LogManager.getLogger(MovieCommand.class);
    private MovieService movieService;
    private MovieFormValidator movieValidator;

    public MovieAddCommand(MovieService movieService, MovieFormValidator movieValidator) {
        this.movieService = movieService;
        this.movieValidator = movieValidator;
    }

    @Override
    protected String performGet(HttpServletRequest request) {
        return "/WEB-INF/admin/add-movie.jsp";
    }

    @Override
    protected String performPost(HttpServletRequest request) {
        String name = request.getParameter("name");
        MovieForm movieForm = new MovieForm(name);
        if (movieValidator.validate(movieForm)) {
            request.setAttribute("errors", true);
            return"/WEB-INF/admin/add-movie.jsp";
        }
        try {
            movieService.create(name);
        } catch (DBException | EntityAlreadyExistException e) {
            throw new RuntimeException(e);
        }
        return "redirect:admin/movie" ;
    }
}
