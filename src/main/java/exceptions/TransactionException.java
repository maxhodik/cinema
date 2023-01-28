package exceptions;

import java.sql.SQLException;

public class TransactionException extends RuntimeException{

    public TransactionException(SQLException s) {

    }

    public TransactionException(String s) {

    }
}
