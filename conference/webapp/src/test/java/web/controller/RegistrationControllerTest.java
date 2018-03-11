package web.controller;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.Filter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import domain.model.Role;
import domain.model.User;
import lombok.extern.log4j.Log4j;
import service.businesslogic.api.ContactTypeService;
import service.businesslogic.api.UserInfoService;
import service.businesslogic.api.UserService;
import service.businesslogic.dto.RegistrationDto;
import service.businesslogic.exception.EmailAlreadyExistsException;
import service.businesslogic.exception.PasswordMismatchException;
import web.config.TestConfig;
import web.config.WebMvcConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class, WebMvcConfig.class})
@WebAppConfiguration
@Log4j
public class RegistrationControllerTest {

    private static final String REGISTER_USER_URL = "/registration";
    private MockMvc mockMvc;
    private RegistrationDto correctRegistrationDto;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private Filter springSecurityFilterChain;

    @Autowired
    private UserService userService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private ContactTypeService contactTypeService;

    @Autowired
    private UserController userController;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        correctRegistrationDto = setupCorrectRegistrationDto();
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilter(springSecurityFilterChain)
                .apply(springSecurity())
                .build();

        when(passwordEncoder.encode(anyString()))
                .then((Answer<String>) invocation -> {
                    Object[] args = invocation.getArguments();
                    return (String) args[0];
                });
    }

    @After
    public void after() {
        Mockito.reset(userService, userInfoService, contactTypeService);
    }

    @Test
    public void tooLongPasswordTest() {
        correctRegistrationDto.setPassword(createStringWithLength(31));
        checkForBadRequest(REGISTER_USER_URL, RequestMethod.POST, correctRegistrationDto);
    }

    @Test
    public void withoutDotEmailTest() {
        correctRegistrationDto.setEmail("withoutDot@com");
        checkForBadRequest(REGISTER_USER_URL, RequestMethod.POST, correctRegistrationDto);
    }

    @Test
    public void toLongFirstNameTest() {
        correctRegistrationDto.setFirstName(createStringWithLength(57));
        checkForBadRequest(REGISTER_USER_URL, RequestMethod.POST, correctRegistrationDto);
    }

    @Test
    public void withoutAtCharacterEmailTest() {
        correctRegistrationDto.setEmail("withoutAtCharacter.com");
        checkForBadRequest(REGISTER_USER_URL, RequestMethod.POST, correctRegistrationDto);
    }

    @Test
    public void nullEmailTest() {
        correctRegistrationDto.setEmail(null);
        checkForBadRequest(REGISTER_USER_URL, RequestMethod.POST, correctRegistrationDto);
    }

    @Test
    public void emptyEmailTest() {
        correctRegistrationDto.setEmail("");
        checkForBadRequest(REGISTER_USER_URL, RequestMethod.POST, correctRegistrationDto);
    }

    @Test
    public void nullFirstNameTest() {
        correctRegistrationDto.setFirstName(null);
        checkForBadRequest(REGISTER_USER_URL, RequestMethod.POST, correctRegistrationDto);
    }

    @Test
    public void toShortFirstNameTest() {
        correctRegistrationDto.setFirstName("");
        checkForBadRequest(REGISTER_USER_URL, RequestMethod.POST, correctRegistrationDto);
    }

    @Test
    public void nullLastNameTest() {
        correctRegistrationDto.setLastName(null);
        checkForBadRequest(REGISTER_USER_URL, RequestMethod.POST, correctRegistrationDto);
    }

    @Test
    public void tooShortLastNameTest() {
        correctRegistrationDto.setLastName("");
        checkForBadRequest(REGISTER_USER_URL, RequestMethod.POST, correctRegistrationDto);
    }

    @Test
    public void tooLongLastNameTest() {
        correctRegistrationDto.setLastName(createStringWithLength(57));
        checkForBadRequest(REGISTER_USER_URL, RequestMethod.POST, correctRegistrationDto);
    }

    @Test
    public void nullPasswordTest() {
        correctRegistrationDto.setPassword(null);
        checkForBadRequest(REGISTER_USER_URL, RequestMethod.POST, correctRegistrationDto);
    }

    @Test
    public void tooShortPasswordTest() {
        correctRegistrationDto.setPassword("");
        checkForBadRequest(REGISTER_USER_URL, RequestMethod.POST, correctRegistrationDto);
    }

    @Test
    public void nullPasswordConfirmationTest() {
        correctRegistrationDto.setConfirm(null);
        checkForBadRequest(REGISTER_USER_URL, RequestMethod.POST, correctRegistrationDto);
    }

    @Test
    public void tooShortPasswordConfirmationTest() {
        correctRegistrationDto.setConfirm("");
        checkForBadRequest(REGISTER_USER_URL, RequestMethod.POST, correctRegistrationDto);
    }

    @Test
    public void tooLongPasswordConfirmationTest() {
        correctRegistrationDto.setConfirm(createStringWithLength(31));
        checkForBadRequest(REGISTER_USER_URL, RequestMethod.POST, correctRegistrationDto);
    }

    @Test
    public void correctRegistrationTest() throws Exception {
        performRegistration(REGISTER_USER_URL, HttpStatus.ACCEPTED.value());
    }

    @Test
    public void alreadyRegisteredEmailTest() throws Exception {
        String alreadyRegisteredEmail = "registered@gmail.com";
        correctRegistrationDto.setEmail(alreadyRegisteredEmail);
        correctRegistrationDto.setUserStatus(User.UserStatus.UNCONFIRMED);
        correctRegistrationDto.setRoleName(Role.ROLE_SPEAKER);
        doThrow(new EmailAlreadyExistsException("email_already_exists")).
                when(userService).checkUserRegistration(correctRegistrationDto);
        performRegistration(REGISTER_USER_URL, HttpStatus.CONFLICT.value());
    }

    @Test
    public void unconfirmedPasswordTest() {
        correctRegistrationDto.setConfirm("777777");
        correctRegistrationDto.setUserStatus(User.UserStatus.UNCONFIRMED);
        correctRegistrationDto.setRoleName(Role.ROLE_SPEAKER);
        doThrow(new PasswordMismatchException("email_already_exists")).
                when(userService).checkUserRegistration(correctRegistrationDto);
        checkForBadRequest(REGISTER_USER_URL, RequestMethod.POST, correctRegistrationDto);
    }

    private String createStringWithLength(int length) {
        StringBuilder builder = new StringBuilder();
        for (int index = 0; index < length; index++) {
            builder.append("a");
        }
        return builder.toString();
    }

    private void performRegistration(String api, int expectedStatus) throws Exception {
        mockMvc.perform(post(api)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(correctRegistrationDto))
        ).andExpect(status().is(expectedStatus));
    }

    private void checkForBadRequest(String uri, RequestMethod method, Object dto) {
        try {
            if (method == RequestMethod.GET) {
                mockMvc.perform(get(uri)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(convertObjectToJsonBytes(dto))
                ).andExpect(status().isBadRequest());
            } else if (method == RequestMethod.POST) {
                mockMvc.perform(post(uri)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(convertObjectToJsonBytes(dto))
                ).andExpect(status().isBadRequest());
            }
        } catch (Exception e) {
            log.info(e);
        }
    }

    private byte[] convertObjectToJsonBytes(Object object) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

    private RegistrationDto setupCorrectRegistrationDto() {
        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setPassword("123456");
        registrationDto.setLastName("Ivanova");
        registrationDto.setFirstName("Olya");
        registrationDto.setConfirm("123456");
        String uniqueEmail = "ivanova@gmail.com";
        registrationDto.setEmail(uniqueEmail);
        return registrationDto;
    }
}