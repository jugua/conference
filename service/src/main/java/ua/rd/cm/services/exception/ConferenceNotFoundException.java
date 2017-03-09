package ua.rd.cm.services.exception;

public class ConferenceNotFoundException extends ResourceNotFoundException {

    public ConferenceNotFoundException() {
        super(CONFERENCE_NOT_FOUND);
    }
}
