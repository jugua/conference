package ua.rd.cm.services.exception;

public class TopicNotFoundException extends EntityNotFoundException {

    public TopicNotFoundException() {
        super("topic_not_found_exception");
    }
}
