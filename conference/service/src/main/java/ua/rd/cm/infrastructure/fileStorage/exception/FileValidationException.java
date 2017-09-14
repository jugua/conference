package ua.rd.cm.infrastructure.fileStorage.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

public class FileValidationException extends RuntimeException {
    public static final String EMPTY = "empty";
    public static final String MAX_SIZE = "max_size";
    public static final String DELETE = "delete";
    public static final String UNSUPPORTED_MEDIA_TYPE = "pattern";

    private Map<String, HttpStatus> messageStatusMap;

    public FileValidationException(String message) {
        super(message);
        messageStatusMap = new HashMap<String, HttpStatus>() {{
            put(EMPTY, HttpStatus.BAD_REQUEST);
            put(MAX_SIZE, HttpStatus.PAYLOAD_TOO_LARGE);
            put(DELETE, HttpStatus.BAD_REQUEST);
            put(UNSUPPORTED_MEDIA_TYPE, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }};
    }

    public HttpStatus getHttpStatus() {
        return messageStatusMap.get(getMessage()) != null ? messageStatusMap.get(getMessage()) : HttpStatus.BAD_REQUEST;
    }
}
