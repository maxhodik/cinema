package command;

import entities.Movie;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.MovieService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public class MovieCommand extends MultipleMethodCommand {
    private static final Logger LOGGER = LogManager.getLogger(MovieCommand.class);
    private MovieService movieService;

    public MovieCommand(MovieService movieService) {
        this.movieService = movieService;
    }

    @Override
    public String performGet(HttpServletRequest request) {
        List<Movie> movies;
        movies= movieService.findAll();
        String orderBy = request.getParameter("orderBy");
        if (orderBy != null) {
            movies = movieService.findAllSortedByName();
        }
        request.setAttribute("movie", movies);
        return "/WEB-INF/admin/movie.jsp";

    }

    @Override
    public String performPost(HttpServletRequest request) {
        return "/WEB-INF/admin/movie.jsp";
    }


}
