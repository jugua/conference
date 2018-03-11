package web.controller;

import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static web.util.TestData.ORGANISER_EMAIL;
import static web.util.TestData.speaker;

import java.util.Arrays;
import java.util.List;

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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import domain.model.Contact;
import domain.model.ContactType;
import domain.model.Role;
import domain.model.User;
import domain.model.UserInfo;
import lombok.extern.log4j.Log4j;
import service.businesslogic.api.ContactTypeService;
import service.businesslogic.api.UserInfoService;
import service.businesslogic.api.UserService;
import service.businesslogic.dto.RegistrationDto;
import service.businesslogic.dto.UserInfoDto;
import service.businesslogic.exception.ResourceNotFoundException;
import service.businesslogic.exception.WrongRoleException;
import web.config.TestConfig;
import web.config.WebMvcConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class, WebMvcConfig.class})
@WebAppConfiguration
@Log4j
public class UserControllerTest {

    public static final String USER_URL = "/user";
    public static final String USER_CREATE_URL = "/user/registerByAdmin";

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

        when(passwordEncoder.encode(anyString())).then(
                (Answer<String>) invocation -> {
                    Object[] args = invocation.getArguments();
                    return (String) args[0];
                });
    }

    @After
    public void after() {
        Mockito.reset(userService, userInfoService, contactTypeService);
    }

    @Test
    @WithMockUser(roles = Role.ADMIN)
    public void correctRegistrationNewOrganiserByAdmin() throws Exception {
        correctRegistrationDto.setRoleName(Role.ROLE_ORGANISER);
        performRegistration(USER_CREATE_URL, HttpStatus.ACCEPTED.value());
    }

    @Test
    @WithMockUser(roles = Role.ADMIN)
    public void correctRegistrationNewSpeakerByAdmin() throws Exception {
        correctRegistrationDto.setRoleName(Role.ROLE_SPEAKER);
        performRegistration(USER_CREATE_URL, HttpStatus.ACCEPTED.value());
    }

    @Test
    @WithMockUser(roles = Role.ADMIN)
    public void registrationNewAdminByAdmin() throws Exception {
        correctRegistrationDto.setRoleName(Role.ROLE_ADMIN);
        doThrow(new WrongRoleException("wrong_role_name")).
                when(userService).checkUserRegistrationByAdmin(correctRegistrationDto);
        performRegistration(USER_CREATE_URL, HttpStatus.FORBIDDEN.value());
    }

    @Test
    @WithMockUser(roles = Role.ORGANISER)
    public void registrationNewUserByAdminAsOrganiser() throws Exception {
        correctRegistrationDto.setRoleName(Role.ROLE_SPEAKER);
        performRegistration(USER_CREATE_URL, HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    @WithMockUser(roles = Role.SPEAKER)
    public void registrationNewUserByAdminAsSpeaker() throws Exception {
        correctRegistrationDto.setRoleName(Role.ROLE_SPEAKER);
        performRegistration(USER_CREATE_URL, HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    public void registrationNewUserByAdminWithoutRole() throws Exception {
        correctRegistrationDto.setRoleName(Role.ROLE_SPEAKER);
        performRegistration(USER_CREATE_URL, HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    @WithMockUser(username = ORGANISER_EMAIL, roles = Role.ORGANISER)
    public void getUserById() throws Exception {
        UserInfoDto user = new UserInfoDto();
        when(userService.getUserDtoById(anyLong())).thenReturn(user);
        mockMvc.perform(prepareGetRequest(USER_URL + "/" + 1)
        ).andExpect(status().isOk());
    }

    @Test
    public void incorrectGetUserById() throws Exception {
        User user = speaker();
        when(userService.find(1L)).thenReturn(user);
        mockMvc.perform(prepareGetRequest(USER_URL + "/" + 1)).
                andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = ORGANISER_EMAIL, roles = Role.ORGANISER)
    public void notFoundUserById() throws Exception {

        when(userService.getUserDtoById(1L)).thenThrow(ResourceNotFoundException.class);
        mockMvc.perform(prepareGetRequest(USER_URL + "/" + 1)).
                andExpect(status().isNotFound());
    }

    private void performRegistration(String api, int expectedStatus) throws Exception {
        mockMvc.perform(post(api)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(correctRegistrationDto))
        ).andExpect(status().is(expectedStatus));
    }

    private MockHttpServletRequestBuilder prepareGetRequest(String uri) {
        return MockMvcRequestBuilders.get(uri)
                .contentType(MediaType.APPLICATION_JSON_UTF8);
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

    protected UserInfo userInfo() {
        ContactType contactType = new ContactType(1L, "LinkedIn");
        ContactType contactType2 = new ContactType(2L, "Twitter");
        ContactType contactType3 = new ContactType(3L, "Facebook");
        ContactType contactType4 = new ContactType(4L, "Blog");

        when(contactTypeService.findByName(anyString()))
                .thenReturn(contactType)
                .thenReturn(contactType2)
                .thenReturn(contactType3)
                .thenReturn(contactType4);

        List<Contact> contacts = Arrays.asList(
                new Contact(1L, "url1", contactType),
                new Contact(2L, "url2", contactType2),
                new Contact(3L, "url3", contactType3),
                new Contact(4L, "url4", contactType4));

        UserInfo userInfo = new UserInfo();
        userInfo.setId(1L);
        userInfo.setShortBio("bio");
        userInfo.setJobTitle("job");
        userInfo.setPastConference("pastConference");
        userInfo.setCompany("EPAM");
        userInfo.setContacts(contacts);
        userInfo.setAdditionalInfo("addInfo");
        return userInfo;
    }
}