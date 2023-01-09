package persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionWrapper implements AutoCloseable {
    private Connection connection;

    public ConnectionWrapper(Connection connection) {
        this.connection = connection;
    }



    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }
    public PreparedStatement prepareStatement(String sql,int statement ) throws SQLException {
        return connection.prepareStatement(sql,statement);
    }

    public Statement statement() throws SQLException {
        return connection.createStatement();
    }

    public void setAutoCommit(boolean autoCommit) throws SQLException {
        connection.setAutoCommit(autoCommit);
    }

    public boolean isTransaction() throws SQLException {
        return !connection.getAutoCommit();
    }

    public void commit() throws SQLException {
        connection.commit();
    }

    public void rollback() throws SQLException {
        connection.rollback();
    }

    @Override
    public void close() throws SQLException {
        if(!isTransaction()){
            connection.close();
        }
    }


}
