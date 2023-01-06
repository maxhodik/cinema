package exceptions;

import java.sql.SQLException;

public class DAOException extends Exception {
    public DAOException(Throwable cause) {
        super(cause);
    }
}
