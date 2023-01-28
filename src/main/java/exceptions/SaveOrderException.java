package exceptions;

import java.sql.SQLException;

public class SaveOrderException extends RuntimeException {
    public SaveOrderException (String message, SQLException e){
        super(message);
    }

    public SaveOrderException(String message) {
        super(message);
    }

    public SaveOrderException(SQLException e) {

    }
}
