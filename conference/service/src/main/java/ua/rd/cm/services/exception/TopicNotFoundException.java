package ua.rd.cm.services.exception;

public class TopicNotFoundException extends ResourceNotFoundException {

    public TopicNotFoundException() {
        super(TOPIC_NOT_FOUND);
    }
}
