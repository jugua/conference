package web.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.log4j.Log4j;

import service.businesslogic.dto.MessageDto;
import service.businesslogic.exception.NoSuchUserException;
import service.businesslogic.exception.ResourceNotFoundException;
import service.businesslogic.exception.TalkValidationException;
import service.infrastructure.fileStorage.exception.FileValidationException;

@Log4j
@RestControllerAdvice
public class ExceptionAdvice {

    public static final String UNAUTHORIZED_MSG = "unauthorized";

    @ExceptionHandler(TalkValidationException.class)
    public ResponseEntity<MessageDto> handleTalkValidationException(TalkValidationException ex) {
        return new ResponseEntity<>(new MessageDto(ex.getMessage()), ex.getHttpStatus());
    }

    @ExceptionHandler(FileValidationException.class)
    public ResponseEntity<MessageDto> handleFileValidationException(FileValidationException ex) {
        return new ResponseEntity<>(new MessageDto(ex.getMessage()), ex.getHttpStatus());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<MessageDto> handleResourceNotFound(ResourceNotFoundException ex) {
        return new ResponseEntity<>(new MessageDto(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public void validationHandler() {
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({AccessDeniedException.class, AuthenticationException.class})
    public MessageDto authenticationHandler() {
        return new MessageDto(UNAUTHORIZED_MSG);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public MessageDto defaultHandler(Exception e) {
        log.error(e);
        return new MessageDto("internal_error");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({NoSuchUserException.class})
    public MessageDto noSuchUserException(Exception e) {
        log.error(e);
        return new MessageDto(e.getMessage());
    }

}
