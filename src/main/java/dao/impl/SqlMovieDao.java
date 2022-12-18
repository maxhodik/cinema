package dao.impl;

import dao.MovieDao;
import dao.maper.MovieMapper;
import dao.maper.ObjectMapper;
import dao.Constants;
import entities.Movie;
import exceptions.DBConnectionException;
import exceptions.DBException;
import persistance.ConnectionPoolHolder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlMovieDao implements MovieDao {
    ObjectMapper<Movie> mapper;
    private final ConnectionPoolHolder connectionPoolHolder;

    public SqlMovieDao(ConnectionPoolHolder connectionPoolHolder) {
        this.connectionPoolHolder = connectionPoolHolder;
    }

@Override
    public List<Movie> findAll() {
        List<Movie> movies = new ArrayList<>();
        try (
                Connection con = connectionPoolHolder.getConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(Constants.FIND_ALL_MOVIES);) {
            while (rs.next()) {
                mapper = new MovieMapper();
                movies.add(mapper.extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new DBConnectionException(e);
        }
        return movies;
    }
@Override
    public List<Movie> findAllSortedByName() {
        List<Movie> movies = new ArrayList<>();
        try (
                Connection con = connectionPoolHolder.getConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(Constants.FIND_ALL_MOVIES_SORTED_BY_NAME);) {
            while (rs.next()) {
                mapper = new MovieMapper();
                movies.add(mapper.extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new DBConnectionException(e);
        }
        return movies;
    }
    @Override
    public Movie findEntityById(Integer id) {

        try (Connection con = connectionPoolHolder.getConnection();
             PreparedStatement stmt = con.prepareStatement(Constants.FIND_MOVIE_BY_ID);) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            mapper = new MovieMapper();
            return mapper.extractFromResultSet(rs);

        } catch (SQLException e) {
            throw new DBConnectionException(e);
        }
    }


    public Movie findEntityByName(String name) {

        try (Connection con = connectionPoolHolder.getConnection();
             PreparedStatement stmt = con.prepareStatement(Constants.FIND_MOVIE_BY_NAME);) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                mapper = new MovieMapper();
                return mapper.extractFromResultSet(rs);
            }

        } catch (SQLException e) {
            throw new DBConnectionException(e);
        }
        return null;
    }


    public boolean delete(Movie entity) {
        Connection con = null;
        try {
            con = connectionPoolHolder.getConnection();
            try (PreparedStatement stmt = con.prepareStatement(Constants.DELETE_MOVIE_BY_NAME);) {

                con.setAutoCommit(false);
                stmt.setString(1, entity.getName());
                if (stmt.executeUpdate() != 0) {
                    con.commit();
                }
            }
        } catch (SQLException e) {
            try {
                assert con != null;
                con.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new DBConnectionException(e);
        }
        return true;
    }



@Override
    public boolean create (Movie entity) throws DBException {
        try (Connection con = connectionPoolHolder.getConnection();
             PreparedStatement stmt = con.prepareStatement(Constants.INSERT_INTO_MOVIES);) {
            stmt.setString(1, entity.getName());
            return stmt.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new DBException("Movie already exists:" + entity.getName(), e);
        }

    }


    public boolean update(Movie entity) {

        try (Connection con = connectionPoolHolder.getConnection();
             PreparedStatement stmt = con.prepareStatement(Constants.UPDATE_MOVIE);) {
            stmt.setString(1, entity.getName());
            stmt.setInt(2, entity.getId());
            return stmt.executeUpdate() != 0;

        } catch (SQLException e) {

            throw new DBConnectionException(e);
        }

    }


}



