package service.impl;

import dao.MovieDao;
import entities.Movie;
import exceptions.DBException;
import exceptions.UserAlreadyExistException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.MovieService;

import java.util.List;

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
    public Movie findEntityByLogin(String name) {
        return movieDao.findEntityByName(name);
    }

    @Override
    public boolean delete(Movie entity) {
        return movieDao.delete(entity);
    }

    @Override
    public Movie create(String name) throws DBException, UserAlreadyExistException {
        Movie movieFromDb = movieDao.findEntityByName(name);
        if (movieFromDb != null) {
            LOGGER.info("Movie already exists name: " + name);
            throw new UserAlreadyExistException("Movie already exists");
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
    public boolean update(Movie entity) {
        return movieDao.update(entity);
    }

    @Override
    public List<Movie> findAllSortedByName() {
        return movieDao.findAllSortedByName();
    }
}
