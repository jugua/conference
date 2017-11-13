package service.businesslogic.exception;

/**
 * @author Andrii Markovych
 */
public class PasswordMismatchException extends RuntimeException {
    public PasswordMismatchException(String message) {
        super(message);
    }
}
