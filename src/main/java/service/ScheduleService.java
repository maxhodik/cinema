package service;

import command.Filter;
import dto.SessionAdminDto;
import dto.SessionDto;
import entities.Session;
import exceptions.DBException;

import java.util.List;

public interface ScheduleService {

    List<Session> findAll();

    List<Session> findAllByMovie();

    List<Session> findAllByDate();

    List<Session> findAllByAvailableSeats();


    List<SessionAdminDto> findAllFilterByAndOrderBy(List<Filter> filters, String orderBy);
    List<Session> findAllOrderBy(String columnName);

    Session findEntityById(Integer id);
    boolean delete(Session entity);
    boolean update(SessionDto sessionDto);
    boolean create(SessionDto sessionDto) throws DBException;
    SessionDto getSessionDto(int id);
    List<SessionAdminDto> getSessionAdminDtoList(List<Session> allSortedSessions);

}