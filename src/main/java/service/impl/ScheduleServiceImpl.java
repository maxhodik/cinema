package service.impl;

import dto.Filter;
import command.Operation;
import dao.HallDao;
import dao.MovieDao;
import dao.OrderDao;
import dao.SessionDao;
import dto.SessionAdminDto;
import dto.SessionDto;
import entities.Hall;
import entities.Movie;
import entities.Order;
import entities.Session;
import exceptions.DBException;
import service.ScheduleService;
import service.strategy.Factory;
import service.strategy.FactoryImpl;
import service.strategy.SortStrategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ScheduleServiceImpl implements ScheduleService {
    private SessionDao sessionDao;
    private HallDao hallDao;
    private MovieDao movieDao;
    private OrderDao orderDao;

    public ScheduleServiceImpl(SessionDao sessionDao, HallDao hallDao, MovieDao movieDao, OrderDao orderDao) {
        this.sessionDao = sessionDao;
        this.hallDao = hallDao;
        this.movieDao = movieDao;
        this.orderDao = orderDao;
    }

    @Override
    public List<Session> findAll() {
        return sessionDao.findAll();

    }

    @Override
    public List<Session> findAllByMovie() {
        List<Session> sessions = sessionDao.findAllSortedByMovieTitle();
        return sessions;
    }

    @Override
    public List<Session> findAllByDate() {
        List<Session> sessions = sessionDao.findAllSortedByDate();
        return sessions;
    }


    @Override
    public List<Session> findAllByAvailableSeats() {
        List<Session> sessions = sessionDao.findAllSortedByNumberOfAvailableSeats();
        return sessions;
    }

    @Override
    public List<SessionAdminDto> findAllFilterByAndOrderBy(List<Filter> filters, String orderBy) {
        List<Session> sessions;
        if (filters.size() == 0) {
            sessions = findAllOrderBy(orderBy);
        } else {
            String sqlFilters = convertFiltersToSqlQuery(filters);
            String sqlColumn = getSqlColumn(orderBy);
            sessions = sessionDao.findAllFilterByAvailableViewing(sqlFilters, sqlColumn);
        }
        List<SessionAdminDto> sessionAdminDtoList = getSessionAdminDtoList(sessions);
        // 1-получаем фактори
        // 2-получаем из фактори стратегию по ордеру
        // 3-соритируем по выбранной стратегии
        // 4 - возвращаем сотрированный лист
if (orderBy == null){return sessionAdminDtoList;}
        Factory factory = new FactoryImpl();
        SortStrategy strategy = factory.defineStrategy(orderBy);
        if ( strategy== null) {
            return sessionAdminDtoList;
        }
        return strategy.sort(sessionAdminDtoList);
    }


    private String convertFiltersToSqlQuery(List<Filter> filters) {
        StringBuilder queryBuilder = new StringBuilder(" ");
        for (Filter filter : filters) {
            if (queryBuilder.length() > 1) {
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
                    //todo
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
                //todo map (map passed columnName to table name otherwise throw error)
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
    public boolean update(SessionDto sessionDto) {
        //todo transaction
        int sessionDtoId = sessionDto.getId();
        Session session = sessionDao.findEntityById(sessionDtoId);
        String movieName = sessionDto.getMovieName();
        int numberOfSeats = sessionDto.getNumberOfSeats();
        LocalDate date = sessionDto.getDate();
        LocalTime time = sessionDto.getTime();
        Hall hall = session.getHall();
        hall = getHall(sessionDtoId, numberOfSeats, hall);
        Movie movie = session.getMovie();
        movie.setName(movieName);
        hallDao.update(hall);
        session = Session.builder()
                .id(sessionDtoId)
                .data(date)
                .time(time)
                .movie(movie)
                .hall(hall)
                .build();
        return sessionDao.update(session);
    }

    private Hall getHall(int sessionDtoId, int numberOfSeats, Hall hall) {
        List<Order> entityBySessionId = orderDao.findEntityBySessionId(sessionDtoId);
        int numberOfSoldSeats = 0;
        for (Order orders : entityBySessionId) {
            numberOfSoldSeats += orders.getNumberOfSeats();
        }
        hall = Hall.builder()
                .id(hall.getId())
                .numberAvailableSeats(numberOfSeats - numberOfSoldSeats)
                .numberSeats(numberOfSeats)
                .build();
        return hall;
    }

    @Override
    public boolean create(SessionDto sessionDto) throws DBException {
        Session session;
        String movieName = sessionDto.getMovieName();
        int numberOfSeats = sessionDto.getNumberOfSeats();
        LocalDate date = sessionDto.getDate();
        LocalTime time = sessionDto.getTime();
        Hall hall = Hall.builder().numberSeats(numberOfSeats).numberAvailableSeats(numberOfSeats).build();
        int hallId = hallDao.createId(hall);
        hall = hallDao.findEntityById(hallId);

        Movie movie = movieDao.findEntityByName(movieName);
        session = Session.builder()
                .data(date)
                .time(time)
                .movie(movie)
                .hall(hall)
                .build();
        return sessionDao.create(session);
    }

    @Override
    public SessionDto getSessionDto(int id) {
        Session session = findEntityById(id);
        int sessionId = session.getId();
        String movieName = session.getMovie().getName();
        LocalDate date = session.getDate();
        LocalTime time = session.getTime();
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        int numberOfSeats = session.getHall().getNumberSeats();
        SessionDto sessionDto = new SessionDto(sessionId, movieName, date, time, numberOfSeats);
        return sessionDto;
    }

    public List<SessionAdminDto> getSessionAdminDtoList(List<Session> allSortedSessions) {
        List<SessionAdminDto> sessionDtoList = new ArrayList<>();
        for (Session s : allSortedSessions) {
            int numberAvailableSeats = s.getHall().getNumberAvailableSeats();
            int capacity = s.getHall().getNumberSeats();
            int numberOfSoldSeats = capacity - numberAvailableSeats;
            BigDecimal attendance = new BigDecimal((float) numberOfSoldSeats / capacity * 100);
            SessionAdminDto sessionAdminDto = new SessionAdminDto(s.getId(),
                    s.getMovie().getName(),
                    s.getDate(),
                    s.getTime(),
                    numberAvailableSeats,
                    capacity);
            sessionAdminDto.setDayOfWeek(s.getDate().getDayOfWeek());
            sessionAdminDto.setNumberOfSoldSeats(capacity - numberAvailableSeats);
            sessionAdminDto.setAttendance(attendance.setScale(2, RoundingMode.HALF_UP));
            sessionDtoList.add(sessionAdminDto);
        }
        return sessionDtoList;
    }

}

