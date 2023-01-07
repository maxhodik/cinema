package service;

import dto.Filter;
import dto.MovieDto;
import entities.Movie;
import exceptions.DBException;
import exceptions.UserAlreadyExistException;

import java.util.List;

public interface MovieService {
    List<Movie> findAll();
    Movie findEntityById(Integer id);
    Movie findEntityByName(String name);
    boolean delete(Movie entity);
   Movie create(String name) throws DBException, UserAlreadyExistException;
    boolean update(Movie entity);
    List<MovieDto> findAllSortedBy(String orderBy, String limits);
    int getNumberOfRecords();



}
