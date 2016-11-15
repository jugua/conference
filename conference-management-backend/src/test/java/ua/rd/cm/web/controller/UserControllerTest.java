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
import ua.rd.cm.services.UserService;
import ua.rd.cm.web.controller.dto.RegistrationDto;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
}
