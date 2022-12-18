package dao.impl;

import dao.*;
import persistance.ConnectionPoolHolder;

public class SqlDaoFactory {
    private static final ConnectionPoolHolder connectionPoolHolder = ConnectionPoolHolder.pool();

    public static UserDao createUserDao() {
        return new SqlUserDao(connectionPoolHolder);
    }

    public static SessionDao createSessionDao() {
        return new SqlSessionDao(connectionPoolHolder);
    }

    public static OrderDao createOrderDao() {
        return new SqlOrderDao(connectionPoolHolder);
    }

    public static MovieDao createMovieDao() {
        return new SqlMovieDao(connectionPoolHolder);
    }

    public static HallDao createHallDao() {
        return new SqlHallDao(connectionPoolHolder);
    }
}
