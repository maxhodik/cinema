package dao;

import entities.Hall;
import exceptions.DBException;

public interface HallDao extends GenericDao<Integer,Hall> {
    Hall createAndReturnWithId(Hall entity) ;

}
