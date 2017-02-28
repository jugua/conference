package ua.rd.cm.services.exception;

public class LanguageNotFoundException extends ResourceNotFoundException {

    public LanguageNotFoundException() {
        super("language_not_found");
    }
}
