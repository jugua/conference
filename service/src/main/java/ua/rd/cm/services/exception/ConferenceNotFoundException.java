package ua.rd.cm.services.exception;

public class ConferenceNotFoundException extends EntityNotFoundException {

    public ConferenceNotFoundException() {
        super("conference_not_found");
    }
}
