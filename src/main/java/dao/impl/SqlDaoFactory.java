package dao.impl;

import dao.*;
import persistance.ConnectionPoolHolder;

public class SqlDaoFactory {


    public static UserDao createUserDao() {
        return new SqlUserDao();
    }

    public static SessionDao createSessionDao() {
        return new SqlSessionDao();
    }

    public static OrderDao createOrderDao() {
        return new SqlOrderDao();
    }

    public static MovieDao createMovieDao() {
        return new SqlMovieDao();
    }

    public static HallDao createHallDao() {
        return new SqlHallDao();
    }
}
