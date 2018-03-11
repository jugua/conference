package web.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import domain.model.VerificationToken;
import web.config.TestConfig;
import web.config.WebMvcConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class, WebMvcConfig.class})
@WebAppConfiguration
public class ConfirmationControllerTest extends WithTokenControllerTest {
    private static final String REGISTRATION_CONFIRM_REQUEST = "/confirmation/registrationConfirm/";

    @Test
    public void testConfirmRegistrationWithWrongToken() throws Exception {
        testForWrongToken(REGISTRATION_CONFIRM_REQUEST);
    }

    @Test
    public void testConfirmRegistrationWithExpiredToken() throws Exception {
        VerificationToken correctToken = createToken();
        correctToken.setStatus(VerificationToken.TokenStatus.EXPIRED);
        String correctUrl = REGISTRATION_CONFIRM_REQUEST + correctToken.getToken();
        testForExpiredToken(correctToken, correctUrl,
                VerificationToken.TokenType.CONFIRMATION);
    }

    @Test
    public void testConfirmRegistrationWithCorrectToken() throws Exception {
        VerificationToken correctToken = createToken();
        String correctUrl = REGISTRATION_CONFIRM_REQUEST + correctToken.getToken();
        testForCorrectToken(correctToken, correctUrl,
                VerificationToken.TokenType.CONFIRMATION);
        testForUpdatingSecurityContext(user);
        verify(userService).updateUserProfile(any());
    }

    @Test
    public void testConfirmNewEmailWithCorrectToken() throws Exception {
        VerificationToken correctToken = createToken();
        VerificationToken.TokenType tokenType = VerificationToken.TokenType.CHANGING_EMAIL;
        correctToken.setType(tokenType);
        String correctUrl = "/confirmation/newEmailConfirm/" + correctToken.getToken();
        when(tokenService.getEmail(correctToken.getToken())).thenReturn(user.getEmail());
        testForCorrectToken(correctToken, correctUrl, tokenType);
        testForUpdatingSecurityContext(user);
        verify(userService).updateUserProfile(any());
    }

    @Test
    public void testConfirmNewEmailWithWrongToken() throws Exception {
        String url = "/confirmation/newEmailConfirm/";
        testForWrongToken(url);
    }

    @Test
    public void testConfirmNewEmailWithExpiredToken() throws Exception {
        VerificationToken correctToken = createToken();
        correctToken.setStatus(VerificationToken.TokenStatus.EXPIRED);
        String correctUrl = "/confirmation/newEmailConfirm/" + correctToken.getToken();
        testForExpiredToken(correctToken, correctUrl,
                VerificationToken.TokenType.CHANGING_EMAIL);
    }
}