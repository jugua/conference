package ua.rd.cm.services.exception;

/**
 * @author Andrii Markovych
 */
public class NoSuchUserException extends RuntimeException {
    public NoSuchUserException(String message) {
        super(message);
    }
}
