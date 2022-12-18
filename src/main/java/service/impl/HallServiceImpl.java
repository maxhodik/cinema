package service.impl;

import dao.HallDao;
import entities.Hall;
import exceptions.DBException;
import service.HallService;

import java.util.List;

public class HallServiceImpl implements HallService {
    private HallDao hallDao;

    public HallServiceImpl(HallDao hallDao) {
        this.hallDao = hallDao;
    }

    @Override
    public List<Hall> findAll() {
        return hallDao.findAll();
    }

    @Override
    public Hall findEntityById(Integer id) {
        return hallDao.findEntityById(id);
    }

    @Override
    public boolean delete(Hall entity) {
        return hallDao.delete(entity);
    }

    @Override
    public boolean create(Hall entity) throws DBException {
        return hallDao.create(entity);
    }

    @Override
    public boolean update(Hall entity) {
        return hallDao.update(entity);
    }
}
