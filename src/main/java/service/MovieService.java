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
    boolean delete(Movie entity);
   Movie create(String name) throws DBException, EntityAlreadyExistException;
    boolean update(Movie entity)throws DBException, EntityAlreadyExistException;
    List<MovieDto> findAllSortedByWithLimit(String orderBy, String limits);
    int getNumberOfRecords();
    List<MovieDto> findAllOrderBy(String orderBy);



}
