package ua.rd.cm.web.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import ua.rd.cm.config.WebMvcConfig;
import ua.rd.cm.config.WebTestConfig;
import ua.rd.cm.domain.User;
import ua.rd.cm.services.UserService;
import ua.rd.cm.dto.SettingsDto;

import java.security.Principal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebTestConfig.class, WebMvcConfig.class, })
@WebAppConfiguration
public class ChangePasswordControllerTest {
    public static final String API_USER_CURRENT_PASSWORD = "/api/user/current/password";
    private MockMvc mockMvc;
    private SettingsDto settingsDto;

    @Autowired
    private SettingsController settingsController;

    @Autowired
    private UserService userService;

    private User user;
    private Principal principal;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(settingsController).build();
        user = new User();
        user.setId(1L);
        user.setFirstName("Olya");
        user.setLastName("Ivanova");
        user.setEmail("ivanova@gmail.com");
        user.setPassword("111111");

        settingsDto = setupCorrectSettingsDto();
        principal = user::getEmail;
        when(userService.getByEmail(user.getEmail())).thenReturn(user);
    }

    @Test
    public void correctChangePasswordTest() throws Exception {
        when(userService.isAuthenticated(any(User.class), anyString())).
                thenReturn(true);
        mockMvc.perform(post(API_USER_CURRENT_PASSWORD)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(settingsDto))
                .principal(principal)
        ).andExpect(status().isOk());
    }

    @Test
    public void unauthorizedChangePasswordTest() throws Exception {
        mockMvc.perform(post(API_USER_CURRENT_PASSWORD)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(settingsDto))
        ).andExpect(status().isUnauthorized());
    }

    @Test
    public void unconfirmedPasswordTest() throws Exception {
        settingsDto.setConfirmedNewPassword("123456789");
        mockMvc.perform(post(API_USER_CURRENT_PASSWORD)
                .principal(principal)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(settingsDto))
        ).andExpect(status().isForbidden());
    }

    @Test
    public void nullCurrentPasswordTest() throws Exception {
        settingsDto.setCurrentPassword(null);
        checkForBadRequest(principal);
    }

    @Test
    public void nullNewPasswordTest() throws Exception {
        settingsDto.setNewPassword(null);
        checkForBadRequest(principal);
    }

    @Test
    public void tooSmallNewPasswordTest() throws Exception {
        settingsDto.setNewPassword(createStringWithLength(5));
        checkForBadRequest(principal);
    }

    @Test
    public void tooLongNewPasswordTest() throws Exception {
        settingsDto.setNewPassword(createStringWithLength(31));
        checkForBadRequest(principal);
    }

    @Test
    public void nullNewConfirmedPasswordTest() throws Exception {
        settingsDto.setConfirmedNewPassword(null);
        checkForBadRequest(principal);
    }

    @Test
    public void tooSmallNewConfirmedPasswordTest() throws Exception {
        settingsDto.setConfirmedNewPassword(createStringWithLength(5));
        checkForBadRequest(principal);
    }

    @Test
    public void tooLongNewConfirmedPasswordTest() throws Exception {
        settingsDto.setConfirmedNewPassword(createStringWithLength(31));
        checkForBadRequest(principal);
    }

    private byte[] convertObjectToJsonBytes(Object object) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

    private String createStringWithLength(int length) {
        StringBuilder builder = new StringBuilder();
        for (int index = 0; index < length; index++) {
            builder.append("a");
        }
        return builder.toString();
    }

    private SettingsDto setupCorrectSettingsDto() {
        SettingsDto settingsDto = new SettingsDto();
        settingsDto.setCurrentPassword("111111");
        settingsDto.setNewPassword("123456");
        settingsDto.setConfirmedNewPassword("123456");
        return settingsDto;
    }

    private void checkForBadRequest(Principal principal) throws Exception {
        mockMvc.perform(post(API_USER_CURRENT_PASSWORD)
                .principal(principal)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(settingsDto))
        ).andExpect(status().isBadRequest());
    }


}
