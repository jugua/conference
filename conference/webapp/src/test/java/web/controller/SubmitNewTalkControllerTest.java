package web.controller;

import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static domain.model.Role.ADMIN;
import static domain.model.Role.ORGANISER;
import static domain.model.Role.SPEAKER;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.Filter;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import domain.model.Role;
import domain.model.Talk;
import domain.model.User;
import domain.model.UserInfo;
import service.businesslogic.api.LevelService;
import service.businesslogic.api.TalkService;
import service.businesslogic.api.TopicService;
import service.businesslogic.api.TypeService;
import service.businesslogic.api.UserInfoService;
import service.businesslogic.api.UserService;
import service.businesslogic.dto.LevelDto;
import service.businesslogic.dto.TalkDto;
import service.businesslogic.dto.TopicDto;
import service.businesslogic.dto.TypeDto;
import service.infrastructure.fileStorage.FileStorageService;
import service.infrastructure.fileStorage.impl.FileStorageServiceImpl;
import web.config.TestConfig;
import web.config.WebMvcConfig;
import web.controller.advice.ExceptionAdvice;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class, WebMvcConfig.class})
@WebAppConfiguration
public class SubmitNewTalkControllerTest {
    private static final String SUBMIT_TALK_PAGE_URL = "/submitTalk";
    private static final String GET_TOPICS_URL = "/topics";
    private static final String GET_TYPES_URL = "/types";
    private static final String GET_LEVELS_URL = "/levels";
    private static final String GET_LANGUAGES_URL = "/languages";

    private static final String SPEAKER_EMAIL = "ivanova@gmail.com";

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private Filter springSecurityFilterChain;

    @Autowired
    private TalkService talkService;

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserService userService;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TopicService topicService;
    @Autowired
    private LevelService levelService;

    private MockMvc mockMvc;

    private User speakerUser;

    MockHttpServletRequestBuilder requestBuilder;

    @Before
    public void setUp() {
        requestBuilder = fileUpload(SUBMIT_TALK_PAGE_URL).
                param("title", "title name").
                param("description", "desc").
                param("topic", "topic").
                param("type", "type").
                param("lang", "English").
                param("level", "Beginner");

        UserInfo userInfo = new UserInfo();
        userInfo.setId(1L);
        userInfo.setShortBio("bio");
        userInfo.setJobTitle("job");
        userInfo.setPastConference("pastConference");
        userInfo.setCompany("EPAM");
        userInfo.setAdditionalInfo("addInfo");

        Set<Role> speakerRole = new HashSet<>();
        speakerRole.add(new Role(2L, Role.ROLE_SPEAKER));
        speakerUser = new User();
        speakerUser.setId(1L);
        speakerUser.setFirstName("Olya");
        speakerUser.setLastName("Ivanova");
        speakerUser.setEmail("ivanova@gmail.com");
        speakerUser.setPassword("123456");
        speakerUser.setStatus(User.UserStatus.CONFIRMED);
        speakerUser.setUserInfo(userInfo);
        speakerUser.setRoles(speakerRole);

        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilter(springSecurityFilterChain)
                .apply(springSecurity())
                .build();
        when(userService.getByEmail(eq(SPEAKER_EMAIL))).thenReturn(speakerUser);
        when(userInfoService.getById(anyLong())).thenReturn(userInfo);
    }

    @After
    public void resetMocks() {
        Mockito.reset(talkService);
    }

    @Test
    @WithMockUser(username = SPEAKER_EMAIL, roles = SPEAKER)
    public void testSuccessfulSubmitTalkAsSpeaker() throws Exception {
        TalkDto talkDto = new TalkDto(
                null, "title name", null, null, null,
                null, "desc", "topic", "type", "English",
                "Beginner", null, null, null, null,
                null, null);
        Talk talk = new Talk();
        talk.setId(1L);

        when(talkService.save(talkDto, speakerUser, null)).thenReturn(talk);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(Integer.parseInt(talk.getId().toString()))));

    }

    @Test
    @WithMockUser(username = SPEAKER_EMAIL, roles = SPEAKER)
    public void testSuccessfulSubmitTalkAsSpeakerWithFile() throws Exception {
        MockMultipartFile file = createMultipartFile();
        TalkDto talkDto = new TalkDto(null, "title name", null, null, null, null,
                "desc", "topic", "type", "English", "Beginner", null,
                null, null, null, null, file);
        Talk talk = new Talk();
        talk.setId(1L);

        requestBuilder = fileUpload(SUBMIT_TALK_PAGE_URL).
                file(file).
                param("title", "title name").
                param("description", "desc").
                param("topic", "topic").
                param("type", "type").
                param("lang", "English").
                param("level", "Beginner");

        when(fileStorageService.saveFile(file, FileStorageServiceImpl.FileType.FILE)).thenReturn("path to file");
        when(talkService.save(talkDto, speakerUser, "path to file")).thenReturn(talk);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(Integer.parseInt(talk.getId().toString()))));
        verify(fileStorageService, times(1)).saveFile(file, FileStorageServiceImpl.FileType.FILE);
    }

    @Test
    public void testUnauthorizedErrorWhenSubmitTalk() throws Exception {
        mockMvc.perform(requestBuilder).andExpect(status().isUnauthorized())
                .andExpect(jsonPath("error", is(ExceptionAdvice.UNAUTHORIZED_MSG)));
    }

    @Test
    public void getTypesShouldNotWorkForUnauthorized() throws Exception {
        List<TypeDto> types = new ArrayList<>();
        when(typeService.getAll()).thenReturn(types);
        mockMvc.perform(prepareGetRequest(GET_TYPES_URL)).
                andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = ORGANISER)
    public void getTypesShouldWorkForOrganiser() throws Exception {
        List<TypeDto> types = new ArrayList<>();
        when(typeService.getAll()).thenReturn(types);
        mockMvc.perform(prepareGetRequest(GET_TYPES_URL)).
                andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = SPEAKER)
    public void getTypesShouldWorkForSpeaker() throws Exception {
        List<TypeDto> types = new ArrayList<>();
        when(typeService.getAll()).thenReturn(types);
        mockMvc.perform(prepareGetRequest(GET_TYPES_URL)).
                andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = ADMIN)
    public void getTypesShouldWorkForAdmin() throws Exception {
        List<TypeDto> types = new ArrayList<>();
        when(typeService.getAll()).thenReturn(types);
        mockMvc.perform(prepareGetRequest(GET_TYPES_URL)).
                andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = ADMIN)
    public void getTypesShouldHaveRightValues() throws Exception {
        TypeDto typeDto = new TypeDto();
        typeDto.setId(1L);
        typeDto.setName("SomeName");
        List<TypeDto> types = new ArrayList<TypeDto>() {{
            add(typeDto);
        }};

        when(typeService.getAll()).thenReturn(types);
        mockMvc.perform(prepareGetRequest(GET_TYPES_URL)).
                andExpect(status().isOk()).
                andExpect(jsonPath("[0].id", CoreMatchers.is(typeDto.getId().intValue()))).
                andExpect(jsonPath("[0].name", CoreMatchers.is(typeDto.getName())));
    }

    @Test
    public void getTopicsShouldNotWorkForUnauthorized() throws Exception {
        mockMvc.perform(get(GET_TOPICS_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = SPEAKER)
    public void getTopicsShouldWorkForSpeaker() throws Exception {
        TopicDto topicDto = new TopicDto();
        topicDto.setName("SomeName");
        List<TopicDto> topics = new ArrayList<TopicDto>() {{
            add(topicDto);
        }};
        when(topicService.getAll()).thenReturn(topics);
        mockMvc.perform(get(GET_TOPICS_URL))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = ORGANISER)
    public void getTopicsShouldWorkForOrganiser() throws Exception {
        when(topicService.getAll()).thenReturn(new ArrayList<>());
        mockMvc.perform(get(GET_TOPICS_URL))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = ADMIN)
    public void getTopicsShouldWorkForAdmin() throws Exception {
        when(topicService.getAll()).thenReturn(new ArrayList<>());
        mockMvc.perform(get(GET_TOPICS_URL))
                .andExpect(status().isOk());
    }

    @Test
    public void getLevelsShouldNotWorkForUnauthorized() throws Exception {
        mockMvc.perform(get(GET_LEVELS_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = SPEAKER)
    public void getLevelsShouldWorkForSpeaker() throws Exception {
        LevelDto levelDto = new LevelDto();
        levelDto.setId(1L);
        levelDto.setName("SomeName");
        List<LevelDto> levels = new ArrayList<LevelDto>() {{
            add(levelDto);
        }};
        when(levelService.findAll()).thenReturn(levels);
        mockMvc.perform(get(GET_LEVELS_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].id", CoreMatchers.is(levelDto.getId().intValue())))
                .andExpect(jsonPath("[0].name", CoreMatchers.is(levelDto.getName())));
    }

    @Test
    @WithMockUser(roles = ORGANISER)
    public void getLevelsShouldWorkForOrganiser() throws Exception {
        when(levelService.findAll()).thenReturn(new ArrayList<>());
        mockMvc.perform(get(GET_LEVELS_URL))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = ADMIN)
    public void getLevelsShouldWorkForAdmin() throws Exception {
        when(levelService.findAll()).thenReturn(new ArrayList<>());
        mockMvc.perform(get(GET_LEVELS_URL))
                .andExpect(status().isOk());
    }

    private MockHttpServletRequestBuilder prepareGetRequest(String uri) {
        return MockMvcRequestBuilders.get(uri)
                .contentType(MediaType.APPLICATION_JSON_UTF8);
    }

    private MockMultipartFile createMultipartFile() throws IOException {
        File file = new File("src/test/resources/trybel_master.JPG");
        FileInputStream fileInputStream = new FileInputStream(file);
        return new MockMultipartFile("file", fileInputStream);
    }
}