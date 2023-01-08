package dao;

import entities.Movie;

import java.util.List;

public interface MovieDao extends GenericDao<Integer, Movie> {

   Movie findEntityByName(String name);
    List<Movie> findAllOrderBy (String orderBy);
    List<Movie> findAllSortedBy(String orderBy, String limits);
    int getNumberOfRecords();


}
