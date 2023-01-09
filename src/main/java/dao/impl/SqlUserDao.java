package dao.impl;

import exceptions.DBException;
import dao.UserDao;
import dao.maper.ObjectMapper;
import dao.maper.UserMapper;
import dao.Constants;
import entities.Role;
import entities.User;
import exceptions.DBConnectionException;
import persistance.ConnectionPoolHolder;
import persistance.ConnectionWrapper;
import persistance.TransactionManagerWrapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlUserDao implements UserDao {
    ObjectMapper<User> mapper;


    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (
                ConnectionWrapper con = TransactionManagerWrapper.getConnection();
                Statement stmt = con.statement();
                ResultSet rs = stmt.executeQuery(Constants.FIND_ALL_USERS);) {
            while (rs.next()) {
                mapper = new UserMapper();
                users.add(mapper.extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new DBConnectionException(e);
        }
        return users;
    }




    public List<User> findAllByRole(Role role) {
        List<User> users = new ArrayList<>();
        try (
                ConnectionWrapper con = TransactionManagerWrapper.getConnection();
                PreparedStatement stmt = con.prepareStatement(Constants.FIND_ALL_USERS_BY_ROLE);) {
            stmt.setString(1, (role.name()));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
               mapper = new UserMapper();
                users.add(mapper.extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new DBConnectionException(e);
        }
        return users;
    }


    public User findEntityById (Integer id) {
//       User user = new User();
        try ( ConnectionWrapper con = TransactionManagerWrapper.getConnection();
             PreparedStatement stmt = con.prepareStatement(Constants.FIND_USERS_BY_ID);) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            mapper = new UserMapper();
            return mapper.extractFromResultSet(rs);

        } catch (SQLException e) {
            throw new DBConnectionException(e);
        }

    }

    public User findEntityByLogin (String login) {

        try ( ConnectionWrapper con = TransactionManagerWrapper.getConnection();
             PreparedStatement stmt = con.prepareStatement(Constants.FIND_USERS_BY_LOGIN);) {
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                mapper = new UserMapper();
                return mapper.extractFromResultSet(rs);
            }
        } catch (SQLException e) {
            throw new DBConnectionException(e);
        }
        return null;
    }


    public boolean delete(User entity) {
        try ( ConnectionWrapper con = TransactionManagerWrapper.getConnection();
             PreparedStatement stmt = con.prepareStatement(Constants.DELETE_USERS_BY_LOGIN);) {
            stmt.setString(1, entity.getLogin());
            return stmt.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new DBConnectionException(e);
        }
    }


    public boolean create(User entity) throws DBException {
        try ( ConnectionWrapper con = TransactionManagerWrapper.getConnection();
             PreparedStatement stmt = con.prepareStatement(Constants.INSERT_INTO_USERS)) {
            stmt.setString(1, entity.getLogin());
            stmt.setString(2, entity.getPassword());
            stmt.setString(3, entity.getRole().toString());
            return stmt.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("User already exists, login: " + entity.getLogin(), e);
        }
    }


    public boolean update(User entity) {
        try ( ConnectionWrapper con = TransactionManagerWrapper.getConnection();
             PreparedStatement stmt = con.prepareStatement(Constants.UPDATE_USER);) {
            stmt.setString(1, entity.getLogin());
            stmt.setString(2, entity.getPassword());
            stmt.setString(3, entity.getRole().toString());
            stmt.setInt(4, entity.getId());
            return stmt.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new DBConnectionException(e);
        }


    }
}
