package dao;

import entities.Session;
import exceptions.DAOException;
import exceptions.DBException;

import java.util.List;

public interface SessionDao extends GenericDao<Integer, Session> {

    List<Session> findAllFilterByAvailableViewing(String filterBy, String orderBy, String limits);

    List<Session> findAllFilterByAvailableViewing(String sqlFilters);

    List<Session> findAllOrderBy(String columnName);

    Session findEntityById(Integer id);

    int getNumberOfRecords(String filters);

    boolean findByMovie(String name);
}

