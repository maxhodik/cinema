package dao;

import entities.Role;
import entities.User;
import exceptions.DBException;

import java.util.List;

public interface UserDao extends GenericDao<Integer, User> {

    List<User> findAllByRole(Role role) ;
    public User findEntityByLogin (String login);

}
