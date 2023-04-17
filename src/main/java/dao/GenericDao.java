package dao;


import exceptions.EntityAlreadyExistException;


import java.sql.Connection;
import java.util.List;

public interface GenericDao<K extends Number, T> {

    List<T> findAll();

    T findEntityById(K id);

    boolean delete(T entity);

   boolean create(T entity) throws  EntityAlreadyExistException;

   boolean update(T entity) throws  EntityAlreadyExistException;



}
