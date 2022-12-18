package service;

import dto.MovieDto;
import entities.Movie;
import exceptions.DBException;
import exceptions.UserAlreadyExistException;

import java.util.List;

public interface MovieService {
    List<Movie> findAll();
    Movie findEntityById(Integer id);
    Movie findEntityByLogin(String name);
    boolean delete(Movie entity);
   Movie create(String name) throws DBException, UserAlreadyExistException;
    boolean update(Movie entity);
    List<Movie> findAllSortedByName();



}
