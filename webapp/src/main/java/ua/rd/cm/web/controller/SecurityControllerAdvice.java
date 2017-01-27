package ua.rd.cm.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ua.rd.cm.web.controller.dto.MessageDto;

@RestControllerAdvice
public class SecurityControllerAdvice {
    public static final String UNAUTHORIZED_MSG = "unauthorized";

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<MessageDto> handleAccessDenied() {
        MessageDto dto = new MessageDto();
        dto.setError(UNAUTHORIZED_MSG);

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(dto);
    }
}
