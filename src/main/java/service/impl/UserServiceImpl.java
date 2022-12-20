package service.impl;

import dao.UserDao;
import entities.User;
import exceptions.DBException;
import exceptions.UserAlreadyExistException;
import exceptions.UserNotFoundException;
import service.UserService;

import static entities.Role.USER;

public class UserServiceImpl implements UserService {

    private UserDao userDao;
    private PasswordEncoderService passwordEncoderService;

    public UserServiceImpl(UserDao userDao, PasswordEncoderService passwordEncoderService) {
        this.userDao = userDao;
        this.passwordEncoderService = passwordEncoderService;
    }

    @Override
    public User create(String name, String password) throws UserAlreadyExistException {
        User userFromDb = userDao.findEntityByLogin(name);
        if (userFromDb != null) {
            throw new UserAlreadyExistException("User already exists");
        }
        try {
            // todo add coder for password
            User user = User.builder()
                    .login(name)
                    .password(passwordEncoderService.encode(password))
                    .role(USER)
                    .build();
            userDao.create(user);
            return user;
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User login(String name, String password) throws UserNotFoundException {
        User userFromDb = userDao.findEntityByLogin(name);
        if (userFromDb != null && userFromDb.getPassword().equals(passwordEncoderService.encode(password))) {
            return userFromDb;
        }
        throw new UserNotFoundException("User not found" + name);

    }

    @Override
    public User findEntityByLogin(String login) {
        return userDao.findEntityByLogin(login);
    }
}
