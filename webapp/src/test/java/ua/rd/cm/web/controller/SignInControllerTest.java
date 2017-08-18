package ua.rd.cm.web.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ua.rd.cm.config.TestSecurityConfig;
import ua.rd.cm.config.WebMvcConfig;
import ua.rd.cm.config.WebTestConfig;
import ua.rd.cm.domain.User;
import ua.rd.cm.domain.VerificationToken;
import ua.rd.cm.dto.NewPasswordDto;
import ua.rd.cm.repository.VerificationTokenRepository;
import ua.rd.cm.services.UserService;
import ua.rd.cm.services.VerificationTokenService;
import ua.rd.cm.web.security.AuthenticationFactory;

import javax.servlet.Filter;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebTestConfig.class, WebMvcConfig.class, TestSecurityConfig.class })
@WebAppConfiguration
public class SignInControllerTest extends TestUtil{
    private static final String WRONG_JSON_WITHOUT_MAIL = "{}";
    private static final String WRONG_JSON_WITH_WRONG_MAIL = "{\"mail\":\"wrong@email\"}";
    private static final String JSON_WITH_CORRECT_MAIL = "{ \"mail\": \"user@gmail.com\"  }";

    private static final String FORGOT_PASSWORD_REQUEST = "/api/forgot-password/";
    private static final String REGISTRATION_CONFIRM_REQUEST = "/api/registrationConfirm/";

    private User user;

    @Mock
    private VerificationTokenRepository tokenRepository;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private Filter springSecurityFilterChain;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private VerificationTokenService tokenService;

    private VerificationTokenService realTokenService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        realTokenService = new VerificationTokenService(tokenRepository);
        user = createUser();

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);


        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilter(springSecurityFilterChain)
                .apply(springSecurity())
                .build();
    }

    @After
    public void after() {
        Mockito.reset(tokenService, userService);
    }

    @Test
    public void testForgotPasswordWithBadRequestWithoutMail()
            throws Exception{
        mockMvc.perform(post(FORGOT_PASSWORD_REQUEST).contentType(MediaType.APPLICATION_JSON)
                .content(WRONG_JSON_WITHOUT_MAIL))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testForgotPasswordWithBadRequestWithWrongEmail()
            throws Exception{
        mockMvc.perform(post(FORGOT_PASSWORD_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(WRONG_JSON_WITH_WRONG_MAIL))
                        .andExpect(status().isBadRequest());
    }

    @Test
    public void testForgotPasswordWithCorrectRequest()
        throws Exception{
        when(userService.isEmailExist(anyString())).thenReturn(true);

        mockMvc.perform(post(FORGOT_PASSWORD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON_WITH_CORRECT_MAIL))
                .andExpect(status().isOk());
    }

    @Test
    public void testConfirmRegistrationWithWrongToken() throws Exception{
        String url = REGISTRATION_CONFIRM_REQUEST;
        testForWrongToken(url);
    }

    @Test
    public void testConfirmRegistrationWithExpiredToken() throws Exception{
        VerificationToken correctToken = createToken();
        correctToken.setStatus(VerificationToken.TokenStatus.EXPIRED);
        String correctUrl = REGISTRATION_CONFIRM_REQUEST + correctToken.getToken();
        testForExpiredToken(correctToken, correctUrl,
                            VerificationToken.TokenType.CONFIRMATION);
    }

    @Test
    public void testConfirmRegistrationWithCorrectToken() throws Exception{
        VerificationToken correctToken = createToken();
        String correctUrl = REGISTRATION_CONFIRM_REQUEST + correctToken.getToken();
        testForCorrectToken(correctToken, correctUrl,
                            VerificationToken.TokenType.CONFIRMATION);
        testForUpdatingSecurityContext(user);
        verify(userService).updateUserProfile(any());
    }

    @Test
    public void testConfirmNewEmailWithCorrectToken() throws Exception{
        VerificationToken correctToken = createToken();
        VerificationToken.TokenType tokenType = VerificationToken.TokenType.CHANGING_EMAIL;
        correctToken.setType(tokenType);
        String correctUrl = "/api/newEmailConfirm/" + correctToken.getToken();
        when(tokenService.getEmail(correctToken.getToken())).thenReturn(user.getEmail());
        testForCorrectToken(correctToken, correctUrl, tokenType);
        testForUpdatingSecurityContext(user);
        verify(userService).updateUserProfile(any());
    }

    @Test
    public void testConfirmNewEmailWithWrongToken() throws Exception{
        String url = "/api/newEmailConfirm/";
        testForWrongToken(url);
    }

    @Test
    public void testConfirmNewEmailWithExpiredToken() throws Exception{
        VerificationToken correctToken = createToken();
        correctToken.setStatus(VerificationToken.TokenStatus.EXPIRED);
        String correctUrl = "/api/newEmailConfirm/" + correctToken.getToken();
        testForExpiredToken(correctToken, correctUrl,
                            VerificationToken.TokenType.CHANGING_EMAIL);
    }

    @Test
    public void testChangePasswordWithCorrectToken() throws Exception{
        VerificationToken correctToken = createToken();
        VerificationToken.TokenType tokenType = VerificationToken.TokenType.FORGOT_PASS;
        correctToken.setType(tokenType);
        String correctUrl = "/api/forgotPassword/" + correctToken.getToken();
        testForCorrectToken(correctToken, correctUrl, tokenType);
        testForUpdatingSecurityContext(user);
    }

    @Test
    public void testChangePasswordWithExpiredToken() throws Exception{
        VerificationToken correctToken = createToken();
        correctToken.setStatus(VerificationToken.TokenStatus.EXPIRED);
        String correctUrl = "/api/forgotPassword/" + correctToken.getToken();
        testForExpiredToken(correctToken, correctUrl,
                            VerificationToken.TokenType.FORGOT_PASS);
    }

    @Test
    public void testChangePasswordWithWrongToken() throws Exception{
        String url = "/api/forgotPassword/";
        testForWrongToken(url);
    }

    @Test
    public void testChangePasswordAndUpdatingUserProfileWithConfirmedPassword() throws Exception{
        VerificationToken correctToken = createToken();
        String correctUrl = "/api/forgotPassword/" +correctToken.getToken();
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
        String correctUrl = "/api/forgotPassword/" +correctToken.getToken();
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

    public void testForCorrectToken(VerificationToken correctToken,
                                    String correctUrl,
                                    VerificationToken.TokenType type) throws Exception {
        when(tokenService.isTokenValid(correctToken, VerificationToken.TokenType.CONFIRMATION)).thenReturn(true);
        when(tokenService.isTokenValid(correctToken, VerificationToken.TokenType.CHANGING_EMAIL)).thenReturn(true);
        when(tokenService.isTokenValid(correctToken, VerificationToken.TokenType.FORGOT_PASS)).thenReturn(true);
        when(tokenService.isTokenExpired(correctToken)).thenReturn(false);
        when(tokenService.getToken(correctToken.getToken())).thenReturn(correctToken);

        mockMvc.perform(get(correctUrl))
                .andExpect(status().isOk());

        verify(tokenService).isTokenValid(correctToken, type);
        verify(tokenService).isTokenExpired(correctToken);
    }

    public void testForExpiredToken(VerificationToken correctToken,
                                    String correctUrl,
                                    VerificationToken.TokenType type) throws Exception {
        when(tokenService.isTokenValid(correctToken, VerificationToken.TokenType.CONFIRMATION)).thenReturn(true);
        when(tokenService.isTokenValid(correctToken, VerificationToken.TokenType.CHANGING_EMAIL)).thenReturn(true);
        when(tokenService.isTokenValid(correctToken, VerificationToken.TokenType.FORGOT_PASS)).thenReturn(true);
        when(tokenService.isTokenExpired(correctToken)).thenReturn(true);
        when(tokenService.getToken(correctToken.getToken())).thenReturn(correctToken);

        mockMvc.perform(get(correctUrl))
                .andExpect(status().isGone());

        verify(tokenService).isTokenValid(correctToken, type);
        verify(tokenService).isTokenExpired(correctToken);
        verify(userService, never()).updateUserProfile(any());
    }

    public void testForWrongToken(String baseUrl) throws Exception {
        String wrongToken = "gasf1";
        when(tokenService.isTokenValid(any(VerificationToken.class), any(VerificationToken.TokenType.class))).thenReturn(false);
        when(tokenService.getToken(anyString())).thenReturn(null);
        doReturn(null).when(tokenService).getToken(wrongToken);
        mockMvc.perform(get(baseUrl+wrongToken))
                .andExpect(status().isBadRequest());

        verify(tokenService).isTokenValid(  any(VerificationToken.class),
                any(VerificationToken.TokenType.class));
        verify(tokenService, never()).isTokenExpired(anyObject());
        verify(userService, never()).updateUserProfile(anyObject());
    }

    public void testForUpdatingSecurityContext(User user){
        Authentication expectedAuthentication = getAuthentication(user);
        verify(securityContext).setAuthentication(expectedAuthentication);
    }

    private VerificationToken createToken(){
        return realTokenService.createToken(user, VerificationToken.TokenType.CONFIRMATION);
    }

    private Authentication getAuthentication(User user){
        return AuthenticationFactory.createAuthentication(user);
    }

    private byte[] convertObjectToJsonBytes(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }
}