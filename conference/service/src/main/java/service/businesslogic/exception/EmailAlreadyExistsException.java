package service.businesslogic.exception;

/**
 * @author Andrii Markovych
 */
public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
