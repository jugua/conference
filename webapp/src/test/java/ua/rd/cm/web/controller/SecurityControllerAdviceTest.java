package ua.rd.cm.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ua.rd.cm.web.controller.dto.MessageDto;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

public class SecurityControllerAdviceTest {
    private SecurityControllerAdvice controller;

    @Before
    public void setUp() {
        controller = new SecurityControllerAdvice();
    }

    @Test
    public void handleAccessDeniedReturnNonNullResponse() throws Exception {
        assertNotNull(controller.handleAccessDenied());
    }

    @Test
    public void handleAccessDeniedReturnNonNullMessage() throws Exception {
        ResponseEntity<MessageDto> response = controller.handleAccessDenied();

        assertNotNull(response.getBody());
    }

    @Test
    public void handleAccessDeniedHasCorrectStatusCode() throws Exception {
        ResponseEntity<MessageDto> response = controller.handleAccessDenied();
        assertThat(response.getStatusCode(), is(HttpStatus.UNAUTHORIZED));
    }

    @Test
    public void handleAccessDeniedHasCorrectMessage() throws Exception {
        ResponseEntity<MessageDto> response = controller.handleAccessDenied();
        assertThat(response.getBody().getError(), is(SecurityControllerAdvice.UNAUTHORIZED_MSG));
    }
}