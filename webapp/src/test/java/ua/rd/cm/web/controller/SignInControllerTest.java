package ua.rd.cm.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ua.rd.cm.config.TestSecurityConfig;
import ua.rd.cm.config.WebMvcConfig;
import ua.rd.cm.config.WebTestConfig;
import ua.rd.cm.services.UserService;

import javax.servlet.Filter;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebTestConfig.class, WebMvcConfig.class, TestSecurityConfig.class })
@WebAppConfiguration
public class SignInControllerTest {
    private static final String WRONG_JSON_WITHOUT_MAIL = "{}";
    private static final String WRONG_JSON_WITH_WRONG_MAIL = "{\"mail\":\"wrong@email\"}";
    private static final String JSON_WITH_CORRECT_MAIL = "{ \"mail\": \"user@gmail.com\"  }";

    private static final String FORGOT_PASSWORD_REQUEST = "/api/forgot-password";

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private Filter springSecurityFilterChain;

    @Autowired
    private UserService userService;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilter(springSecurityFilterChain)
                .apply(springSecurity())
                .build();
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
        mockMvc.perform(post(FORGOT_PASSWORD_REQUEST).contentType(MediaType.APPLICATION_JSON)
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
}