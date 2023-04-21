package service.impl;


import command.Operation;
import command.ScheduleCommand;
import dao.SessionDao;
import dto.Filter;
import dto.SessionAdminDto;
import dto.SessionDto;
import entities.*;
import exceptions.EntityAlreadyExistException;
import exceptions.TransactionException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import persistance.TransactionManagerWrapper;
import service.HallService;
import service.MovieService;
import service.OrderService;
import service.ScheduleService;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ScheduleServiceImpl implements ScheduleService {
    private static final Logger LOGGER = LogManager.getLogger(ScheduleServiceImpl.class);
    private final SessionDao sessionDao;
    private final HallService hallService;
    private final MovieService movieService;
    private final OrderService orderService;

    public ScheduleServiceImpl(SessionDao sessionDao, HallService hallService, MovieService movieService, OrderService orderService) {
        this.sessionDao = sessionDao;
        this.hallService = hallService;
        this.movieService = movieService;
        this.orderService = orderService;
    }

    @Override
    public List<Session> findAll() {
        return sessionDao.findAll();

    }


    @Override
    public List<SessionAdminDto> findAllFilterByAndOrderBy(List<Filter> filters, String orderBy, String limits) {
        List<Session> sessions;

        String sqlFilters = convertFiltersToSqlQuery(filters);
        String sqlColumn = getSqlColumn(orderBy);
        sessions = sessionDao.findAllFilterByAvailableViewing(sqlFilters, sqlColumn, limits);
        List<SessionAdminDto> sessionAdminDtoList = getSessionAdminDtoList(sessions);
        return sessionAdminDtoList;
    }

    @Override
    public List<SessionAdminDto> findAllFilterBy(List<Filter> filters) {
        List<Session> sessions;
        String sqlFilters = convertFiltersToSqlQuery(filters);

        sessions = sessionDao.findAllFilterByAvailableViewing(sqlFilters);

        List<SessionAdminDto> sessionAdminDtoList = getSessionAdminDtoList(sessions);

        return sessionAdminDtoList;
    }

    @Override
    public boolean findByMovie(String name) {
        return sessionDao.findByMovie(name);
    }

    private String convertFiltersToSqlQuery(List<Filter> filters) {
        if (filters == null || filters.isEmpty()) {
            return "";
        }
        StringBuilder queryBuilder = new StringBuilder(" WHERE ");
        int i = 0;
        for (Filter filter : filters) {
            i++;
            if (i > 1) {
                queryBuilder.append(" AND ");
            }
            String column = getSqlColumn(filter.getColumn());
            if (column == null) {
                continue;
            }
            List<String> values = filter.getValues();
            String value = getValueQueryFromList(values);
            Operation operation = filter.getOperations();
            switch (operation) {

                case BETWEEN:
                    queryBuilder.append(column).append(" BETWEEN '").append(values.get(0)).append("' AND '").append(values.get(1)).append("'");
                    break;
                case WEEKDAY:
                    queryBuilder.append(" (select WEEKDAY( " + column + ")) " + getValueQueryFromList(values));
                    break;
                case IN:
                    queryBuilder.append(column + getValueQueryFromList(values));
                    break;
                case IS:
                    if (column.equals(getSqlColumn("number_available_seats"))) {
                        if (Boolean.parseBoolean(values.get(0))) {
                            queryBuilder.append(column).append(">").append("0");
                        }
                    } else {
                        queryBuilder.append(column).append("=").append(value);
                    }
                case MORE:
                    if (column.equals("datetime")) {
                        queryBuilder.append("CONCAT(date, 'T', time) >").append(value);
                    }
            }
        }
        return queryBuilder.toString();
    }

    private String getValueQueryFromList(List<String> values) {
        String value;
        if (values.size() > 1) {
            value = " IN('" + String.join("','", values) + "')";
        } else {
            value = "= '" + values.get(0) + "'";
        }
        return value;
    }

    @Override
    public List<Session> findAllOrderBy(String columnName) {
        String sqlColumn;
        sqlColumn = getSqlColumn(columnName);
        return sessionDao.findAllOrderBy(sqlColumn);
    }

    private String getSqlColumn(String columnName) {
        String sqlColumn = null;
        try {
            if (columnName != null) {
                ResourceBundle bundle = ResourceBundle.getBundle("mapping");
                sqlColumn = bundle.getString(columnName);
            }
        } catch (MissingResourceException e) {
            return null;
        }
        return sqlColumn;
    }

    @Override
    public Session findEntityById(Integer id) {
        return sessionDao.findEntityById(id);
    }

    @Override
    public boolean delete(Session entity) {
        return sessionDao.delete(entity);
    }

    @Override
    public boolean canceledSession(Session session) {


        Session updatedSession= session;
        updatedSession.setStatus(Status.CANCELED);
        try {
            TransactionManagerWrapper.startTransaction();

            sessionDao.update(updatedSession);
            orderService.canceledOrder(updatedSession.getId());

            TransactionManagerWrapper.commit();
            LOGGER.info(String.format("Successful update session with id= %s and orders status", session.getId()));
            return true;
        } catch (SQLException | EntityAlreadyExistException e) {
            LOGGER.error("Update session and orders status failed", e);
            try {
                TransactionManagerWrapper.rollback();
                LOGGER.error("Transaction rollback");
                throw new TransactionException("Can't update session and oder status");
            } catch (SQLException ex) {
                LOGGER.error("Transaction rollback failed", ex);
                throw new TransactionException(ex);
            }

        }
    }


    @Override
    public boolean update(SessionDto sessionDto) {
        int sessionDtoId = sessionDto.getId();
        String movieName = sessionDto.getMovieName();
        int numberOfSeats = sessionDto.getNumberOfSeats();
        try {
            TransactionManagerWrapper.startTransaction();
            Session session = sessionDao.findEntityById(sessionDtoId);
            Hall hall = session.getHall();
            hallService.changeHallCapacity(hall, numberOfSeats);
            Movie movie = movieService.findEntityByName(movieName);
            session = buildSession(sessionDto, hall, movie);
            boolean update = sessionDao.update(session);
            TransactionManagerWrapper.commit();
            LOGGER.info(String.format("Successful update session(id=%s)  and hall in transaction", sessionDtoId));
            return update;
        } catch (SQLException | EntityAlreadyExistException e) {
            LOGGER.error("Update session and hall status failed", e);
            try {
                TransactionManagerWrapper.rollback();
            } catch (SQLException ex) {
                LOGGER.error("Transaction rollback failed", ex);
                throw new TransactionException(ex);
            }
            return false;
        }
    }

    private static Session buildSession(SessionDto sessionDto, Hall hall, Movie movie) {
        return Session.builder()
                .id(sessionDto.getId())
                .data(sessionDto.getDate())
                .time(sessionDto.getTime())
                .status(sessionDto.getStatus())
                .movie(movie)
                .hall(hall)
                .build();
    }


    @Override
    public boolean create(SessionDto sessionDto) {
        try {
            TransactionManagerWrapper.startTransaction();
            String movieName = sessionDto.getMovieName();
            int numberOfSeats = sessionDto.getNumberOfSeats();
            Hall hall = hallService.createWithCapacity(numberOfSeats);
            Movie movie = movieService.findEntityByName(movieName);
            Session session = buildSession(sessionDto, hall, movie);
            boolean create = sessionDao.create(session);
            TransactionManagerWrapper.commit();
            LOGGER.info("Successful create session and hall in transaction");
            return create;
        } catch (SQLException | EntityAlreadyExistException e) {
            LOGGER.error("Create session and hall failed", e);
            try {
                TransactionManagerWrapper.rollback();
            } catch (SQLException ex) {
                LOGGER.error("Transaction rollback failed", ex);
                throw new TransactionException(ex);
            }
        }
        return false;
    }


    @Override
    public SessionDto getSessionDto(int id) {
        Session session = findEntityById(id);
        int sessionId = session.getId();
        String movieName = session.getMovie().getName();
        LocalDate date = session.getDate();
        LocalTime time = session.getTime();
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        Status status = session.getStatus();
        int numberOfSeats = session.getHall().getCapacity();
        SessionDto sessionDto = new SessionDto(sessionId, movieName, date, time, status, numberOfSeats);
        return sessionDto;
    }

    public List<SessionAdminDto> getSessionAdminDtoList(List<Session> allSortedSessions) {
        List<SessionAdminDto> sessionDtoList = new ArrayList<>();
        for (Session s : allSortedSessions) {
            int numberAvailableSeats = s.getHall().getNumberAvailableSeats();
            int capacity = s.getHall().getCapacity();
            int numberOfSoldSeats = s.getHall().getNumberOfSoldSeats();
            BigDecimal attendance = s.getHall().getAttendance();
            SessionAdminDto sessionAdminDto = new SessionAdminDto(s.getId(),
                    s.getMovie().getName(),
                    s.getDate(),
                    s.getTime(),
                    numberAvailableSeats,
                    numberOfSoldSeats,
                    capacity,
                    attendance,
                    s.getStatus());
            sessionAdminDto.setDayOfWeek(s.getDate().getDayOfWeek());
            sessionDtoList.add(sessionAdminDto);
        }
        return sessionDtoList;
    }

    @Override
    public int getNumberOfRecords(List<Filter> filters) {
        String sqlFilters = convertFiltersToSqlQuery(filters);
        int numberOfRecords;
        numberOfRecords = sessionDao.getNumberOfRecords(sqlFilters);
        return numberOfRecords;
    }
}


