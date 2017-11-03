package ua.rd.cm.web.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import ua.rd.cm.domain.User;
import ua.rd.cm.domain.VerificationToken;
import ua.rd.cm.dto.NewPasswordDto;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SignInControllerTest extends WithTokenControllerTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testChangePasswordWithCorrectToken() throws Exception{
        VerificationToken correctToken = createToken();
        VerificationToken.TokenType tokenType = VerificationToken.TokenType.FORGOT_PASS;
        correctToken.setType(tokenType);
        String correctUrl = "/changePassword/" + correctToken.getToken();
        testForCorrectToken(correctToken, correctUrl, tokenType);
        testForUpdatingSecurityContext(user);
    }

    @Test
    public void testChangePasswordWithExpiredToken() throws Exception{
        VerificationToken correctToken = createToken();
        correctToken.setStatus(VerificationToken.TokenStatus.EXPIRED);
        String correctUrl = "/changePassword/" + correctToken.getToken();
        testForExpiredToken(correctToken, correctUrl,
                VerificationToken.TokenType.FORGOT_PASS);
    }

    @Test
    public void testChangePasswordWithWrongToken() throws Exception{
        String url = "/changePassword/";
        testForWrongToken(url);
    }

    @Test
    public void testChangePasswordAndUpdatingUserProfileWithConfirmedPassword() throws Exception{
        VerificationToken correctToken = createToken();
        String correctUrl = "/changePassword/" +correctToken.getToken();
        String correctPassword = "password";
        NewPasswordDto dto = new NewPasswordDto(correctPassword);
        dto.setConfirm(correctPassword);
        dto.setPassword(correctPassword);

        when(passwordEncoder.encode(anyString())).thenReturn(user.getPassword());
        when(tokenService.getToken(correctToken.getToken())).thenReturn(correctToken);
        mockMvc.perform(post(correctUrl)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(dto)))
                .andExpect(status().isOk());
        verify(userService).updateUserProfile(user);
    }

    @Test
    public void testChangePasswordWithUnconfirmedPassword() throws Exception {
        VerificationToken correctToken = createToken();
        String correctUrl = "/changePassword/" +correctToken.getToken();
        String correctPassword = "password";
        NewPasswordDto dto = new NewPasswordDto(correctPassword);
        dto.setConfirm(correctPassword);
        dto.setPassword("unconfirmed password!!!");
        when(tokenService.getToken(correctToken.getToken())).thenReturn(correctToken);
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
