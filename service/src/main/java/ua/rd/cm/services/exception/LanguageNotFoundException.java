package ua.rd.cm.services.exception;

public class LanguageNotFoundException extends ResourceNotFoundException {

    public LanguageNotFoundException() {
        super(LANGUAGE_NOT_FOUND);
    }
}
