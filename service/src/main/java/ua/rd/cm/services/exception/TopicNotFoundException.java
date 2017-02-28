package ua.rd.cm.services.exception;

public class TopicNotFoundException extends ResourceNotFoundException {

    public TopicNotFoundException() {
        super("topic_not_found_exception");
    }
}
