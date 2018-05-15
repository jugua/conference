package web.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import domain.model.User;
import domain.model.VerificationToken;
import service.businesslogic.dto.ConfirmPasswordPair;

public class ForgotPasswordControllerTest extends WithTokenControllerTest {
    private static final String WRONG_JSON_WITHOUT_MAIL = "{}";
    private static final String WRONG_JSON_WITH_WRONG_MAIL = "{\"mail\":\"wrong@email\"}";
    private static final String JSON_WITH_CORRECT_MAIL = "{ \"mail\": \"user@gmail.com\"  }";

    private static final String FORGOT_PASSWORD_REQUEST = "/forgotPasswordPage/forgotPassword/";

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testForgotPasswordWithBadRequestWithoutMail() throws Exception {
        mockMvc.perform(post(FORGOT_PASSWORD_REQUEST).contentType(MediaType.APPLICATION_JSON)
                .content(WRONG_JSON_WITHOUT_MAIL))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testForgotPasswordWithBadRequestWithWrongEmail() throws Exception {
        mockMvc.perform(post(FORGOT_PASSWORD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .content(WRONG_JSON_WITH_WRONG_MAIL))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testForgotPasswordWithCorrectRequest()
            throws Exception {
        when(userService.isEmailExist(anyString())).thenReturn(true);

        mockMvc.perform(post(FORGOT_PASSWORD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON_WITH_CORRECT_MAIL))
                .andExpect(status().isOk());
    }

    @Test
    public void testChangePasswordWithCorrectToken() throws Exception {
        VerificationToken correctToken = createToken();
        VerificationToken.TokenType tokenType = VerificationToken.TokenType.FORGOT_PASS;
        correctToken.setType(tokenType);
        String correctUrl = "/forgotPasswordPage/changePassword/" + correctToken.getToken();
        testForCorrectToken(correctToken, correctUrl);
        testForUpdatingSecurityContext(user);
    }

    @Test
    public void testChangePasswordWithExpiredToken() throws Exception {
        VerificationToken correctToken = createToken();
        correctToken.expire();
        String correctUrl = "/forgotPasswordPage/changePassword/" + correctToken.getToken();
        testForExpiredToken(correctToken, correctUrl);
    }

    @Test
    public void testChangePasswordWithWrongToken() throws Exception {
        String url = "/forgotPasswordPage/changePassword/";
        testForWrongToken(url);
    }

    @Test
    public void testChangePasswordAndUpdatingUserProfileWithConfirmedPassword() throws Exception {
        VerificationToken correctToken = createToken();
        String correctUrl = "/forgotPasswordPage/changePassword/" + correctToken.getToken();
        String correctPassword = "password";
        ConfirmPasswordPair dto = new ConfirmPasswordPair(correctPassword);
        dto.setConfirm(correctPassword);
        dto.setPassword(correctPassword);

        when(passwordEncoder.encode(anyString())).thenReturn(user.getPassword());
        when(tokenService.findTokenBy(correctToken.getToken())).thenReturn(correctToken);
        mockMvc.perform(post(correctUrl)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(dto)))
                .andExpect(status().isOk());
        verify(userService).updateUserProfile(user);
    }

    @Test
    public void testChangePasswordWithUnconfirmedPassword() throws Exception {
        VerificationToken correctToken = createToken();
        String correctUrl = "/forgotPasswordPage/changePassword/" + correctToken.getToken();
        String correctPassword = "password";
        ConfirmPasswordPair dto = new ConfirmPasswordPair(correctPassword);
        dto.setConfirm(correctPassword);
        dto.setPassword("unconfirmed password!!!");
        when(tokenService.findTokenBy(correctToken.getToken())).thenReturn(correctToken);
        mockMvc.perform(post(correctUrl)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(dto)))
                .andExpect(status().isBadRequest());
        verify(userService, never()).updateUserProfile(any(User.class));
    }

    private byte[] convertObjectToJsonBytes(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }
}