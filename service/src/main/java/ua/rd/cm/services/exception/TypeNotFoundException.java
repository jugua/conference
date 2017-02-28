package ua.rd.cm.services.exception;

public class TypeNotFoundException extends ResourceNotFoundException {

    public TypeNotFoundException() {
        super("type_not_found_exception");
    }
}
