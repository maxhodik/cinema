package service;

import dto.MovieDto;
import entities.Movie;
import exceptions.DBException;
import exceptions.EntityAlreadyExistException;

import java.util.List;

public interface MovieService {
    List<Movie> findAll();

    Movie findEntityById(Integer id);

    Movie findEntityByName(String name);

    boolean delete(Movie entity) throws EntityAlreadyExistException;

    Movie create(String name) throws EntityAlreadyExistException;

    boolean update(Movie entity) throws EntityAlreadyExistException;

    List<MovieDto> findAllSortedByWithLimit(String orderBy, String limits);

    int getNumberOfRecords();

    List<MovieDto> findAllOrderBy(String orderBy);


}
