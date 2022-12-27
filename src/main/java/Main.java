import dao.*;
import dao.impl.*;
import entities.Movie;
import entities.Order;
import entities.Role;
import entities.User;
import exceptions.DuplicateDBException;
import persistance.ConnectionPoolHolder;

import static entities.Role.ADMIN;
import static entities.State.NEW;

public class Main {
    public static void main(String[] args) throws DuplicateDBException {
        ConnectionPoolHolder connectionPoolHolder = ConnectionPoolHolder.pool();
        UserDao userDao = new SqlUserDao(connectionPoolHolder);
        SessionDao sessionDao = new SqlSessionDao(connectionPoolHolder);
        User user = new User(User.builder());
        MovieDao movieDao = new SqlMovieDao(connectionPoolHolder);
        Movie movie = new Movie(Movie.builder());
        movie.setName("test");
//        movieDao.create(movie);
        System.out.println(movieDao.findAll());
        HallDao hallDao = new SqlHallDao(connectionPoolHolder);
        System.out.println(hallDao.findAll());
        user.setLogin("test2");
        user.setPassword("1223");
        user.setRole(ADMIN);
        System.out.println(userDao.findAll());
        System.out.println("*****************************************************************************************************************");

        userDao.update(user);


//      userDao.create(user);
        System.out.println(userDao.findAll());
        System.out.println("*****************************************************************************************************************");
        System.out.println(userDao.findAllByRole(Role.getByNameIgnoringCase("admin")));
        System.out.println(userDao.findEntityById(3));
        System.out.println(userDao.findEntityByLogin("Ivanov"));

        System.out.println(sessionDao.findEntityById(7));
        System.out.println(sessionDao.findAllSortedByDate());
//        System.out.println(sessionDao.findAllFilterByAvailableViewing());
        System.out.println(sessionDao.findAllSortedByMovieTitle());
        System.out.println(sessionDao.findAllSortedByNumberOfAvailableSeats());

        OrderDao orderDao = new SqlOrderDao(connectionPoolHolder);
        Order order = new Order(Order.builder());
        order.setPrice(200);
        order.setState(NEW);
        order.setNumberOfSeats(2);
        order.setUser(userDao.findEntityById(7));
        order.setSession(sessionDao.findEntityById(7));
   //     orderDao.create(order);
        System.out.println(orderDao.findAll());
    }

}
