package ua.rd.cm.services.exception;

public class TalkNotFoundException extends EntityNotFoundException {

    public TalkNotFoundException() {
        super("talk_not_found");
    }
}
