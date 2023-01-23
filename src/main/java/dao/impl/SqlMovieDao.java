package dao.impl;

import dao.MovieDao;
import dao.maper.MovieMapper;
import dao.maper.ObjectMapper;
import dao.Constants;
import entities.Movie;
import exceptions.DAOException;
import exceptions.DBConnectionException;
import exceptions.DBException;
import exceptions.EntityAlreadyExistException;
import persistance.ConnectionPoolHolder;
import persistance.ConnectionWrapper;
import persistance.TransactionManagerWrapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlMovieDao implements MovieDao {
    ObjectMapper<Movie> mapper;

    @Override
    public List<Movie> findAllOrderBy(String orderBy) {
        List<Movie> movies = new ArrayList<>();
        try (
                ConnectionWrapper con = TransactionManagerWrapper.getConnection();
                PreparedStatement stmt = con.prepareStatement(Constants.FIND_ALL_MOVIES_SORTED_BY_NAME + " ORDER BY " + orderBy)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                mapper = new MovieMapper();
                movies.add(mapper.extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Movie not found", e);
        }
        return movies;
    }

    @Override
    public List<Movie> findAllSortedBy(String orderBy, String limits) {
        List<Movie> movies = new ArrayList<>();
        try (
                ConnectionWrapper con = TransactionManagerWrapper.getConnection();
                PreparedStatement stmt = con.prepareStatement(Constants.FIND_ALL_MOVIES_SORTED_BY_NAME + " ORDER BY " + orderBy + limits)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                mapper = new MovieMapper();
                movies.add(mapper.extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Movies not found", e);
        }
        return movies;
    }

    @Override
    public List<Movie> findAll() {
        List<Movie> movies = new ArrayList<>();
        try (
                ConnectionWrapper con = TransactionManagerWrapper.getConnection();
                PreparedStatement stmt = con.prepareStatement(Constants.FIND_ALL_MOVIES_SORTED_BY_NAME)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                mapper = new MovieMapper();
                movies.add(mapper.extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Movies not found", e);
        }
        return movies;
    }

    @Override
    public Movie findEntityById(Integer id) {

        try (ConnectionWrapper con = TransactionManagerWrapper.getConnection();
             PreparedStatement stmt = con.prepareStatement(Constants.FIND_MOVIE_BY_ID);) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            mapper = new MovieMapper();
            return mapper.extractFromResultSet(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Movies not found", e);
        }
    }


    public Movie findEntityByName(String name) {

        try (ConnectionWrapper con = TransactionManagerWrapper.getConnection();
             PreparedStatement stmt = con.prepareStatement(Constants.FIND_MOVIE_BY_NAME);) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                mapper = new MovieMapper();
                return mapper.extractFromResultSet(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Movie not found", e);
        }
        return null;
    }


    public boolean delete(Movie entity) {
        ConnectionWrapper con = null;
        try {
            con = TransactionManagerWrapper.getConnection();
            try (PreparedStatement stmt = con.prepareStatement(Constants.DELETE_MOVIE_BY_NAME);) {

                con.setAutoCommit(false);
                stmt.setString(1, entity.getName());
                if (stmt.executeUpdate() != 0) {
                    con.commit();
                }else {
                    con.rollback();
                    return false;
                }
            }
        } catch (SQLException e) {
            try {
                assert con != null;
                con.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException("Failed delete", ex);
            }
            throw new RuntimeException("Failed delete", e);
        }
        return true;
    }


    @Override
    public boolean create(Movie entity) throws EntityAlreadyExistException {
        try (ConnectionWrapper con = TransactionManagerWrapper.getConnection();
             PreparedStatement stmt = con.prepareStatement(Constants.INSERT_INTO_MOVIES);) {
            stmt.setString(1, entity.getName());
            return stmt.executeUpdate() != 0;
        } catch (SQLIntegrityConstraintViolationException ex) {
            throw new EntityAlreadyExistException("Movie already exists, name: " + entity.getName(), ex);
        } catch (SQLException e) {
            throw new RuntimeException("Exception in DB", e);
        }

    }

    @Override
    public boolean update(Movie entity) throws EntityAlreadyExistException {

        try (ConnectionWrapper con = TransactionManagerWrapper.getConnection();
             PreparedStatement stmt = con.prepareStatement(Constants.UPDATE_MOVIE);) {
            stmt.setString(1, entity.getName());
            stmt.setInt(2, entity.getId());
            return stmt.executeUpdate() != 0;

        } catch (SQLIntegrityConstraintViolationException ex) {
            throw new EntityAlreadyExistException("Movie already exists, name: " + entity.getName(), ex);
        } catch (SQLException e) {
            throw new RuntimeException("Exception in DB", e);
        }

    }

    @Override
    public int getNumberOfRecords() {
        int numberOfRecords = 0;
        try (ConnectionWrapper con = TransactionManagerWrapper.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(Constants.COUNT_ALL_MOVIES)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    numberOfRecords = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Exception in DB", e);
        }
        return numberOfRecords;
    }

}


