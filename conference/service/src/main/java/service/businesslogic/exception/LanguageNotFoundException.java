package service.businesslogic.exception;

public class LanguageNotFoundException extends ResourceNotFoundException {

    public LanguageNotFoundException() {
        super(LANGUAGE_NOT_FOUND);
    }
}
