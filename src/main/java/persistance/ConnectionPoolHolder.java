package persistance;

import exceptions.DBConnectionException;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;




public class ConnectionPoolHolder {

    private static final Logger LOGGER = LogManager.getLogger(ConnectionPoolHolder.class);


    private static volatile ConnectionPoolHolder pool;
    private BasicDataSource dataSource;

    private ConnectionPoolHolder() {
        ResourceBundle bundle = ResourceBundle.getBundle("database");
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl(bundle.getString("db.url"));
        ds.setUsername(bundle.getString("db.user"));
        ds.setPassword(bundle.getString("db.password"));
        ds.setDriverClassName(bundle.getString("db.driver"));
        ds.setMinIdle(Integer.parseInt(bundle.getString("db.minIdle")));
        ds.setMaxIdle(Integer.parseInt(bundle.getString("db.maxIdle")));
        ds.setInitialSize(Integer.parseInt(bundle.getString("db.maxOpenStatement")));
        ds.setMaxOpenPreparedStatements(Integer.parseInt(bundle.getString("db.initialSize")));
        dataSource = ds;
    }

    public static ConnectionPoolHolder pool() {
        if (pool == null) {
            synchronized (ConnectionPoolHolder.class) {
                if (pool == null) {
                    LOGGER.info("connection pool created");
                    pool = new ConnectionPoolHolder();
                }
            }
        }
        return pool;
    }


    public Connection getConnection() {
        LOGGER.debug("connect");
        try {
            return this.dataSource.getConnection();
        } catch (SQLException e) {
            LOGGER.info("connection error", e);
            throw new DBConnectionException(e);
        }
    }


}
