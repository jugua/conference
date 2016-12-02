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
import ua.rd.cm.domain.Role;
import ua.rd.cm.domain.User;
import ua.rd.cm.domain.UserInfo;
import ua.rd.cm.services.TalkService;
import ua.rd.cm.services.UserInfoService;
import ua.rd.cm.services.UserService;
import ua.rd.cm.web.controller.dto.TalkDto;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebTestConfig.class, WebMvcConfig.class, })
@WebAppConfiguration
public class TalkControllerTest {
    public static final String API_TALK = "/api/talk";
    private MockMvc mockMvc;
    private TalkDto correctTalkDto;

    @Autowired
    private TalkService talkService;

    @Autowired
    private TalkController talkController;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserService userService;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(talkController).build();
        correctTalkDto = setupCorrectTalkDto();
    }

    private void mockServices(User user, UserInfo userInfo) {
        when(userService.getByEmail(user.getEmail())).thenReturn(user);
        when(userInfoService.find(anyLong())).thenReturn(userInfo);
    }

    @Test
    public void correctSubmitNewTalkTest() throws Exception{
        UserInfo userInfo = createUserInfo();
        Role role = createSpeakerRole();
        User user = createUser(role, userInfo);
        Principal correctPrincipal = () -> user.getEmail();

        mockServices(user, userInfo);

        mockMvc.perform(post(API_TALK)
                .principal(correctPrincipal)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(correctTalkDto))
        ).andExpect(status().isOk());
    }

    @Test
    public void nullPrincipleSubmitNewTalkTest() throws Exception {
        UserInfo userInfo = createUserInfo();
        Role role = createSpeakerRole();
        User user = createUser(role, userInfo);

        mockServices(user, userInfo);

        mockMvc.perform(post(API_TALK)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(correctTalkDto))
        ).andExpect(status().isUnauthorized());
    }

    private TalkDto setupCorrectTalkDto() {
        TalkDto correctTalkDto = new TalkDto();
        correctTalkDto.setDescription("Description");
        correctTalkDto.setTitle("Title");
        correctTalkDto.setLanguage("English");
        correctTalkDto.setLevel("Beginner");
        correctTalkDto.setStatus("New");
        correctTalkDto.setType("Regular Talk");
        correctTalkDto.setTopic("JVM Languages and new programming paradigms");
        correctTalkDto.setDate(LocalDateTime.now().toString());
        correctTalkDto.setAdditionalInfo("Info");
        return correctTalkDto;
    }

    private byte[] convertObjectToJsonBytes(Object object) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

    private User createUser(Role role , UserInfo info){
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        return new User(1L,"Olya","Ivanova","ivanova@gmail.com", "123456", null, info, roles);
    }

    private Role createSpeakerRole(){
        return new Role(1L, "SPEAKER");
    }

    private UserInfo createUserInfo(){
       return new UserInfo(1L, "bio", "job", "pastConference", "EPAM", null, "addInfo");
    }
}
