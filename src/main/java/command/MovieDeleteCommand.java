package command;

import entities.Movie;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.MovieService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public class MovieDeleteCommand extends MultipleMethodCommand {
    private static final Logger LOGGER = LogManager.getLogger(MovieCommand.class);
    private MovieService movieService;

    public MovieDeleteCommand(MovieService movieService) {
        this.movieService = movieService;
    }

    @Override
    public String performGet(HttpServletRequest request) {
        List<Movie> movies = movieService.findAll();
        request.setAttribute("movie", movies);
        return "/WEB-INF/admin/movie.jsp";
    }

    @Override
    public String performPost(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Movie entityById = movieService.findEntityById(id);
        movieService.delete(entityById);
        return "redirect:admin/movie" ;
    }



}
