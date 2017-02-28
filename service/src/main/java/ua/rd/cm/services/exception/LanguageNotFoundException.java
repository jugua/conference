package ua.rd.cm.services.exception;

public class LanguageNotFoundException extends EntityNotFoundException {

    public LanguageNotFoundException() {
        super("language_not_found");
    }
}
