package ua.rd.cm.web.controller;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.Filter;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
import ua.rd.cm.repository.VerificationTokenRepository;
import ua.rd.cm.services.businesslogic.UserService;
import ua.rd.cm.services.businesslogic.impl.VerificationTokenService;
import ua.rd.cm.web.security.AuthenticationFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebTestConfig.class, WebMvcConfig.class, TestSecurityConfig.class})
@WebAppConfiguration
public abstract class WithTokenControllerTest extends TestUtil {

    protected User user;

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
    protected UserService userService;

    @Autowired
    protected VerificationTokenService tokenService;

    protected VerificationTokenService realTokenService;

    protected MockMvc mockMvc;

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
        mockMvc.perform(get(baseUrl + wrongToken))
                .andExpect(status().isBadRequest());

        verify(tokenService).isTokenValid(any(VerificationToken.class),
                any(VerificationToken.TokenType.class));
        verify(tokenService, never()).isTokenExpired(anyObject());
        verify(userService, never()).updateUserProfile(anyObject());
    }

    public void testForUpdatingSecurityContext(User user) {
        Authentication expectedAuthentication = getAuthentication(user);
        verify(securityContext).setAuthentication(expectedAuthentication);
    }

    public VerificationToken createToken() {
        return realTokenService.createToken(user, VerificationToken.TokenType.CONFIRMATION);
    }

    private Authentication getAuthentication(User user) {
        return AuthenticationFactory.createAuthentication(user);
    }
}
