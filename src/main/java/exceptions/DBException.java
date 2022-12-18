package exceptions;

import java.sql.SQLException;

public class DBException extends Throwable {
    public DBException(String message, SQLException e) {
    }
}
