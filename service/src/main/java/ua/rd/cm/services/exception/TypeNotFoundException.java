package ua.rd.cm.services.exception;

public class TypeNotFoundException extends EntityNotFoundException {

    public TypeNotFoundException() {
        super("type_not_found_exception");
    }
}
