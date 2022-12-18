package service.impl;

import dao.MovieDao;
import dto.MovieDto;
import entities.Movie;
import exceptions.DBException;

import exceptions.UserAlreadyExistException;
import service.MovieService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MovieServiceImpl implements MovieService {
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
