package web.controller.advice;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import static service.businesslogic.exception.TalkValidationException.NOT_ALLOWED_TO_UPDATE;
import static service.infrastructure.fileStorage.exception.FileValidationException.UNSUPPORTED_MEDIA_TYPE;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import service.businesslogic.dto.MessageDto;
import service.businesslogic.exception.ResourceNotFoundException;
import service.businesslogic.exception.TalkNotFoundException;
import service.businesslogic.exception.TalkValidationException;
import service.infrastructure.fileStorage.exception.FileValidationException;

public class ExceptionAdviceTest {

    private ExceptionAdvice testing;

    @Before
    public void setUp() {
        testing = new ExceptionAdvice();
    }

    @Test
    public void handleTalkNotFoundCorrectStatusAndMessage() {
        ResponseEntity<MessageDto> response = testing.handleResourceNotFound(new TalkNotFoundException());
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
        assertThat(response.getBody().getError(), is(ResourceNotFoundException.TALK_NOT_FOUND));
    }

    @Test
    public void handleTalkValidExceptionCorrectStatusAndMessage() {
        ResponseEntity<MessageDto> response = testing.handleTalkValidationException(new TalkValidationException(NOT_ALLOWED_TO_UPDATE));
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
        assertThat(response.getBody().getError(), is(NOT_ALLOWED_TO_UPDATE));
    }

    @Test
    public void handleFileValidExceptionCorrectStatusAndMessage() {
        ResponseEntity<MessageDto> response = testing.handleFileValidationException(new FileValidationException(UNSUPPORTED_MEDIA_TYPE));
        assertThat(response.getStatusCode(), is(HttpStatus.UNSUPPORTED_MEDIA_TYPE));
        assertThat(response.getBody().getError(), is(UNSUPPORTED_MEDIA_TYPE));
    }

}