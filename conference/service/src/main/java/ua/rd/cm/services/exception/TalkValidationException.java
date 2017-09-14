package ua.rd.cm.services.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

/**
 * Created by Valeriia_Fatova on 8/9/2017.
 */
public class TalkValidationException extends RuntimeException {
    public static final String ADDITIONAL_COMMENT_TOO_LONG = "additional_info_too_long";
    public static final String ORG_COMMENT_TOO_LONG = "comment_too_long";
    public static final String ORG_COMMENT_IS_EMPTY = "empty_comment";
    public static final String STATUS_IS_WRONG = "wrong_status";
    public static final String STATUS_IS_NULL = "status_is_null";
    public static final String NOT_ALLOWED_TO_UPDATE = "forbidden";

    private Map<String, HttpStatus> messageStatusMap;

    public TalkValidationException(String message) {
        super(message);
        messageStatusMap = new HashMap<String, HttpStatus>() {{
            put(ADDITIONAL_COMMENT_TOO_LONG, HttpStatus.PAYLOAD_TOO_LARGE);
            put(ORG_COMMENT_TOO_LONG, HttpStatus.PAYLOAD_TOO_LARGE);
            put(ORG_COMMENT_IS_EMPTY, HttpStatus.BAD_REQUEST);
            put(STATUS_IS_WRONG, HttpStatus.CONFLICT);
            put(STATUS_IS_NULL, HttpStatus.BAD_REQUEST);
            put(NOT_ALLOWED_TO_UPDATE, HttpStatus.FORBIDDEN);
        }};
    }

    public HttpStatus getHttpStatus() {
        return messageStatusMap.get(getMessage()) != null ? messageStatusMap.get(getMessage()) : HttpStatus.BAD_REQUEST;
    }
}
