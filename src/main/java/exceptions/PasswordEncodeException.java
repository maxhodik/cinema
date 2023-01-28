package exceptions;

import java.security.NoSuchAlgorithmException;

public class PasswordEncodeException extends RuntimeException{
    public PasswordEncodeException (NoSuchAlgorithmException message){super(message);}

}
