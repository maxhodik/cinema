package dao;

import entities.Movie;
import exceptions.DBException;
import exceptions.EntityAlreadyExistException;

import java.util.List;

public interface MovieDao extends GenericDao<Integer, Movie> {

   Movie findEntityByName(String name);
    List<Movie> findAllOrderBy (String orderBy);
    List<Movie> findAllSortedBy(String orderBy, String limits);
    int getNumberOfRecords();

   boolean create(Movie entity) throws DBException;

   boolean update(Movie entity) throws DBException;
}
