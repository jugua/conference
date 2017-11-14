package service.businesslogic.exception;

public class ConferenceNotFoundException extends ResourceNotFoundException {

    public ConferenceNotFoundException() {
        super(CONFERENCE_NOT_FOUND);
    }
}
