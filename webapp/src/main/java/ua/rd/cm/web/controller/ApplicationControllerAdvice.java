package ua.rd.cm.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.log4j.Log4j;
import ua.rd.cm.dto.MessageDto;
import ua.rd.cm.services.exception.ResourceNotFoundException;

@Log4j
@RestControllerAdvice
public class ApplicationControllerAdvice {
    public static final String UNAUTHORIZED_MSG = "unauthorized";

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public MessageDto notFoundHandler(ResourceNotFoundException exception) {
        return messageDtoWithError(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public void validationHandler() {
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({AccessDeniedException.class, AuthenticationException.class})
    public MessageDto authenticationHandler() {
        return messageDtoWithError(UNAUTHORIZED_MSG);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public MessageDto defaultHandler(Exception e) {
        log.error(e);
        return messageDtoWithError("internal_error");
    }

    private MessageDto messageDtoWithError(String errorMsg) {
        MessageDto message = new MessageDto();
        message.setError(errorMsg);
        return message;
    }
}
