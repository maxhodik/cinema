package command;

import entities.Movie;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.MovieService;
import service.impl.MovieServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class MovieUpdateCommand extends MultipleMethodCommand {
    private static final Logger LOGGER = LogManager.getLogger(MovieCommand.class);
    private MovieService movieService;

    public MovieUpdateCommand(MovieService movieService) {this.movieService = movieService;
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
        Movie movie = movieService.findEntityById(id);
        movie.setName(name);
        movieService.update(movie);
        return "redirect:admin/movie" ;
    }

}
