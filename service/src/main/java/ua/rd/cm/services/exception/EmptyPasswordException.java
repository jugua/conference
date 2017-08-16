package ua.rd.cm.services.exception;

/**
 * @author Andrii Markovych
 */
public class EmptyPasswordException extends RuntimeException {
    public EmptyPasswordException(String message) {
        super(message);
    }
}
