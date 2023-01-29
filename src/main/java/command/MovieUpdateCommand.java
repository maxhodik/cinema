package command;

import entities.Movie;
import exceptions.DBException;
import exceptions.EntityAlreadyExistException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.MovieService;
import web.form.IdForm;
import web.form.MovieForm;
import web.form.validation.IdValidator;
import web.form.validation.MovieFormValidator;
import web.handler.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

public class MovieUpdateCommand extends MultipleMethodCommand {
    private static final Logger LOGGER = LogManager.getLogger(MovieUpdateCommand.class);
    private MovieService movieService;
    private MovieFormValidator movieValidator;
    private IdValidator idValidator;

    public MovieUpdateCommand(MovieService movieService, MovieFormValidator movieValidator, IdValidator idValidator) {
        this.movieService = movieService;
        this.movieValidator = movieValidator;
        this.idValidator=idValidator;
    }

    @Override
    public String performGet(HttpServletRequest request) {
            String movieId = request.getParameter("id");
            IdForm idForm  = new IdForm(movieId);
            if (idValidator.validate(idForm)){
            LOGGER.error("Illegal movie id");
            throw new IllegalArgumentException();
        }
            int id= Integer.parseInt(movieId);
        Movie movie = movieService.findEntityById(id);
        request.setAttribute("movie", movie);
        return "/WEB-INF/admin/update-movie.jsp";
    }

    @Override
    public String performPost(HttpServletRequest request) {
        String movieId = request.getParameter("id");
        IdForm idForm  = new IdForm(movieId);
        if (idValidator.validate(idForm)){
            LOGGER.error("Illegal movie id");
            throw new IllegalArgumentException();
        }
        int id= Integer.parseInt(movieId);
        String name = request.getParameter("name");
        MovieForm movieForm = new MovieForm(name);
        if (movieValidator.validate(movieForm)) {
            request.getSession().setAttribute("errors", true);
            LOGGER.info("Movie not valid");
            return "redirect:admin/movie/update-movie?id="+ id;

        }
        Movie movie = movieService.findEntityById(id);
        movie.setName(name);
        try {
            movieService.update(movie);
        } catch (EntityAlreadyExistException e) {
            ExceptionHandler handler = new ExceptionHandler(e, "admin/movie/update-movie?id="+ id, "redirect");
            LOGGER.info("Movie already exist with name=" + name);
            return handler.handling(request);
        }
        return "redirect:admin/movie";
    }

}
