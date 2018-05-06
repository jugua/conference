package web.controller;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import domain.model.VerificationToken;
import service.infrastructure.mail.MailService;
import service.infrastructure.mail.preparator.OldEmailMessagePreparator;
import web.config.TestConfig;
import web.config.WebMvcConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class, WebMvcConfig.class})
@WebAppConfiguration
public class ConfirmationControllerTest extends WithTokenControllerTest {
    private static final String REGISTRATION_CONFIRM_REQUEST = "/confirmation/registrationConfirm/";

    @Autowired
    private MailService mailService;

    @Test
    public void testConfirmRegistrationWithWrongToken() throws Exception {
        testForWrongToken(REGISTRATION_CONFIRM_REQUEST);
    }

    @Test
    public void testConfirmRegistrationWithExpiredToken() throws Exception {
        VerificationToken correctToken = createToken();
        correctToken.expire();
        String correctUrl = REGISTRATION_CONFIRM_REQUEST + correctToken.getToken();
        testForExpiredToken(correctToken, correctUrl, VerificationToken.TokenType.CONFIRMATION);
    }

    @Test
    public void testConfirmRegistrationWithCorrectToken() throws Exception {
        VerificationToken correctToken = createToken();
        String correctUrl = REGISTRATION_CONFIRM_REQUEST + correctToken.getToken();
        testForCorrectToken(correctToken, correctUrl,
                VerificationToken.TokenType.CONFIRMATION);
        testForUpdatingSecurityContext(user);
        verify(userService).confirm(user);
    }

    @Test
    @DirtiesContext
    public void successfulEmailConfirmationShouldUpdateUserProfile() throws Exception {
        VerificationToken correctToken = VerificationToken.createChangeEmailToken(
                user, VerificationToken.TokenType.CONFIRMATION, "newEmail@gmail.com");
        VerificationToken.TokenType tokenType = VerificationToken.TokenType.CHANGING_EMAIL;
        correctToken.setType(tokenType);
        String correctUrl = "/confirmation/newEmailConfirm/" + correctToken.getToken();
        testForCorrectToken(correctToken, correctUrl, tokenType);
        testForUpdatingSecurityContext(user);
        verify(userService).updateUserProfile(user);
    }

    @Test
    @DirtiesContext
    public void successfulEmailConfirmationShouldSendEmailToUser() throws Exception {
        String oldEmail = user.getEmail();
        VerificationToken correctToken = VerificationToken.createChangeEmailToken(
                user, VerificationToken.TokenType.CONFIRMATION, "newEmail@gmail.com");

        VerificationToken.TokenType tokenType = VerificationToken.TokenType.CHANGING_EMAIL;
        correctToken.setType(tokenType);
        String correctUrl = "/confirmation/newEmailConfirm/" + correctToken.getToken();
        testForCorrectToken(correctToken, correctUrl, tokenType);
        testForUpdatingSecurityContext(user);

        verify(mailService).sendEmail(user, new OldEmailMessagePreparator(oldEmail));
    }

    @Test
    public void testConfirmNewEmailWithWrongToken() throws Exception {
        String url = "/confirmation/newEmailConfirm/";
        testForWrongToken(url);
    }

    @Test
    public void testConfirmNewEmailWithExpiredToken() throws Exception {
        VerificationToken correctToken = createToken();
        correctToken.expire();
        String correctUrl = "/confirmation/newEmailConfirm/" + correctToken.getToken();
        testForExpiredToken(correctToken, correctUrl,
                VerificationToken.TokenType.CHANGING_EMAIL);
    }
}