package service.impl;

import command.Operation;
import dao.SessionDao;
import dto.Filter;
import dto.SessionAdminDto;
import dto.SessionDto;
import entities.*;
import exceptions.DBException;
import service.HallService;
import service.MovieService;
import service.OrderService;
import service.ScheduleService;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ScheduleServiceImpl implements ScheduleService {
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
//        // 1-получаем фактори
//        // 2-получаем из фактори стратегию по ордеру
//        // 3-соритируем по выбранной стратегии
//        // 4 - возвращаем сотрированный лист
//        if (orderBy == null) {
//            return sessionAdminDtoList;
//        }
//        Factory factory = new FactoryImpl();
//        SortStrategy strategy = factory.defineStrategy(orderBy);
//        if (strategy == null) {
//            return sessionAdminDtoList;
//        }
//        return strategy.sort(sessionAdminDtoList);
        return sessionAdminDtoList;
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
    public boolean updateStatus(Session session) {
        //todo transaction
        Session updetedSession = Session.builder()
                .id(session.getId())
                .data(session.getDate())
                .time(session.getTime())
                .movie(session.getMovie())
                .hall(session.getHall())
                .status(Status.CANCELED)
                .build();
        sessionDao.update(updetedSession);
       List<Order> orders = orderService.findAllBySessionId(updetedSession.getId());
        for (Order o : orders) {
            Order updetedOrder = Order.builder()
                    .id(o.getId())
                    .user(o.getUser())
                    .session(o.getSession())
                    .price(o.getPrice())
                    .numberOfSeats(o.getNumberOfSeats())
                    .state(State.CANCELED)
                    .build();
            orderService.update(updetedOrder);
        }
       return true;
    }

    @Override
    public boolean update(SessionDto sessionDto) {
        //todo transaction
        // todo check orders. If bought tickets > new capacity --> throw error
        int sessionDtoId = sessionDto.getId();
        Session session = sessionDao.findEntityById(sessionDtoId);
        String movieName = sessionDto.getMovieName();
        int numberOfSeats = sessionDto.getNumberOfSeats();
        Hall hall = session.getHall();
//        hall = getHall(sessionDtoId, numberOfSeats, hall);
        Hall updatedHall = hallService.changeHallCapacity(hall, numberOfSeats);
//        hallDao.update(hall);
        //todo validate movie name
        Movie movie = session.getMovie();
        movie.setName(movieName);
        session = buildSession(sessionDto, hall, movie);
        // todo update session and hall in transaction
        return sessionDao.update(session);
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

    private Hall getHall(int sessionDtoId, int numberOfSeats, Hall hall) {
        List<Order> entityBySessionId = orderService.findAllBySessionId(sessionDtoId);
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
        String movieName = sessionDto.getMovieName();
        int numberOfSeats = sessionDto.getNumberOfSeats();
        Hall hall = hallService.createWithCapacity(numberOfSeats);

        Movie movie = movieService.findEntityByName(movieName);
        Session session = buildSession(sessionDto, hall, movie);
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

}

