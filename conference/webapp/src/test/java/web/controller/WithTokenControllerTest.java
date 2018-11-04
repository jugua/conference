package web.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyObject;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static web.util.TestData.speaker;

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

import domain.model.User;
import domain.model.VerificationToken;
import domain.repository.VerificationTokenRepository;
import service.businesslogic.api.UserService;
import service.businesslogic.impl.VerificationTokenService;
import web.config.TestConfig;
import web.config.WebMvcConfig;
import web.security.AuthenticationFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class, WebMvcConfig.class})
@WebAppConfiguration
public abstract class WithTokenControllerTest {

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
        user = speaker();

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

    public void testForCorrectToken(VerificationToken correctToken, String correctUrl) throws Exception {
        when(tokenService.findTokenBy(correctToken.getToken())).thenReturn(correctToken);

        mockMvc.perform(get(correctUrl)).andExpect(status().isOk());
    }

    public void testForExpiredToken(VerificationToken correctToken, String correctUrl) throws Exception {
        when(tokenService.findTokenBy(correctToken.getToken())).thenReturn(correctToken);

        mockMvc.perform(get(correctUrl)).andExpect(status().isGone());

        verify(userService, never()).updateUser(any());
    }

    public void testForWrongToken(String baseUrl) throws Exception {
        String wrongToken = "wrongToken";
        when(tokenService.findTokenBy(wrongToken)).thenReturn(null);

        mockMvc.perform(get(baseUrl + wrongToken)).andExpect(status().isBadRequest());

        verify(userService, never()).updateUser(anyObject());
    }

    public void testForUpdatingSecurityContext(User user) {
        Authentication expectedAuthentication = getAuthentication(user);
        verify(securityContext).setAuthentication(expectedAuthentication);
    }

    public VerificationToken createToken() {
        return VerificationToken.of(user, VerificationToken.TokenType.CONFIRMATION);
    }

    private Authentication getAuthentication(User user) {
        return AuthenticationFactory.createAuthentication(user);
    }
}
