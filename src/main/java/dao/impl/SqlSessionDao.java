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
import exceptions.DBConnectionException;
import persistance.ConnectionPoolHolder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlSessionDao implements SessionDao {
    private ObjectMapper<Session> mapper;
    private ObjectMapper<Hall> hallMapper;
    private ObjectMapper<Movie> movieMapper;
    private final ConnectionPoolHolder connectionPoolHolder;

    public SqlSessionDao(final ConnectionPoolHolder connectionPoolHolder) {
        this.connectionPoolHolder = connectionPoolHolder;
        this.hallMapper = new HallMapper();
        this.movieMapper = new MovieMapper();
    }


    @Override
    public List<Session> findAll() {
        List<Session> sessions = new ArrayList<>();
        try (
                Connection con = connectionPoolHolder.getConnection();
                Statement stmt = con.createStatement();
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
            throw new DBConnectionException(e);
        }
        return sessions;
    }

    @Override
    public Session findEntityById(Integer id) {
        try (Connection con = connectionPoolHolder.getConnection();
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
            throw new DBConnectionException(e);
        }
    }

    @Override
    public boolean delete(Session entity) {
        try (Connection con = connectionPoolHolder.getConnection();
             PreparedStatement stmt = con.prepareStatement(Constants.DELETE_SESSION_BY_ID);) {
            stmt.setInt(1, entity.getId());
            return stmt.executeUpdate() != 0;

        } catch (SQLException e) {
            throw new DBConnectionException(e);
        }

    }

    @Override
    public boolean create(Session session) {
        // need create new Hall???
        try (Connection con = connectionPoolHolder.getConnection();
             PreparedStatement stmt = con.prepareStatement(Constants.INSERT_INTO_SESSIONS, Statement.RETURN_GENERATED_KEYS);) {
            stmt.setDate(1, Date.valueOf(session.getDate()));
            stmt.setInt(2, session.getMovie().getId());
            stmt.setInt(3, session.getHall().getId());
            stmt.setTime(4, Time.valueOf(session.getTime()));
            return stmt.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new DBConnectionException(e);
        }
    }


    @Override
    public boolean update(Session entity) {
        try (Connection con = connectionPoolHolder.getConnection();
             PreparedStatement stmt = con.prepareStatement(Constants.UPDATE_SESSIONS);) {
            int k=0;
            stmt.setDate(++k, Date.valueOf(entity.getDate()));
            stmt.setInt(++k, entity.getMovie().getId());
            stmt.setInt(++k, entity.getHall().getId());
            stmt.setTime(++k, Time.valueOf(entity.getTime()));
            stmt.setString(++k, entity.getStatus().toString());
            stmt.setInt(++k, entity.getId());
            return stmt.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new DBConnectionException(e);
        }
    }

    @Override
    public List<Session> findAllSortedByNumberOfAvailableSeats() {
        List<Session> sessions = new ArrayList<>();
        try (
                Connection con = connectionPoolHolder.getConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(Constants.FIND_ALL_SESSIONS);) {
            while (rs.next()) {
                Session session;
                mapper = new SessionMapper();
                session = mapper.extractFromResultSet(rs);
                session.setMovie(movieMapper.extractFromResultSet(rs));
                session.setHall(hallMapper.extractFromResultSet(rs));
                sessions.add(session);
            }
        } catch (SQLException e) {
            throw new DBConnectionException(e);
        }
        return sessions;
    }

    @Override
    public List<Session> findAllSortedByMovieTitle() {
        List<Session> sessions = new ArrayList<>();
        try (Connection con = connectionPoolHolder.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(Constants.FIND_ALL_SESSIONS_SORTED_BY_MOVIE_TITLE);) {
            while (rs.next()) {
                Session session;
                mapper = new SessionMapper();
                session = mapper.extractFromResultSet(rs);
                session.setMovie(movieMapper.extractFromResultSet(rs));
                session.setHall(hallMapper.extractFromResultSet(rs));
                sessions.add(session);
            }
        } catch (SQLException e) {
            throw new DBConnectionException(e);
        }

        return sessions;
    }

    @Override
    public List<Session> findAllSortedByDate() {
        List<Session> sessions = new ArrayList<>();
        try (
                Connection con = connectionPoolHolder.getConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(Constants.FIND_ALL_SESSIONS_SORTED_BY_DATE);) {
            while (rs.next()) {
                Session session;
                mapper = new SessionMapper();
                session = mapper.extractFromResultSet(rs);
                session.setMovie(movieMapper.extractFromResultSet(rs));
                session.setHall(hallMapper.extractFromResultSet(rs));
                sessions.add(session);

            }
        } catch (SQLException e) {
            throw new DBConnectionException(e);
        }
        return sessions;
    }

    @Override
    public List<Session> findAllFilterByAvailableViewing(String filterBy, String orderBy) {
        List<Session> sessions = new ArrayList<>();
        try (
                Connection con = connectionPoolHolder.getConnection();
                PreparedStatement stmt = con.prepareStatement(Constants.FIND_ALL_SESSIONS_FILTER_BY_SORTED_BY + filterBy + " ORDER BY " + orderBy)) {
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
            throw new DBConnectionException(e);
        }
        return sessions;
    }


    @Override
    public List<Session> findAllOrderBy(String columnName) {
        List<Session> sessions = new ArrayList<>();
        try (
                Connection con = connectionPoolHolder.getConnection();
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
            throw new DBConnectionException(e);
        }
        return sessions;
    }
}

