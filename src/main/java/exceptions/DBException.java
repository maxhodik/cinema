package exceptions;

import java.sql.SQLException;

public class DBException extends RuntimeException {
    public DBException(String message, SQLException e) {
    }
    public DBException(SQLException e){}
}
