package dao;

import exceptions.DBException;
import exceptions.DuplicateDBException;

import java.sql.Connection;
import java.util.List;

public interface GenericDao<K extends Number, T> {
//    protected Connection connection;



    abstract List<T> findAll();

    abstract T findEntityById(K id);


    abstract boolean delete(T entity);

   abstract boolean create(T entity) throws DBException;

   abstract boolean update(T entity);


}
