package ua.rd.cm.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ua.rd.cm.services.exception.ResourceNotFoundException;
import ua.rd.cm.web.controller.dto.MessageDto;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public MessageDto notFoundHandler(ResourceNotFoundException exception) {
        MessageDto messageDto = new MessageDto();
        messageDto.setError(exception.getMessage());
        return messageDto;
    }
}
