package ua.rd.cm.services.exception;

public class TalkNotFoundException extends ResourceNotFoundException {

    public TalkNotFoundException() {
        super(TALK_NOT_FOUND);
    }
}
