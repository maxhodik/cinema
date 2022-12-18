package dao;

import exceptions.DBException;
import exceptions.DuplicateDBException;

import java.util.List;

public interface GenericDao<K extends Number, T> {



    List<T> findAll();

    T findEntityById(K id);


    boolean delete(T entity);

    boolean create(T entity) throws DBException;

    boolean update(T entity);

}
