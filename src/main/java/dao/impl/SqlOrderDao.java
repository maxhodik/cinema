package dao.impl;

import dao.Constants;
import dao.OrderDao;
import dao.maper.*;
import entities.*;
import exceptions.DBConnectionException;
import exceptions.DBException;
import exceptions.SaveOrderException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import persistance.ConnectionPoolHolder;
import persistance.ConnectionWrapper;
import persistance.TransactionManagerWrapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlOrderDao implements OrderDao {
    private static final Logger LOGGER = LogManager.getLogger(SqlOrderDao.class);
    private ObjectMapper<Order> mapper;
    private ObjectMapper<Session> sessionMapper;
    private ObjectMapper<User> userMapper;
    private ObjectMapper<Hall> hallMapper;
    private ObjectMapper<Movie> movieMapper;


    public SqlOrderDao() {
        this.mapper = new OrderMapper();
        this.sessionMapper = new SessionMapper();
        this.userMapper = new UserMapper();
        this.hallMapper = new HallMapper();
        this.movieMapper = new MovieMapper();
    }


    @Override
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        try (
                ConnectionWrapper con = TransactionManagerWrapper.getConnection();
                Statement stmt = con.statement();
                ResultSet rs = stmt.executeQuery(Constants.FIND_ALL_ORDERS);) {
            while (rs.next()) {
                Order order;
                mapper = new OrderMapper();
                order = mapper.extractFromResultSet(rs);

                orders.add(order);
            }
        } catch (SQLException e) {
            LOGGER.error("Orders not found", e);
            throw new DBException(e);
        }
        return orders;
    }


    @Override
    public Order findEntityById(Integer id) {

        try (ConnectionWrapper con = TransactionManagerWrapper.getConnection();
             PreparedStatement stmt = con.prepareStatement(Constants.FIND_ORDER_BY_ID);) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            mapper = new OrderMapper();
            Order order = mapper.extractFromResultSet(rs);
            Session session;
            session = sessionMapper.extractFromResultSet(rs);
            session.setMovie(movieMapper.extractFromResultSet(rs));
            session.setHall(hallMapper.extractFromResultSet(rs));
            order.setSession(session);
            order.setUser(userMapper.extractFromResultSet(rs));
            return order;
        } catch (SQLException e) {
            LOGGER.error("Orders not found", e);
            throw new DBException(e);

        }
    }

    @Override
    public List<Order> findAllBySessionId(Integer id) {
        List<Order> orders = new ArrayList<>();
        try (ConnectionWrapper con = TransactionManagerWrapper.getConnection();
             PreparedStatement stmt = con.prepareStatement(Constants.FIND_ORDER_BY_SESSION_ID);) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                mapper = new OrderMapper();
                Order order = mapper.extractFromResultSet(rs);
                Session session;
                session = sessionMapper.extractFromResultSet(rs);
                session.setMovie(movieMapper.extractFromResultSet(rs));
                session.setHall(hallMapper.extractFromResultSet(rs));
                order.setSession(session);
                order.setUser(userMapper.extractFromResultSet(rs));
                orders.add(order);
            }
            return orders;
        } catch (SQLException e) {
            LOGGER.error("Orders not found", e);
            throw new DBException(e);

        }
    }

    @Override
    public boolean delete(Order entity) {
        try (ConnectionWrapper con = TransactionManagerWrapper.getConnection();
             PreparedStatement stmt = con.prepareStatement(Constants.DELETE_ORDER_BY_ID);) {
            stmt.setInt(1, entity.getId());
            return stmt.executeUpdate() != 0;
        } catch (SQLException e) {
            LOGGER.error("Failed delete", e);
            throw new DBException(e);
        }
    }

    @Override
    public boolean create(Order entity) {
        try (ConnectionWrapper con = TransactionManagerWrapper.getConnection();
             PreparedStatement stmt = con.prepareStatement(Constants.INSERT_INTO_ORDER);) {
            stmt.setString(1, String.valueOf(entity.getState()));
            stmt.setInt(2, entity.getNumberOfSeats());
            stmt.setInt(3, entity.getPrice());
            stmt.setInt(4, entity.getUser().getId());
            stmt.setInt(5, entity.getSession().getId());

            if (stmt.executeUpdate() == 0) {
                throw new SaveOrderException("Order not created");
            }
        } catch (SQLException e) {
            LOGGER.error("Cannot save order to database cause", e);
            throw new SaveOrderException(e);
        }
        return true;
    }

    @Override
    public Order createAndReturnWithId(Order entity) {
        try (ConnectionWrapper con = TransactionManagerWrapper.getConnection();
             PreparedStatement stmt = con.prepareStatement(Constants.INSERT_INTO_ORDER, Statement.RETURN_GENERATED_KEYS);) {
            stmt.setString(1, String.valueOf(entity.getState()));
            stmt.setInt(2, entity.getNumberOfSeats());
            stmt.setInt(3, entity.getPrice());
            stmt.setInt(4, entity.getUser().getId());
            stmt.setInt(5, entity.getSession().getId());
            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getInt(1));
                    return entity;
                } else {
                    throw new SaveOrderException("Key not generated for order");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Exception in DB", e);
            throw new DBException(e);
        }
    }

    @Override
    public boolean update(Order entity) {
        try (ConnectionWrapper con = TransactionManagerWrapper.getConnection();
             PreparedStatement stmt = con.prepareStatement(Constants.UPDATE_ORDERS);) {
            stmt.setString(1, String.valueOf(entity.getState()));
            stmt.setInt(2, entity.getNumberOfSeats());
            stmt.setInt(3, entity.getPrice());
            stmt.setInt(4, entity.getUser().getId());
            stmt.setInt(5, entity.getSession().getId());
            stmt.setInt(6, entity.getId());
            return stmt.executeUpdate() != 0;
        } catch (SQLException e) {
            LOGGER.error("Exception in db", e);
            throw new DBException(e);
        }
    }
}
