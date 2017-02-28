package ua.rd.cm.web.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
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
import ua.rd.cm.config.TestSecurityConfig;
import ua.rd.cm.config.WebMvcConfig;
import ua.rd.cm.config.WebTestConfig;
import ua.rd.cm.domain.ContactType;
import ua.rd.cm.domain.Role;
import ua.rd.cm.domain.User;
import ua.rd.cm.domain.UserInfo;
import ua.rd.cm.services.ContactTypeService;
import ua.rd.cm.services.UserInfoService;
import ua.rd.cm.services.UserService;
import ua.rd.cm.dto.RegistrationDto;
import ua.rd.cm.services.exception.EntityNotFoundException;
import ua.rd.cm.web.controller.dto.UserDto;

import javax.servlet.Filter;
import java.security.Principal;
import java.util.*;

import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebTestConfig.class, WebMvcConfig.class, TestSecurityConfig.class })
@WebAppConfiguration
public class UserControllerTest extends TestUtil{
    public static final String API_USER_CURRENT = "/api/user/current";
    public static final String API_USER = "/api/user";
    public static final String API_USER_CREATE = "/api/user/create";
    private MockMvc mockMvc;
    private RegistrationDto correctRegistrationDto;
    private UserDto correctUserDto;
    private String uniqueEmail = "ivanova@gmail.com";
    private String alreadyRegisteredEmail = "registered@gmail.com";

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
        correctUserDto = setupCorrectUserInfoDto();
        createSpeakerAndOrganiser(userService);
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilter(springSecurityFilterChain)
                .apply(springSecurity())
                .build();

        when(passwordEncoder.encode(anyString())).then(new Answer<String>() {
            @Override
            public String answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                return (String) args[0];
            }
        });
    }

    @After
    public void after() {
        Mockito.reset(userService, userInfoService, contactTypeService);
    }

    @Test
    public void correctRegistrationTest() throws Exception{
        performRegistration(API_USER, HttpStatus.ACCEPTED.value());
    }

    @Test
    public void alreadyRegisteredEmailTest() throws Exception{
        correctRegistrationDto.setEmail(alreadyRegisteredEmail);
        when(userService.isEmailExist(alreadyRegisteredEmail)).thenReturn(true);
        performRegistration(API_USER, HttpStatus.CONFLICT.value());
    }

    @Test
    @WithMockUser(roles = ADMIN_ROLE)
    public void correctRegistrationNewOrganiserByAdmin() throws Exception{
        correctRegistrationDto.setRoleName(Role.ORGANISER);
        performRegistration(API_USER_CREATE, HttpStatus.ACCEPTED.value());
    }

    @Test
    @WithMockUser(roles = ADMIN_ROLE)
    public void correctRegistrationNewSpeakerByAdmin() throws Exception{
        correctRegistrationDto.setRoleName(Role.SPEAKER);
        performRegistration(API_USER_CREATE, HttpStatus.ACCEPTED.value());
    }

    @Test
    @WithMockUser(roles = ADMIN_ROLE)
    public void registrationNewAdminByAdmin() throws Exception{
        correctRegistrationDto.setRoleName(Role.ADMIN);
        performRegistration(API_USER_CREATE, HttpStatus.FORBIDDEN.value());
    }

    @Test
    @WithMockUser(roles = ORGANISER_ROLE)
    public void registrationNewUserByAdminAsOrganiser() throws Exception{
        correctRegistrationDto.setRoleName(Role.SPEAKER);
        performRegistration(API_USER_CREATE, HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    @WithMockUser(roles = SPEAKER_ROLE)
    public void registrationNewUserByAdminAsSpeaker() throws Exception{
        correctRegistrationDto.setRoleName(Role.SPEAKER);
        performRegistration(API_USER_CREATE, HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    public void registrationNewUserByAdminWithoutRole() throws Exception{
        correctRegistrationDto.setRoleName(Role.SPEAKER);
        performRegistration(API_USER_CREATE, HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    public void unconfirmedPasswordTest(){
        correctRegistrationDto.setConfirm("777777");
        checkForBadRequest(API_USER, RequestMethod.POST, correctRegistrationDto);
    }

    @Test
    public void nullFirstNameTest(){
        correctRegistrationDto.setFirstName(null);
        checkForBadRequest(API_USER, RequestMethod.POST, correctRegistrationDto);
    }

    @Test
    public void toShortFirstNameTest(){
        correctRegistrationDto.setFirstName("");
        checkForBadRequest(API_USER, RequestMethod.POST, correctRegistrationDto);
    }

    @Test
    public void toLongFirstNameTest(){
        correctRegistrationDto.setFirstName(createStringWithLength(57));
        checkForBadRequest(API_USER, RequestMethod.POST, correctRegistrationDto);
    }

    @Test
    public void nullLastNameTest(){
        correctRegistrationDto.setLastName(null);
        checkForBadRequest(API_USER, RequestMethod.POST, correctRegistrationDto);
    }

    @Test
    public void tooShortLastNameTest(){
        correctRegistrationDto.setLastName("");
        checkForBadRequest(API_USER, RequestMethod.POST, correctRegistrationDto);
    }

    @Test
    public void tooLongLastNameTest(){
        correctRegistrationDto.setLastName(createStringWithLength(57));
        checkForBadRequest(API_USER, RequestMethod.POST, correctRegistrationDto);
    }

    @Test
    public void nullPasswordTest(){
        correctRegistrationDto.setPassword(null);
        checkForBadRequest(API_USER, RequestMethod.POST, correctRegistrationDto);
    }

    @Test
    public void tooShortPasswordTest(){
        correctRegistrationDto.setPassword("");
        checkForBadRequest(API_USER, RequestMethod.POST, correctRegistrationDto);
    }

    @Test
    public void tooLongPasswordTest(){
        correctRegistrationDto.setPassword(createStringWithLength(31));
        checkForBadRequest(API_USER, RequestMethod.POST, correctRegistrationDto);
    }

    @Test
    public void nullPasswordConfirmationTest(){
        correctRegistrationDto.setConfirm(null);
        checkForBadRequest(API_USER, RequestMethod.POST, correctRegistrationDto);
    }

    @Test
    public void tooShortPasswordConfirmationTest(){
        correctRegistrationDto.setConfirm("");
        checkForBadRequest(API_USER, RequestMethod.POST, correctRegistrationDto);
    }

    @Test
    public void tooLongPasswordConfirmationTest(){
        correctRegistrationDto.setConfirm(createStringWithLength(31));
        checkForBadRequest(API_USER, RequestMethod.POST, correctRegistrationDto);
    }

    @Test
    public void nullEmailTest(){
        correctRegistrationDto.setEmail(null);
        checkForBadRequest(API_USER, RequestMethod.POST, correctRegistrationDto);
    }

    @Test
    public void emptyEmailTest(){
        correctRegistrationDto.setEmail("");
        checkForBadRequest(API_USER, RequestMethod.POST, correctRegistrationDto);
    }

    @Test
    public void withoutAtCharacterEmailTest(){
        correctRegistrationDto.setEmail("withoutAtCharacter.com");
        checkForBadRequest(API_USER, RequestMethod.POST, correctRegistrationDto);
    }

    @Test
    public void withoutDotEmailTest(){
        correctRegistrationDto.setEmail("withoutDot@com");
        checkForBadRequest(API_USER, RequestMethod.POST, correctRegistrationDto);
    }

    @Test
    @WithMockUser(username = SPEAKER_EMAIL, roles = SPEAKER_ROLE)
    public void correctPrincipalGetCurrentUserTest() throws Exception{
        Role speaker = createSpeakerRole();
        UserInfo info = createUserInfo();
        User user = createUser(speaker, info);
        Principal correctPrincipal = () -> user.getEmail();

        when(userService.getByEmail(user.getEmail())).thenReturn(user);

        mockMvc.perform(get(API_USER_CURRENT)
                .principal(correctPrincipal)
        ).andExpect(status().isAccepted())
                .andExpect(jsonPath("fname", is(user.getFirstName())))
                .andExpect(jsonPath("lname", is(user.getLastName())))
                .andExpect(jsonPath("mail", is(user.getEmail())))
                .andExpect(jsonPath("bio", is(user.getUserInfo().getShortBio())))
                .andExpect(jsonPath("job", is(user.getUserInfo().getJobTitle())))
                .andExpect(jsonPath("past", is(user.getUserInfo().getPastConference())))
                .andExpect(jsonPath("photo", is("api/user/current/photo/" + user.getId())))
                .andExpect(jsonPath("info", is(user.getUserInfo().getAdditionalInfo())))
                .andExpect(jsonPath("linkedin", is(user.getUserInfo().getContacts().get(new ContactType(1L, "LinkedIn")))))
                .andExpect(jsonPath("twitter", is(user.getUserInfo().getContacts().get(new ContactType(2L, "Twitter")))))
                .andExpect(jsonPath("facebook", is(user.getUserInfo().getContacts().get(new ContactType(3L, "FaceBook")))))
                .andExpect(jsonPath("blog", is(user.getUserInfo().getContacts().get(new ContactType(4L, "Blog")))))
                .andExpect(jsonPath("roles[0]", is("ROLE_SPEAKER")));
    }

    @Test
    @WithMockUser(username = SPEAKER_EMAIL, roles = SPEAKER_ROLE)
    public void correctFillUserInfoTest() throws Exception{
        Role speaker = createSpeakerRole();
        UserInfo info = createUserInfo();
        User user = createUser(speaker, info);
        Principal correctPrincipal = () -> user.getEmail();

        when(userService.getByEmail(user.getEmail())).thenReturn(user);

        mockMvc.perform(post(API_USER_CURRENT)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(correctUserDto))
                .principal(correctPrincipal)
        ).andExpect(status().isOk());
    }

    @Test
    public void incorrectPrincipalFillInfoTest() throws Exception {
        mockMvc.perform(post(API_USER_CURRENT)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(correctUserDto))
        ).andExpect(status().isUnauthorized());
    }

    @Test
    public void nullBioTest() {
        correctUserDto.setUserInfoShortBio(null);
        checkForBadRequest(API_USER_CURRENT, RequestMethod.POST, correctUserDto);
    }

//    @Ignore
//    @Test
//    public void tooShortBioTest(){
//        correctUserDto.setUserInfoShortBio("");
//        checkForBadRequest(API_USER_CURRENT, RequestMethod.POST, correctUserDto);
//    }

    @Test
    public void tooLongBioTest(){
        correctUserDto.setUserInfoShortBio(createStringWithLength(2001));
        checkForBadRequest(API_USER_CURRENT, RequestMethod.POST, correctUserDto);
    }

    @Test
    public void nullJobTest() {
        correctUserDto.setUserInfoJobTitle(null);
        checkForBadRequest(API_USER_CURRENT, RequestMethod.POST, correctUserDto);
    }

//    @Ignore
//    @Test
//    public void tooShortJobTest(){
//        correctUserDto.setUserInfoJobTitle("");
//        checkForBadRequest(API_USER_CURRENT, RequestMethod.POST, correctUserDto);
//    }

    @Test
    public void tooLongJobTest(){
        correctUserDto.setUserInfoJobTitle(createStringWithLength(257));
        checkForBadRequest(API_USER_CURRENT, RequestMethod.POST, correctUserDto);
    }

    @Test
    public void nullCompanyTest() {
        correctUserDto.setUserInfoCompany(null);
        checkForBadRequest(API_USER_CURRENT, RequestMethod.POST, correctUserDto);
    }

//    @Ignore
//    @Test
//    public void tooShortCompanyTest(){
//        correctUserDto.setUserInfoCompany("");
//        checkForBadRequest(API_USER_CURRENT, RequestMethod.POST, correctUserDto);
//    }

    @Test
    public void tooLongCompanyTest(){
        correctUserDto.setUserInfoCompany(createStringWithLength(257));
        checkForBadRequest(API_USER_CURRENT, RequestMethod.POST, correctUserDto);
    }

    @Test
    public void tooLongPastConferenceTest(){
        correctUserDto.setUserInfoPastConference(createStringWithLength(1001));
        checkForBadRequest(API_USER_CURRENT, RequestMethod.POST, correctUserDto);
    }

    @Test
    public void tooLongAdditionalInfoTest(){
        correctUserDto.setUserInfoAdditionalInfo(createStringWithLength(1001));
        checkForBadRequest(API_USER_CURRENT, RequestMethod.POST, correctUserDto);
    }

    @Test
    @WithMockUser(username = ORGANISER_EMAIL, roles = ORGANISER_ROLE)
    public void getUserById() throws Exception{
        User user=createUser();
        when(userService.find(anyLong())).thenReturn(user);
        mockMvc.perform(prepareGetRequest(API_USER+"/"+1)
        ).andExpect(status().isOk())
                .andExpect(jsonPath("fname", is(user.getFirstName())))
                .andExpect(jsonPath("lname", is(user.getLastName())))
                .andExpect(jsonPath("mail", is(user.getEmail())))
                .andExpect(jsonPath("bio", is(user.getUserInfo().getShortBio())))
                .andExpect(jsonPath("job", is(user.getUserInfo().getJobTitle())))
                .andExpect(jsonPath("past", is(user.getUserInfo().getPastConference())))
                .andExpect(jsonPath("photo", is("api/user/current/photo/" + user.getId())))
                .andExpect(jsonPath("info", is(user.getUserInfo().getAdditionalInfo())))
                .andExpect(jsonPath("linkedin", is(user.getUserInfo().getContacts().get(new ContactType(1L, "LinkedIn")))))
                .andExpect(jsonPath("twitter", is(user.getUserInfo().getContacts().get(new ContactType(2L, "Twitter")))))
                .andExpect(jsonPath("facebook", is(user.getUserInfo().getContacts().get(new ContactType(3L, "FaceBook")))))
                .andExpect(jsonPath("blog", is(user.getUserInfo().getContacts().get(new ContactType(4L, "Blog")))))
                .andExpect(jsonPath("roles[0]", is("ROLE_SPEAKER")));
    }

    @Test
    public void incorrectGetUserById() throws Exception{
        User user=createUser();
        when(userService.find(1L)).thenReturn(user);
        mockMvc.perform(prepareGetRequest(API_USER+"/"+1)).
                andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = ORGANISER_EMAIL, roles = ORGANISER_ROLE)
    public void notFoundUserById() throws Exception{

        when(userService.find(1L)).thenThrow(EntityNotFoundException.class);
        mockMvc.perform(prepareGetRequest(API_USER+"/"+1)).
                andExpect(status().isNotFound());
    }

    private void performRegistration(String api, int expectedStatus) throws Exception{
        mockMvc.perform(post(api)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(correctRegistrationDto))
        ).andExpect(status().is(expectedStatus));
    }

    private MockHttpServletRequestBuilder prepareGetRequest(String uri) throws Exception{
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
            e.printStackTrace();
        }
    }

    private RegistrationDto setupCorrectRegistrationDto(){
        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setPassword("123456");
        registrationDto.setLastName("Ivanova");
        registrationDto.setFirstName("Olya");
        registrationDto.setConfirm("123456");
        registrationDto.setEmail(uniqueEmail);
        return  registrationDto;
    }

    private UserDto setupCorrectUserInfoDto() {
        UserDto userDto = new UserDto();
        userDto.setUserInfoShortBio("bio");
        userDto.setUserInfoJobTitle("job");
        userDto.setUserInfoCompany("company");
        return userDto;
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

    @Override
    protected UserInfo createUserInfo(){
        ContactType contactType = new ContactType(1L, "LinkedIn");
        ContactType contactType2 = new ContactType(2L, "Twitter");
        ContactType contactType3 = new ContactType(3L, "FaceBook");
        ContactType contactType4 = new ContactType(4L, "Blog");

        List contactTypes = spy(List.class);
        when(contactTypes.get(anyInt())).thenReturn(contactType).thenReturn(contactType2).thenReturn(contactType3).thenReturn(contactType4);
        when(contactTypeService.findByName(anyString())).thenReturn(contactTypes);

        Map<ContactType, String> contacts = new HashMap<ContactType, String>(){{
            put(contactType, "LinkedIn");
            put(contactType2, "Twitter");
            put(contactType3, "FaceBook");
            put(contactType4, "Blog");
        }};
        return new UserInfo(1L, "bio", "job", "pastConference", "EPAM", contacts, "addInfo");
    }
}