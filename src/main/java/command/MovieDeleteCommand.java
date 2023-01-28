package command;

import dto.MovieDto;
import entities.Movie;
import exceptions.EntityAlreadyExistException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.MovieService;
import web.form.IdForm;
import web.form.validation.IdValidator;
import web.handler.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public class MovieDeleteCommand extends MultipleMethodCommand {
    private static final Logger LOGGER = LogManager.getLogger(MovieCommand.class);
    private MovieService movieService;
    private IdValidator idValidator;

    public MovieDeleteCommand(MovieService movieService, IdValidator idValidator) {

        this.movieService = movieService;
        this.idValidator = idValidator;
    }

    @Override
    public String performGet(HttpServletRequest request) {
        List<MovieDto> movies = movieService.findAllOrderBy("id");
        request.setAttribute("movie", movies);
        return "/WEB-INF/admin/movie.jsp";
    }

    @Override
    public String performPost(HttpServletRequest request) {
        String movieId = request.getParameter("id");
        IdForm idForm = new IdForm(movieId);
        if (idValidator.validate(idForm)) {
            LOGGER.error("Illegal movie id");
            throw new IllegalArgumentException();
        }
        int id = Integer.parseInt(movieId);
        Movie entityById = movieService.findEntityById(id);
        try {
            movieService.delete(entityById);
        } catch (EntityAlreadyExistException e) {
            ExceptionHandler handler = new ExceptionHandler(e, "admin/movie", "redirect");
            LOGGER.info("Movie already used");
            return handler.handling(request);
        }
        return "redirect:admin/movie";
    }


}
