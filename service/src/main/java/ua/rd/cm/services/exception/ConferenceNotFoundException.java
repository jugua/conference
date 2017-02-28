package ua.rd.cm.services.exception;

public class ConferenceNotFoundException extends ResourceNotFoundException {

    public ConferenceNotFoundException() {
        super("conference_not_found");
    }
}
