package command;

import entities.Movie;
import exceptions.DBException;
import exceptions.EntityAlreadyExistException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.MovieService;
import web.form.MovieForm;
import web.form.validation.MovieFormValidator;
import web.handler.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

public class MovieUpdateCommand extends MultipleMethodCommand {
    private static final Logger LOGGER = LogManager.getLogger(MovieCommand.class);
    private MovieService movieService;
    private MovieFormValidator movieValidator;

    public MovieUpdateCommand(MovieService movieService, MovieFormValidator movieValidator) {this.movieService = movieService;
        this.movieValidator = movieValidator;
    }

    @Override
    public String performGet(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Movie movie = movieService.findEntityById(id);
        request.setAttribute("movie",movie);
        return "/WEB-INF/admin/update-movie.jsp";
    }

    @Override
    public String performPost(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        MovieForm movieForm= new MovieForm(name);
        if (movieValidator.validate(movieForm)) {
            request.setAttribute("errors", true);
            LOGGER.info("Movie not valid");
            return "/WEB-INF/admin/update-movie.jsp";
        }
        Movie movie = movieService.findEntityById(id);
        movie.setName(name);
        try {
            movieService.update(movie);
        } catch (EntityAlreadyExistException e) {
            ExceptionHandler handler = new ExceptionHandler(e, "/WEB-INF/admin/update-movie.jsp" );
            LOGGER.info("Movie already exist with name=" + name);
            return handler.handling(request);
//            request.getSession().setAttribute("exception", true);
        }
        return "redirect:admin/movie" ;
    }

}
