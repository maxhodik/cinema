package dao;

import entities.Order;
import exceptions.DBException;
import exceptions.DuplicateDBException;
import exceptions.EntityAlreadyExistException;


import java.sql.Connection;
import java.util.List;

public interface GenericDao<K extends Number, T> {
//    protected Connection connection;



    abstract List<T> findAll();

    abstract T findEntityById(K id);


    abstract boolean delete(T entity);

   abstract boolean create(T entity) throws  EntityAlreadyExistException;

   abstract boolean update(T entity) throws  EntityAlreadyExistException;



}
