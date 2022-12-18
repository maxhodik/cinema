package exceptions;

public class SaveOrderException extends RuntimeException {
    public SaveOrderException (String message){
        super(message);
    }

}
