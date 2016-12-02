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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    private UserInfo userInfo;
    private Role role;
    private User user;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(talkController).build();
        correctTalkDto = setupCorrectTalkDto();
        userInfo = createUserInfo();
        role = createSpeakerRole();
        user = createUser(role, userInfo);
        mockServices(user, userInfo);
    }

    private void mockServices(User user, UserInfo userInfo) {
        when(userService.getByEmail(user.getEmail())).thenReturn(user);
        when(userInfoService.find(anyLong())).thenReturn(userInfo);
    }

    @Test
    public void correctSubmitNewTalkTest() throws Exception{
        Principal correctPrincipal = () -> user.getEmail();
        mockMvc.perform(post(API_TALK)
                .principal(correctPrincipal)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(correctTalkDto))
        ).andExpect(status().isOk());
    }

    @Test
    public void emptyCompanyMyInfoSubmitNewTalkTest() {
        userInfo.setCompany("");
        Principal correctPrincipal = () -> user.getEmail();
        checkForForbidden(API_TALK, correctPrincipal);
    }

    @Test
    public void emptyJobMyInfoSubmitNewTalkTest() {
        userInfo.setJobTitle("");
        Principal correctPrincipal = () -> user.getEmail();
        checkForForbidden(API_TALK, correctPrincipal);
    }

    @Test
    public void emptyBioMyInfoSubmitNewTalkTest() {
        userInfo.setShortBio("");
        Principal correctPrincipal = () -> user.getEmail();
        checkForForbidden(API_TALK, correctPrincipal);
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

    @Test
    public void nullTitleSubmitNewTalkTest() {
        correctTalkDto.setTitle(null);
        checkForBadRequest(API_TALK, RequestMethod.POST);
    }

    @Test
    public void emptyTitleSubmitNewTalkTest() {
        correctTalkDto.setTitle("");
        checkForBadRequest(API_TALK, RequestMethod.POST);
    }

    @Test
    public void tooLongTitleSubmitNewTalkTest() {
        correctTalkDto.setTitle(createStringWithLength(251));
        checkForBadRequest(API_TALK, RequestMethod.POST);
    }

    @Test
    public void nullDescriptionSubmitNewTalkTest() {
        correctTalkDto.setDescription(null);
        checkForBadRequest(API_TALK, RequestMethod.POST);
    }

    @Test
    public void emptyDescriptionSubmitNewTalkTest() {
        correctTalkDto.setDescription("");
        checkForBadRequest(API_TALK, RequestMethod.POST);
    }

    @Test
    public void tooLongDescriptionSubmitNewTalkTest() {
        correctTalkDto.setDescription(createStringWithLength(3001));
        checkForBadRequest(API_TALK, RequestMethod.POST);
    }

    @Test
    public void nullTopicSubmitNewTalkTest() {
        correctTalkDto.setTopic(null);
        checkForBadRequest(API_TALK, RequestMethod.POST);
    }

    @Test
    public void emptyTopicSubmitNewTalkTest() {
        correctTalkDto.setTopic("");
        checkForBadRequest(API_TALK, RequestMethod.POST);
    }

    @Test
    public void tooLongTopicSubmitNewTalkTest() {
        correctTalkDto.setTopic(createStringWithLength(256));
        checkForBadRequest(API_TALK, RequestMethod.POST);
    }

    @Test
    public void nullTypeSubmitNewTalkTest() {
        correctTalkDto.setType(null);
        checkForBadRequest(API_TALK, RequestMethod.POST);
    }

    @Test
    public void emptyTypeSubmitNewTalkTest() {
        correctTalkDto.setType("");
        checkForBadRequest(API_TALK, RequestMethod.POST);
    }

    @Test
    public void tooLongTypeSubmitNewTalkTest() {
        correctTalkDto.setType(createStringWithLength(256));
        checkForBadRequest(API_TALK, RequestMethod.POST);
    }

    @Test
    public void nullLanguageSubmitNewTalkTest() {
        correctTalkDto.setLanguage(null);
        checkForBadRequest(API_TALK, RequestMethod.POST);
    }

    @Test
    public void emptyLanguageSubmitNewTalkTest() {
        correctTalkDto.setLanguage("");
        checkForBadRequest(API_TALK, RequestMethod.POST);
    }

    @Test
    public void tooLongLanguageSubmitNewTalkTest() {
        correctTalkDto.setLanguage(createStringWithLength(256));
        checkForBadRequest(API_TALK, RequestMethod.POST);
    }

    @Test
    public void nullLevelSubmitNewTalkTest() {
        correctTalkDto.setLevel(null);
        checkForBadRequest(API_TALK, RequestMethod.POST);
    }

    @Test
    public void emptyLevelSubmitNewTalkTest() {
        correctTalkDto.setLevel("");
        checkForBadRequest(API_TALK, RequestMethod.POST);
    }

    @Test
    public void tooLongLevelSubmitNewTalkTest() {
        correctTalkDto.setLevel(createStringWithLength(256));
        checkForBadRequest(API_TALK, RequestMethod.POST);
    }

    @Test
    public void tooLongAddInfoSubmitNewTalkTest() {
        correctTalkDto.setAdditionalInfo(createStringWithLength(1501));
        checkForBadRequest(API_TALK, RequestMethod.POST);
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

    private void checkForBadRequest(String uri, RequestMethod method) {
        try {
            if (method == RequestMethod.GET) {
                mockMvc.perform(get(uri)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(convertObjectToJsonBytes(correctTalkDto))
                ).andExpect(status().isBadRequest());
            } else if (method == RequestMethod.POST) {
                mockMvc.perform(post(uri)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(convertObjectToJsonBytes(correctTalkDto))
                ).andExpect(status().isBadRequest());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkForForbidden(String uri, Principal principal) {
        try {
            mockMvc.perform(post(uri)
                    .principal(principal)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(convertObjectToJsonBytes(correctTalkDto))
            ).andExpect(status().isForbidden());
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        return new User(1L,"Olya","Ivanova","ivanova@gmail.com", "123456", null, info, roles);
    }

    private Role createSpeakerRole(){
        return new Role(1L, "SPEAKER");
    }

    private UserInfo createUserInfo(){
       return new UserInfo(1L, "bio", "job", "pastConference", "EPAM", null, "addInfo");
    }
}
