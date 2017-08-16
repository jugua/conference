package ua.rd.cm.services.exception;

/**
 * @author Andrii Markovych
 */
public class WrongRoleException extends RuntimeException {
    public WrongRoleException(String message) {
        super(message);
    }
}
