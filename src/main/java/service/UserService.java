package service;

import entities.User;
import exceptions.UserAlreadyExistException;
import exceptions.UserNotFoundException;

public interface UserService {
    User create(String name, String password) throws UserAlreadyExistException;
    User login (String name, String password) throws UserNotFoundException;
    User findEntityByLogin (String login);
}
