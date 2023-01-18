package exceptions;

import java.sql.SQLException;

public class SaveOrderException extends RuntimeException {
    public SaveOrderException (String message, SQLException e){
        super(message);
    }

}
