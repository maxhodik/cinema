package command;

import dto.MovieDto;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.MovieService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public class MovieCommand extends MultipleMethodCommand {
    private static final Logger LOGGER = LogManager.getLogger(MovieCommand.class);
    private MovieService movieService;
    private Pagination pagination;


    public MovieCommand(MovieService movieService, Pagination pagination) {
        this.movieService = movieService;
        this.pagination = pagination;
    }

    @Override
    public String performGet(HttpServletRequest request) {
        List<MovieDto> movies;
        String limits = setLimits(request);
        String orderBy = request.getParameter("orderBy");
        movies = movieService.findAllSortedByWithLimit(orderBy, limits);
        int numberOfRecords = 0;
        numberOfRecords = movieService.getNumberOfRecords();
        pagination.paginate(numberOfRecords, request);
        request.setAttribute("movieDto", movies);
        return "/WEB-INF/admin/movie.jsp";

    }

    @Override
    public String performPost(HttpServletRequest request) {
        return "/WEB-INF/admin/movie.jsp";
    }

    private String setLimits(HttpServletRequest request) {
        String offset = request.getParameter("offset");
        String records = request.getParameter("records");
        if (offset != null && records != null) {
            return " LIMIT " + records + " OFFSET " + offset;
        } else return " LIMIT 5 OFFSET 0";
    }
}
