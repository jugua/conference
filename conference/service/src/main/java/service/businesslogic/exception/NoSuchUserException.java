package service.businesslogic.exception;

/**
 * @author Andrii Markovych
 */
public class NoSuchUserException extends RuntimeException {
    public NoSuchUserException(String message) {
        super(message);
    }
}
