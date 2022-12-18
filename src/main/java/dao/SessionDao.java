package dao;

import entities.Session;

import java.util.List;

public interface SessionDao extends GenericDao<Integer, Session> {
    List<Session> findAllSortedByNumberOfAvailableSeats();

    List<Session> findAllSortedByMovieTitle();

    List<Session> findAllSortedByDate();

    List<Session> findAllFilterByAvailableViewing(String filterBy, String orderBy);

    List<Session> findAllOrderBy(String columnName);

    Session findEntityById(Integer id);
}

