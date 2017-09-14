package ua.rd.cm.services.exception;

/**
 * @author Andrii Markovych
 */
public class PasswordMismatchException extends RuntimeException {
    public PasswordMismatchException(String message) {
        super(message);
    }
}
