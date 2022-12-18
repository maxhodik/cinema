package exceptions;

public class DuplicateDBException extends Exception{
    public DuplicateDBException (String message){
        super(message);
    }
}
