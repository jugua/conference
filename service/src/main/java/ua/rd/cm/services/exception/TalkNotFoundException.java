package ua.rd.cm.services.exception;

public class TalkNotFoundException extends ResourceNotFoundException {

    public TalkNotFoundException() {
        super("talk_not_found");
    }
}
