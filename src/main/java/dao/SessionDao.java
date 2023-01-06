package dao;

import entities.Session;
import exceptions.DAOException;

import java.util.List;

public interface SessionDao extends GenericDao<Integer, Session> {
    List<Session> findAllSortedByNumberOfAvailableSeats();

    List<Session> findAllSortedByMovieTitle();

    List<Session> findAllSortedByDate();

    List<Session> findAllFilterByAvailableViewing(String filterBy, String orderBy, String limits);

    List<Session> findAllOrderBy(String columnName);

    Session findEntityById(Integer id);
    int getNumberOfRecords (String filters) throws DAOException, DAOException;
}

