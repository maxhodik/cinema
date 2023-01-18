package dao.impl;

import dao.HallDao;
import dao.maper.HallMapper;
import dao.maper.ObjectMapper;
import dao.Constants;
import entities.Hall;
import exceptions.DBConnectionException;
import exceptions.DBException;
import exceptions.EntityAlreadyExistException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import persistance.ConnectionWrapper;
import persistance.TransactionManagerWrapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlHallDao implements HallDao {
    private static final Logger LOGGER = LogManager.getLogger(SqlHallDao.class);
    private ObjectMapper<Hall> mapper;

    @Override
    public List<Hall> findAll() {
        List<Hall> halls = new ArrayList<>();
        try (
                ConnectionWrapper con = TransactionManagerWrapper.getConnection();
                Statement stmt = con.statement();
                ResultSet rs = stmt.executeQuery(Constants.FIND_ALL_HALLS);) {
            while (rs.next()) {
                mapper = new HallMapper();
                halls.add(mapper.extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Users not found", e);
        }
        return halls;
    }

    @Override
    public Hall findEntityById(Integer id) {

        try (
                ConnectionWrapper con = TransactionManagerWrapper.getConnection();
                PreparedStatement stmt = con.prepareStatement(Constants.FIND_HALL_BY_ID);) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            mapper = new HallMapper();
            return mapper.extractFromResultSet(rs);

        } catch (SQLException e) {
            throw new RuntimeException("Users not found", e);
        }
    }


    @Override
    public boolean delete(Hall entity) {
        try (
                ConnectionWrapper con = TransactionManagerWrapper.getConnection();
                PreparedStatement stmt = con.prepareStatement(Constants.DELETE_HALL_BY_ID);) {
            stmt.setInt(1, entity.getId());
            return stmt.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed delete", e);
        }
    }

    @Override
    public boolean create(Hall entity) {
        try (
                ConnectionWrapper con = TransactionManagerWrapper.getConnection();
                PreparedStatement stmt = con.prepareStatement(Constants.INSERT_INTO_HALLS);) {
            stmt.setInt(1, entity.getCapacity());
            stmt.setInt(2, entity.getNumberAvailableSeats());
            stmt.setInt(3, entity.getNumberOfSoldSeats());
            stmt.setBigDecimal(4, entity.getAttendance());
            return stmt.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException("Creating  hall failed", e);
        }

    }

    @Override
    public Hall createAndReturnWithId(Hall entity) {
        try (
                ConnectionWrapper con = TransactionManagerWrapper.getConnection();
                PreparedStatement stmt = con.prepareStatement(Constants.INSERT_INTO_HALLS, Statement.RETURN_GENERATED_KEYS);) {
            stmt.setInt(1, entity.getCapacity());
            stmt.setInt(2, entity.getNumberAvailableSeats());
            stmt.setInt(3, entity.getNumberOfSoldSeats());
            stmt.setBigDecimal(4, entity.getAttendance());

            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getInt(1));
                    return entity;
                } else {
                    throw new SQLException("Creating  hall failed, no ID obtained.");
                }
            }
    } catch (SQLException e) {
        throw new RuntimeException("Exception in DB", e);
    }

    }

    @Override
    public boolean update(Hall entity) {
        try (
                ConnectionWrapper con = TransactionManagerWrapper.getConnection();
                PreparedStatement stmt = con.prepareStatement(Constants.UPDATE_HALL);) {
            stmt.setInt(1, entity.getCapacity());
            stmt.setInt(2, entity.getNumberAvailableSeats());
            stmt.setInt(3, entity.getNumberOfSoldSeats());
            stmt.setBigDecimal(4, entity.getAttendance());

            stmt.setInt(5, entity.getId());
            return stmt.executeUpdate() != 0;

        }catch (SQLException e) {
            throw new RuntimeException("Exception in DB", e);
        }

    }



}

