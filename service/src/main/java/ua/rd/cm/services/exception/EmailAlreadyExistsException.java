package ua.rd.cm.services.exception;

/**
 * @author Andrii Markovych
 */
public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
