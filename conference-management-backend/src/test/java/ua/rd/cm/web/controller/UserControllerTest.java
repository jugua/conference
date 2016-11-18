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
import ua.rd.cm.domain.ContactType;
import ua.rd.cm.domain.Role;
import ua.rd.cm.domain.User;
import ua.rd.cm.domain.UserInfo;
import ua.rd.cm.services.UserService;
import ua.rd.cm.web.controller.dto.RegistrationDto;

import java.security.Principal;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebTestConfig.class, WebMvcConfig.class})
@WebAppConfiguration
public class UserControllerTest {
    private MockMvc mockMvc;
    private RegistrationDto correctRegistrationDto;
    private String uniqueEmail = "unique@gmail.com";
    private String alreadyRegisteredEmail = "registered@gmail.com";

    @Autowired
    private UserService userServiceMock;

    @Autowired
    private UserController userController;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        correctRegistrationDto = setupCorrectDto();
    }

    @Test
    public void correctRegistrationTest() throws Exception{
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(correctRegistrationDto))
        ).andExpect(status().isAccepted());
    }

    @Test
    public void alreadyRegisteredEmailTest() throws Exception{
        correctRegistrationDto.setEmail(alreadyRegisteredEmail);

        when(userServiceMock.isEmailExist(alreadyRegisteredEmail)).thenReturn(true);

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(correctRegistrationDto))
        ).andExpect(status().isConflict());
    }

    @Test
    public void unconfirmedPasswordTest(){
        correctRegistrationDto.setConfirm("777777");
        checkForBadRequest();
    }

    @Test
    public void nullFirstNameTest(){
        correctRegistrationDto.setFirstName(null);
        checkForBadRequest();
    }

    @Test
    public void toShortFirstNameTest(){
        correctRegistrationDto.setFirstName("");
        checkForBadRequest();
    }

    @Test
    public void toLongFirstNameTest(){
        correctRegistrationDto.setFirstName(createStringWithLength(57));
        checkForBadRequest();
    }

    @Test
    public void nullLastNameTest(){
        correctRegistrationDto.setLastName(null);
        checkForBadRequest();
    }

    @Test
    public void tooShortLastNameTest(){
        correctRegistrationDto.setLastName("");
        checkForBadRequest();
    }

    @Test
    public void tooLongLastNameTest(){
        correctRegistrationDto.setLastName(createStringWithLength(57));
        checkForBadRequest();
    }

    @Test
    public void nullPasswordTest(){
        correctRegistrationDto.setPassword(null);
        checkForBadRequest();
    }

    @Test
    public void tooShortPasswordTest(){
        correctRegistrationDto.setPassword("");
        checkForBadRequest();
    }

    @Test
    public void tooLongPasswordTest(){
        correctRegistrationDto.setPassword(createStringWithLength(31));
        checkForBadRequest();
    }

    @Test
    public void nullPasswordConfirmationTest(){
        correctRegistrationDto.setConfirm(null);
        checkForBadRequest();
    }

    @Test
    public void tooShortPasswordConfirmationTest(){
        correctRegistrationDto.setConfirm("");
        checkForBadRequest();
    }

    @Test
    public void tooLongPasswordConfirmationTest(){
        correctRegistrationDto.setConfirm(createStringWithLength(31));
        checkForBadRequest();
    }

    @Test
    public void nullEmailTest(){
        correctRegistrationDto.setEmail(null);
        checkForBadRequest();
    }

    @Test
    public void emptyEmailTest(){
        correctRegistrationDto.setEmail("");
        checkForBadRequest();
    }

    @Test
    public void withoutAtCharacterEmailTest(){
        correctRegistrationDto.setEmail("withoutAtCharacter.com");
        checkForBadRequest();
    }

    @Test
    public void withoutDotEmailTest(){
        correctRegistrationDto.setEmail("withoutDot@com");
        checkForBadRequest();
    }

    @Test
    public  void correctPrincipalGetCurrentUserTest() throws Exception{
        Role speaker = createSpeakerRole();
        UserInfo info = createUserInfo();
        User user = createUser(speaker, info);
        Principal correctPrincipal = () -> user.getEmail();

        when(userServiceMock.getByEmail(user.getEmail())).thenReturn(user);

        mockMvc.perform(get("/api/users/current")
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
                .andExpect(jsonPath("linkedin", is(user.getUserInfo().getContacts().get(new ContactType(1L, "linkedin")))))
                .andExpect(jsonPath("twitter", is(user.getUserInfo().getContacts().get(new ContactType(1L, "twitter")))))
                .andExpect(jsonPath("facebook", is(user.getUserInfo().getContacts().get(new ContactType(1L, "facebook")))))
                .andExpect(jsonPath("blog", is(user.getUserInfo().getContacts().get(new ContactType(1L, "blog")))))
                .andExpect(jsonPath("roles[0]", is("S")));
    }

    private void checkForBadRequest(){
        try {
            mockMvc.perform(post("/api/users")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(convertObjectToJsonBytes(correctRegistrationDto))
            ).andExpect(status().isBadRequest());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private RegistrationDto setupCorrectDto(){
        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setPassword("666666");
        registrationDto.setLastName("LastName");
        registrationDto.setFirstName("FirstName");
        registrationDto.setConfirm("666666");
        registrationDto.setEmail(uniqueEmail);
        return  registrationDto;
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
        Map<ContactType, String> contacts = new HashMap<>();
        for (ContactType contactType : createContactTypes()){
            contacts.put(contactType, contactType.getName());
        }
        UserInfo info = new UserInfo(1L, "bio", "job", "pastConference", "test", contacts, "addInfo");
        return info;
    }

    private Set<ContactType> createContactTypes(){
        Set<ContactType> contactTypes = new HashSet<>();
        contactTypes.add(new ContactType(1L, "linkedin"));
        contactTypes.add(new ContactType(2L, "twitter"));
        contactTypes.add(new ContactType(3L, "facebook"));
        contactTypes.add(new ContactType(4L, "blog"));
        return contactTypes;
    }
}
