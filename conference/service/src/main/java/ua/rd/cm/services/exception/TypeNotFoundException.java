package ua.rd.cm.services.exception;

public class TypeNotFoundException extends ResourceNotFoundException {

    public TypeNotFoundException() {
        super(TYPE_NOT_FOUND);
    }
}
