package service;

import entities.User;
import exceptions.DBException;
import exceptions.EntityAlreadyExistException;
import exceptions.UserNotFoundException;

public interface UserService {
    User create(String name, String password) throws EntityAlreadyExistException;
    User login (String name, String password) throws UserNotFoundException;
    User findEntityByLogin (String login);
}
