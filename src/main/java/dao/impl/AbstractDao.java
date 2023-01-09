package dao.impl;

import persistance.ConnectionPoolHolder;
import persistance.ConnectionWrapper;

import java.sql.Connection;

public abstract class AbstractDao {

    protected Connection con;

    protected ConnectionWrapper getConnectionWrapper() {
        if (con != null) {
            return new ConnectionWrapper(con);
        }
        return new ConnectionWrapper(ConnectionPoolHolder.pool().getConnection());
    }
}
