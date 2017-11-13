package service.businesslogic.exception;

public class TypeNotFoundException extends ResourceNotFoundException {

    public TypeNotFoundException() {
        super(TYPE_NOT_FOUND);
    }
}
