package service.businesslogic.exception;

public class TalkNotFoundException extends ResourceNotFoundException {

    public TalkNotFoundException() {
        super(TALK_NOT_FOUND);
    }
}
