package dao.impl;

import dao.HallDao;
import dao.maper.HallMapper;
import dao.maper.ObjectMapper;
import dao.Constants;
import entities.Hall;
import exceptions.DBConnectionException;
import exceptions.DBException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import persistance.ConnectionPoolHolder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlHallDao implements HallDao {
    private static final Logger LOGGER = LogManager.getLogger(SqlHallDao.class);
    ObjectMapper<Hall> mapper;
    private final ConnectionPoolHolder connectionPoolHolder;

    public SqlHallDao(ConnectionPoolHolder connectionPoolHolder) {
        this.connectionPoolHolder = connectionPoolHolder;
    }


    @Override
    public List<Hall> findAll() {
        List<Hall> halls = new ArrayList<>();
        try (
                Connection con = connectionPoolHolder.getConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(Constants.FIND_ALL_HALLS);) {
            while (rs.next()) {
                mapper= new HallMapper();
                halls.add(mapper.extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new DBConnectionException(e);
        }
        return halls;
    }

    @Override
    public Hall findEntityById(Integer id) {

        try (Connection con = connectionPoolHolder.getConnection();
             PreparedStatement stmt = con.prepareStatement(Constants.FIND_HALL_BY_ID);) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            mapper= new HallMapper();
            return mapper.extractFromResultSet(rs);

        } catch (SQLException e) {
            throw new DBConnectionException(e);
        }
    }



    @Override
    public boolean delete(Hall entity) {
        try (Connection con = connectionPoolHolder.getConnection();
             PreparedStatement stmt = con.prepareStatement(Constants.DELETE_HALL_BY_ID);) {
            stmt.setInt(1, entity.getId());
            return stmt.executeUpdate() != 0;

        } catch (SQLException e) {
            throw new DBConnectionException(e);
        }
    }

    @Override
    public boolean create(Hall entity) throws DBException {
        try (Connection con = connectionPoolHolder.getConnection();
             PreparedStatement stmt = con.prepareStatement(Constants.INSERT_INTO_HALLS);) {
            stmt.setInt(1, entity.getNumberSeats());
            stmt.setInt(2, entity.getNumberAvailableSeats());
            return stmt.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new DBException("Hall already exists:" + entity.getId(), e);
        }

    }
    @Override
    public int createId (Hall entity) throws DBException {
        try (Connection con = connectionPoolHolder.getConnection();
             PreparedStatement stmt = con.prepareStatement(Constants.INSERT_INTO_HALLS, Statement.RETURN_GENERATED_KEYS);) {
            stmt.setInt(1, entity.getNumberSeats());
            stmt.setInt(2, entity.getNumberAvailableSeats());
            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
                else {
                    throw new SQLException("Creating  hall failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new DBException("Hall already exists:" + entity.getId(), e);
        }

    }

    @Override
    public boolean update(Hall entity) {
        try (Connection con = connectionPoolHolder.getConnection();
             PreparedStatement stmt = con.prepareStatement(Constants.UPDATE_HALL);) {
            stmt.setInt(1, entity.getNumberSeats());
            stmt.setInt(2, entity.getNumberAvailableSeats());
            stmt.setInt(3, entity.getId());
            return stmt.executeUpdate() != 0;

        } catch (SQLException e) {
            LOGGER.info("DBException");
            throw new DBConnectionException(e);
        }

    }

}

