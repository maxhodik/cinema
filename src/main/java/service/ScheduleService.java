package service;


import dto.Filter;
import dto.SessionAdminDto;
import dto.SessionDto;
import entities.Role;
import entities.Session;
import exceptions.DBException;
import exceptions.ServiceException;

import java.util.List;

public interface ScheduleService {

    List<Session> findAll();

    List<SessionAdminDto> findAllFilterByAndOrderBy(List<Filter> filters, String orderBy, String limits);
    List<SessionAdminDto> findAllFilterBy (List<Filter> filters);
    List<Session> findAllOrderBy(String columnName);

    Session findEntityById(Integer id);
    boolean delete(Session entity);
    boolean update(SessionDto sessionDto);
    boolean updateStatus (Session session);
    boolean create(SessionDto sessionDto) throws DBException;
    SessionDto getSessionDto(int id);
    List<SessionAdminDto> getSessionAdminDtoList(List<Session> allSortedSessions);
    int getNumberOfRecords(List<Filter> filters) throws ServiceException;
}
