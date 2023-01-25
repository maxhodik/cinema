package dao.impl;

import dao.SessionDao;
import dao.maper.HallMapper;
import dao.maper.MovieMapper;
import dao.maper.ObjectMapper;
import dao.maper.SessionMapper;
import dao.Constants;
import entities.Hall;
import entities.Movie;
import entities.Session;
import exceptions.DAOException;
import exceptions.DBConnectionException;
import exceptions.DBException;
import persistance.ConnectionPoolHolder;
import persistance.ConnectionWrapper;
import persistance.TransactionManagerWrapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlSessionDao implements SessionDao {
    private ObjectMapper<Session> mapper;
    private ObjectMapper<Hall> hallMapper;
    private ObjectMapper<Movie> movieMapper;


    public SqlSessionDao() {
        this.hallMapper = new HallMapper();
        this.movieMapper = new MovieMapper();
    }


    @Override
    public List<Session> findAll() {
        List<Session> sessions = new ArrayList<>();
        try (
                ConnectionWrapper con = TransactionManagerWrapper.getConnection();
                Statement stmt = con.statement();
                ResultSet rs = stmt.executeQuery(Constants.FIND_ALL_SESSIONS_SORTED_BY_NUMBER_OF_SEATS);) {
            while (rs.next()) {
                Session session;
                mapper = new SessionMapper();
                session = mapper.extractFromResultSet(rs);
                session.setMovie(movieMapper.extractFromResultSet(rs));
                session.setHall(hallMapper.extractFromResultSet(rs));
                sessions.add(session);

            }
        } catch (SQLException e) {
            throw new RuntimeException("Sessions not found", e);
        }
        return sessions;
    }

    @Override
    public Session findEntityById(Integer id) {
        try (ConnectionWrapper con = TransactionManagerWrapper.getConnection();
             PreparedStatement stmt = con.prepareStatement(Constants.FIND_SESSION_BY_ID);) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            mapper = new SessionMapper();
            Session session = mapper.extractFromResultSet(rs);
            session.setMovie(movieMapper.extractFromResultSet(rs));
            session.setHall(hallMapper.extractFromResultSet(rs));
            return session;
        } catch (SQLException e) {
            throw new RuntimeException("Sessions not found", e);
        }
    }
@Override
    public boolean findByMovie(String name) {
        try (
                ConnectionWrapper con = TransactionManagerWrapper.getConnection();
                PreparedStatement stmt = con.prepareStatement(Constants.FIND_ALL_SESSIONS_BY_MOVIE_NAME)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return true;
            }return false;
        } catch (SQLException e) {
            throw new RuntimeException("Sessions not found", e);
        }
    }

    @Override
    public boolean delete(Session entity) {
        try (ConnectionWrapper con = TransactionManagerWrapper.getConnection();
             PreparedStatement stmt = con.prepareStatement(Constants.DELETE_SESSION_BY_ID);) {
            stmt.setInt(1, entity.getId());
            return stmt.executeUpdate() != 0;

        } catch (SQLException e) {
            throw new RuntimeException("Sessions not found", e);
        }

    }

    @Override
    public boolean create(Session session) {

        try (ConnectionWrapper con = TransactionManagerWrapper.getConnection();
             PreparedStatement stmt = con.prepareStatement(Constants.INSERT_INTO_SESSIONS, Statement.RETURN_GENERATED_KEYS);) {
            stmt.setDate(1, Date.valueOf(session.getDate()));
            stmt.setInt(2, session.getMovie().getId());
            stmt.setInt(3, session.getHall().getId());
            stmt.setTime(4, Time.valueOf(session.getTime()));
            return stmt.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException("Session not created", e);
        }
    }


    @Override
    public boolean update(Session entity) {
        try (ConnectionWrapper con = TransactionManagerWrapper.getConnection();
             PreparedStatement stmt = con.prepareStatement(Constants.UPDATE_SESSIONS);) {
            int k = 0;
            stmt.setDate(++k, Date.valueOf(entity.getDate()));
            stmt.setInt(++k, entity.getMovie().getId());
            stmt.setInt(++k, entity.getHall().getId());
            stmt.setTime(++k, Time.valueOf(entity.getTime()));
            stmt.setString(++k, entity.getStatus().toString());
            stmt.setInt(++k, entity.getId());
            return stmt.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException("Session not updated", e);
        }
    }


    @Override
    public List<Session> findAllFilterByAvailableViewing(String filterBy, String orderBy, String limits) {
        List<Session> sessions = new ArrayList<>();
        try (
                ConnectionWrapper con = TransactionManagerWrapper.getConnection();
                PreparedStatement stmt = con.prepareStatement(Constants.FIND_ALL_SESSIONS_FILTER_BY_SORTED_BY + filterBy + " ORDER BY " + orderBy + limits)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Session session;
                mapper = new SessionMapper();
                session = mapper.extractFromResultSet(rs);
                session.setMovie(movieMapper.extractFromResultSet(rs));
                session.setHall(hallMapper.extractFromResultSet(rs));
                sessions.add(session);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Sessions not found", e);
        }
        return sessions;
    }

    @Override
    public List<Session> findAllFilterByAvailableViewing(String filterBy) {
        List<Session> sessions = new ArrayList<>();
        try (
                ConnectionWrapper con = TransactionManagerWrapper.getConnection();
                PreparedStatement stmt = con.prepareStatement(Constants.FIND_ALL_SESSIONS_FILTER_BY_SORTED_BY + filterBy)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Session session;
                mapper = new SessionMapper();
                session = mapper.extractFromResultSet(rs);
                session.setMovie(movieMapper.extractFromResultSet(rs));
                session.setHall(hallMapper.extractFromResultSet(rs));
                sessions.add(session);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Sessions not found", e);
        }
        return sessions;
    }


    @Override
    public List<Session> findAllOrderBy(String columnName) {
        List<Session> sessions = new ArrayList<>();
        try (
                ConnectionWrapper con = TransactionManagerWrapper.getConnection();
                PreparedStatement stmt = con.prepareStatement(Constants.FIND_ALL_SESSIONS_SORTED_ORDER_BY + columnName)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Session session;
                mapper = new SessionMapper();
                session = mapper.extractFromResultSet(rs);
                session.setMovie(movieMapper.extractFromResultSet(rs));
                session.setHall(hallMapper.extractFromResultSet(rs));
                sessions.add(session);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Sessions not found", e);
        }
        return sessions;
    }

    @Override
    public int getNumberOfRecords(String filters) {
        int numberOfRecords = 0;
        try (ConnectionWrapper con = TransactionManagerWrapper.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(Constants.COUNT_ALL_SESSIONS_FILTER_BY_SORTED_BY + filters)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    numberOfRecords = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Sessions not found", e);
        }
        return numberOfRecords;
    }
}

