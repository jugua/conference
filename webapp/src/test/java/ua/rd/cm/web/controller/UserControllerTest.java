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
import org.springframework.web.bind.annotation.RequestMethod;
import ua.rd.cm.config.WebMvcConfig;
import ua.rd.cm.config.WebTestConfig;
import ua.rd.cm.domain.ContactType;
import ua.rd.cm.domain.Role;
import ua.rd.cm.domain.User;
import ua.rd.cm.domain.UserInfo;
import ua.rd.cm.services.ContactTypeService;
import ua.rd.cm.services.UserInfoService;
import ua.rd.cm.services.UserService;
import ua.rd.cm.web.controller.dto.RegistrationDto;
import ua.rd.cm.web.controller.dto.UserInfoDto;

import java.security.Principal;
import java.util.*;

import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebTestConfig.class, WebMvcConfig.class, })
@WebAppConfiguration
public class UserControllerTest {
    public static final String API_USER_CURRENT = "/api/user/current";
    public static final String API_USER = "/api/user";
    private MockMvc mockMvc;
    private RegistrationDto correctRegistrationDto;
    private UserInfoDto correctUserInfoDto;
    private String uniqueEmail = "unique@gmail.com";
    private String alreadyRegisteredEmail = "registered@gmail.com";

    @Autowired
    private UserService userServiceMock;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private ContactTypeService contactTypeService;

    @Autowired
    private UserController userController;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        correctRegistrationDto = setupCorrectRegistrationDto();
        correctUserInfoDto = setupCorrectUserInfoDto();
    }

    @Test
    public void correctRegistrationTest() throws Exception{
        mockMvc.perform(post(API_USER)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(correctRegistrationDto))
        ).andExpect(status().isAccepted());
    }

    @Test
    public void alreadyRegisteredEmailTest() throws Exception{
        correctRegistrationDto.setEmail(alreadyRegisteredEmail);

        when(userServiceMock.isEmailExist(alreadyRegisteredEmail)).thenReturn(true);

        mockMvc.perform(post(API_USER)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(correctRegistrationDto))
        ).andExpect(status().isConflict());
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
    public void correctPrincipalGetCurrentUserTest() throws Exception{
        Role speaker = createSpeakerRole();
        UserInfo info = createUserInfo();
        User user = createUser(speaker, info);
        Principal correctPrincipal = () -> user.getEmail();

        when(userServiceMock.getByEmail(user.getEmail())).thenReturn(user);

        mockMvc.perform(get(API_USER_CURRENT)
                .principal(correctPrincipal)
        ).andExpect(status().isAccepted())
                .andExpect(jsonPath("fname", is(user.getFirstName())))
                .andExpect(jsonPath("lname", is(user.getLastName())))
                .andExpect(jsonPath("mail", is(user.getEmail())))
                .andExpect(jsonPath("bio", is(user.getUserInfo().getShortBio())))
                .andExpect(jsonPath("job", is(user.getUserInfo().getJobTitle())))
                .andExpect(jsonPath("past", is(user.getUserInfo().getPastConference())))
                .andExpect(jsonPath("photo", is(user.getPhoto())))
                .andExpect(jsonPath("info", is(user.getUserInfo().getAdditionalInfo())))
                .andExpect(jsonPath("linkedin", is(user.getUserInfo().getContacts().get(new ContactType(1L, "LinkedIn")))))
                .andExpect(jsonPath("twitter", is(user.getUserInfo().getContacts().get(new ContactType(2L, "Twitter")))))
                .andExpect(jsonPath("facebook", is(user.getUserInfo().getContacts().get(new ContactType(3L, "FaceBook")))))
                .andExpect(jsonPath("blog", is(user.getUserInfo().getContacts().get(new ContactType(4L, "Blog")))))
                .andExpect(jsonPath("roles[0]", is("s")));
    }

    @Test
    public void correctFillUserInfoTest() throws Exception{
        Role speaker = createSpeakerRole();
        UserInfo info = createUserInfo();
        User user = createUser(speaker, info);
        Principal correctPrincipal = () -> user.getEmail();

        when(userServiceMock.getByEmail(user.getEmail())).thenReturn(user);

        mockMvc.perform(post(API_USER_CURRENT)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(correctUserInfoDto))
                .principal(correctPrincipal)
        ).andExpect(status().isOk());
    }

    @Test
    public void incorrectPrincipalFillInfoTest() throws Exception {
        mockMvc.perform(post(API_USER_CURRENT)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(correctUserInfoDto))
        ).andExpect(status().isUnauthorized());
    }

    @Test
    public void nullBioTest() {
        correctUserInfoDto.setUserInfoShortBio(null);
        checkForBadRequest(API_USER_CURRENT, RequestMethod.POST, correctUserInfoDto);
    }

    @Test
    public void toShortBioTest(){
        correctUserInfoDto.setUserInfoShortBio("");
        checkForBadRequest(API_USER_CURRENT, RequestMethod.POST, correctUserInfoDto);
    }

    @Test
    public void toLongBioTest(){
        correctUserInfoDto.setUserInfoShortBio(createStringWithLength(2001));
        checkForBadRequest(API_USER_CURRENT, RequestMethod.POST, correctUserInfoDto);
    }

    @Test
    public void nullJobTest() {
        correctUserInfoDto.setUserInfoJobTitle(null);
        checkForBadRequest(API_USER_CURRENT, RequestMethod.POST, correctUserInfoDto);
    }

    @Test
    public void toShortJobTest(){
        correctUserInfoDto.setUserInfoJobTitle("");
        checkForBadRequest(API_USER_CURRENT, RequestMethod.POST, correctUserInfoDto);
    }

    @Test
    public void toLongJobTest(){
        correctUserInfoDto.setUserInfoJobTitle(createStringWithLength(257));
        checkForBadRequest(API_USER_CURRENT, RequestMethod.POST, correctUserInfoDto);
    }

    @Test
    public void nullCompanyTest() {
        correctUserInfoDto.setUserInfoCompany(null);
        checkForBadRequest(API_USER_CURRENT, RequestMethod.POST, correctUserInfoDto);
    }

    @Test
    public void toShortCompanyTest(){
        correctUserInfoDto.setUserInfoCompany("");
        checkForBadRequest(API_USER_CURRENT, RequestMethod.POST, correctUserInfoDto);
    }

    @Test
    public void toLongCompanyTest(){
        correctUserInfoDto.setUserInfoCompany(createStringWithLength(257));
        checkForBadRequest(API_USER_CURRENT, RequestMethod.POST, correctUserInfoDto);
    }

    @Test
    public void toShortPastConferenceTest(){
        correctUserInfoDto.setUserInfoPastConference("");
        checkForBadRequest(API_USER_CURRENT, RequestMethod.POST, correctUserInfoDto);
    }

    @Test
    public void toLongPastConferenceTest(){
        correctUserInfoDto.setUserInfoPastConference(createStringWithLength(1001));
        checkForBadRequest(API_USER_CURRENT, RequestMethod.POST, correctUserInfoDto);
    }

    @Test
    public void toShortAdditionalInfoTest(){
        correctUserInfoDto.setUserInfoAdditionalInfo("");
        checkForBadRequest(API_USER_CURRENT, RequestMethod.POST, correctUserInfoDto);
    }

    @Test
    public void toLongAdditionalInfoTest(){
        correctUserInfoDto.setUserInfoAdditionalInfo(createStringWithLength(1001));
        checkForBadRequest(API_USER_CURRENT, RequestMethod.POST, correctUserInfoDto);
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
        registrationDto.setPassword("666666");
        registrationDto.setLastName("LastName");
        registrationDto.setFirstName("FirstName");
        registrationDto.setConfirm("666666");
        registrationDto.setEmail(uniqueEmail);
        return  registrationDto;
    }

    private UserInfoDto setupCorrectUserInfoDto() {
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setUserInfoShortBio("bio");
        userInfoDto.setUserInfoJobTitle("job");
        userInfoDto.setUserInfoCompany("company");
        return userInfoDto;
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

    private User createUser(Role role , UserInfo info){
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        User user = new User(1L, "FirstName", "LastName", alreadyRegisteredEmail, "pass", "url", info, roles);
        return user;
    }

    private Role createSpeakerRole(){
        return new Role(1L, "SPEAKER");
    }

    private UserInfo createUserInfo(){
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
