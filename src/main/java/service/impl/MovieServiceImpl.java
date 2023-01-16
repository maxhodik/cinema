package service.impl;

import dao.MovieDao;
import dto.MovieDto;
import entities.Movie;
import exceptions.DBException;
import exceptions.EntityAlreadyExistException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.MovieService;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class MovieServiceImpl implements MovieService {
    private static final Logger LOGGER = LogManager.getLogger(MovieServiceImpl.class);
    private MovieDao movieDao;

    public MovieServiceImpl(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    @Override
    public List<Movie> findAll() {
        return movieDao.findAll();
    }

    @Override
    public Movie findEntityById(Integer id) {
        return movieDao.findEntityById(id);
    }

    @Override
    public Movie findEntityByName(String name) {
        return movieDao.findEntityByName(name);
    }

    @Override
    public boolean delete(Movie entity) {
        return movieDao.delete(entity);
    }

    @Override
    public Movie create(String name) throws DBException, EntityAlreadyExistException {
        Movie movieFromDb = movieDao.findEntityByName(name);
        if (movieFromDb != null) {
            LOGGER.info("Movie already exists name: " + name);
            throw new EntityAlreadyExistException("Movie already exists");
        }
        try {
            Movie movie = Movie.builder().name(name).build();
            movieDao.create(movie);
            return movie;
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public boolean update(Movie entity) throws DBException, EntityAlreadyExistException {
        return movieDao.update(entity);
    }

    @Override
    public List<MovieDto> findAllSortedByWithLimit(String orderBy, String limits) {
        List<Movie> movies;
        movies = movieDao.findAllSortedBy(orderBy, limits);
        List<MovieDto> movieDtoList = getMovieDtoList(movies);
        return movieDtoList;
    }
    @Override
    public List<MovieDto> findAllOrderBy(String orderBy) {
        List<Movie> movies;
        movies = movieDao.findAllOrderBy(orderBy);
        List<MovieDto> movieDtoList = getMovieDtoList(movies);
        return movieDtoList;
    }
    private List<MovieDto> getMovieDtoList(List<Movie> movies) {
        List<MovieDto> movieDtoList = new ArrayList<>();
        for (Movie m : movies) {
            int id = m.getId();
            String name = m.getName();
            MovieDto movieDto = new MovieDto(id, name);
            movieDtoList.add(movieDto);
        }
        return movieDtoList;
    }


    private String getSqlColumn(String columnName) {
        String sqlColumn = null;
        try {
            if (columnName != null) {
                //todo map (map passed columnName to table name otherwise throw error)
                ResourceBundle bundle = ResourceBundle.getBundle("mapping");
                sqlColumn = bundle.getString(columnName);
            }
        } catch (MissingResourceException e) {
            return null;
        }
        return sqlColumn;
    }
    public int getNumberOfRecords() {
        int numberOfRecords;
            numberOfRecords = movieDao.getNumberOfRecords();
        return numberOfRecords;
    }
}
