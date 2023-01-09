package dao.impl;

import dao.Constants;
import dao.OrderDao;
import dao.maper.ObjectMapper;
import dao.maper.OrderMapper;
import dao.maper.SessionMapper;
import dao.maper.UserMapper;
import entities.Order;
import entities.Session;
import entities.User;
import exceptions.DBConnectionException;
import exceptions.SaveOrderException;
import persistance.ConnectionPoolHolder;
import persistance.ConnectionWrapper;
import persistance.TransactionManagerWrapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlOrderDao implements OrderDao {
    private ObjectMapper<Order> mapper;
    private ObjectMapper<Session> sessionMapper;
    private ObjectMapper<User> userMapper;


    public SqlOrderDao() {
        this.userMapper = new UserMapper();
        this.sessionMapper = new SessionMapper();
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
//                order.setSession(sessionMapper.extractFromResultSet(rs));
//                order.setUser(userMapper.extractFromResultSet(rs));
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new DBConnectionException(e);
        }
        return orders;
    }


    @Override
    public Order findEntityById(Integer id) {

        try ( ConnectionWrapper con = TransactionManagerWrapper.getConnection();
             PreparedStatement stmt = con.prepareStatement(Constants.FIND_ORDER_BY_ID);) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            mapper = new OrderMapper();
            Order order = mapper.extractFromResultSet(rs);
            order.setSession(sessionMapper.extractFromResultSet(rs));
            order.setUser(userMapper.extractFromResultSet(rs));
            return order;
        } catch (SQLException e) {
            throw new DBConnectionException(e);
        }
    }

    @Override
    public List<Order> findAllBySessionId(Integer id) {
        List<Order> orders = new ArrayList<>();
        try ( ConnectionWrapper con = TransactionManagerWrapper.getConnection();
             PreparedStatement stmt = con.prepareStatement(Constants.FIND_ORDER_BY_SESSION_ID);) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                mapper = new OrderMapper();
                Order order = mapper.extractFromResultSet(rs);
                order.setSession(sessionMapper.extractFromResultSet(rs));
                order.setUser(userMapper.extractFromResultSet(rs));
                orders.add(order);
            }
            return orders;
        } catch (SQLException e) {
            throw new DBConnectionException(e);
        }
    }

    @Override
    public boolean delete(Order entity) {
        try ( ConnectionWrapper con = TransactionManagerWrapper.getConnection();
             PreparedStatement stmt = con.prepareStatement(Constants.DELETE_ORDER_BY_ID);) {
            stmt.setInt(1, entity.getId());
            return stmt.executeUpdate() != 0;

        } catch (SQLException e) {
            throw new DBConnectionException(e);
        }
    }

    @Override
    public boolean create(Order entity) throws SaveOrderException {

        try ( ConnectionWrapper con = TransactionManagerWrapper.getConnection();) {
            try (PreparedStatement stmt = con.prepareStatement(Constants.INSERT_INTO_ORDER);) {
                con.setAutoCommit(false);
                stmt.setString(1, String.valueOf(entity.getState()));
                stmt.setInt(2, entity.getNumberOfSeats());
                stmt.setInt(3, entity.getPrice());
                stmt.setInt(4, entity.getUser().getId());
                stmt.setInt(5, entity.getSession().getId());

                if (stmt.executeUpdate() != 0) {
                    con.commit();
                }
            } catch (SQLException e) {
                con.rollback();
                throw new SaveOrderException("Transaction rollback. Cannot save order to database cause");
            }
        } catch (SQLException e) {
            throw new DBConnectionException(e);
        }
        return true;
    }

    @Override
    public boolean update(Order entity) {
        try ( ConnectionWrapper con = TransactionManagerWrapper.getConnection();
             PreparedStatement stmt = con.prepareStatement(Constants.UPDATE_ORDERS);) {
            stmt.setString(1, String.valueOf(entity.getState()));
            stmt.setInt(2, entity.getNumberOfSeats());
            stmt.setInt(3, entity.getPrice());
            stmt.setInt(4, entity.getUser().getId());
            stmt.setInt(5, entity.getSession().getId());
            stmt.setInt(6, entity.getId());
            return stmt.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new DBConnectionException(e);
        }
    }
}
