package ua.rd.cm.services.exception;

public class ResourceNotFoundException extends RuntimeException {
    public static final String LEVEL_NOT_FOUND = "level_not_found";
    public static final String FILE_NOT_FOUND = "file_not_found";
    public static final String ROLE_NOT_FOUND = "role_not_found";
    public static final String USER_NOT_FOUND = "user_not_found";
    public static final String CONTACT_TYPE_NOT_FOUND = "contact_type_not_found";
    public static final String USER_INFO_NOT_FOUND = "user_info_not_found";
    public static final String CONFERENCE_NOT_FOUND = "conference_not_found";
    public static final String LANGUAGE_NOT_FOUND = "language_not_found";
    public static final String TALK_NOT_FOUND = "talk_not_found";
    public static final String TOPIC_NOT_FOUND = "topic_not_found_exception";
    public static final String TYPE_NOT_FOUND = "type_not_found_exception";

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
