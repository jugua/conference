package ua.rd.cm.web.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ua.rd.cm.config.TestSecurityConfig;
import ua.rd.cm.config.WebMvcConfig;
import ua.rd.cm.config.WebTestConfig;
import ua.rd.cm.domain.*;
import ua.rd.cm.services.TalkService;
import ua.rd.cm.services.UserInfoService;
import ua.rd.cm.services.UserService;
import ua.rd.cm.services.exception.TalkNotFoundException;
import ua.rd.cm.dto.MessageDto;
import ua.rd.cm.dto.TalkDto;
import ua.rd.cm.services.exception.TalkValidationException;

import javax.servlet.Filter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebTestConfig.class, WebMvcConfig.class, TestSecurityConfig.class})
@WebAppConfiguration
public class TalkControllerTest extends TestUtil {
    private static final String API_TALK = "/api/talk";
    private static final String SPEAKER_EMAIL = "ivanova@gmail.com";
    private static final String ORGANISER_EMAIL = "trybel@gmail.com";
    public static final String APPROVED = "Approved";
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private Filter springSecurityFilterChain;

    @Autowired
    private TalkService talkService;
    @Autowired
    private TalkController talkController;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserService userService;

    private MockMvc mockMvc;

    private User speakerUser;
    private User organiserUser;
    private UserInfo userInfo;
    private TalkDto correctTalkDto;

    MockHttpServletRequestBuilder requestBuilder;

    @Before
    public void setUp() {
        correctTalkDto = setupCorrectTalkDto();
        requestBuilder = MockMvcRequestBuilders.fileUpload(API_TALK).
                param("title", "title name").
                param("description", "desc").
                param("topic", "topic").
                param("type", "type").
                param("lang", "English").
                param("level", "Beginner");

        userInfo = new UserInfo();
        userInfo.setId(1L);
        userInfo.setShortBio("bio");
        userInfo.setJobTitle("job");
        userInfo.setPastConference("pastConference");
        userInfo.setCompany("EPAM");
        userInfo.setAdditionalInfo("addInfo");

        Set<Role> speakerRole = new HashSet<>();
        speakerRole.add(new Role(2L, Role.SPEAKER));
        speakerUser = new User();
        speakerUser.setId(1L);
        speakerUser.setFirstName("Olya");
        speakerUser.setLastName("Ivanova");
        speakerUser.setEmail("ivanova@gmail.com");
        speakerUser.setPassword("123456");
        speakerUser.setStatus(User.UserStatus.CONFIRMED);
        speakerUser.setUserInfo(userInfo);
        speakerUser.setUserRoles(speakerRole);

        Set<Role> organiserRole = new HashSet<>();
        organiserRole.add(new Role(1L, Role.ORGANISER));
        organiserUser = new User();
        organiserUser.setId(1L);
        organiserUser.setFirstName("Artem");
        organiserUser.setLastName("Trybel");
        organiserUser.setEmail("trybel@gmail.com");
        organiserUser.setPassword("123456");
        organiserUser.setStatus(User.UserStatus.CONFIRMED);
        organiserUser.setUserInfo(userInfo);
        organiserUser.setUserRoles(organiserRole);

        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilter(springSecurityFilterChain)
                .apply(springSecurity())
                .build();
        when(userService.getByEmail(eq(SPEAKER_EMAIL))).thenReturn(speakerUser);
        when(userService.getByEmail(eq(ORGANISER_EMAIL))).thenReturn(organiserUser);
        when(userInfoService.find(anyLong())).thenReturn(userInfo);
    }

    @After
    public void resetMocks() {
        Mockito.reset(talkService, userService);
    }

    /**
     * Test submitTalk() for successful result
     *
     * @throws Exception
     */
    @Test
    @WithMockUser(username = SPEAKER_EMAIL, roles = SPEAKER_ROLE)
    public void testSuccessfulSubmitTalkAsSpeaker() throws Exception {
        TalkDto talkDto = new TalkDto(null, "title name", null, null, null, null, "desc", "topic", "type", "English", "Beginner", null, null, null, null, null, null);
        Talk talk = new Talk();
        talk.setId(1L);

        when(talkService.save(talkDto, speakerUser, null)).thenReturn(talk);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(Integer.parseInt(talk.getId().toString()))));

    }


    /**
     * @throws Exception
     */
    @Test
    public void testUnauthorizedErrorWhenSubmitTalk() throws Exception {
        mockMvc.perform(requestBuilder).andExpect(status().isUnauthorized())
                .andExpect(jsonPath("error", is(ApplicationControllerAdvice.UNAUTHORIZED_MSG)));
    }

    /**
     * Test getTalks() method for correct data return to speaker
     *
     * @throws Exception
     */
    @Test
    @WithMockUser(username = SPEAKER_EMAIL, roles = SPEAKER_ROLE)
    public void testSuccessfulGetTalksAsSpeaker() throws Exception {
        TalkDto talkDto = correctTalkDto;
        List<TalkDto> talks = new ArrayList<>();
        talks.add(talkDto);

        when(talkService.getTalksForSpeaker(speakerUser.getEmail())).thenReturn(talks);
        mockMvc.perform(prepareGetRequest(API_TALK))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is(talkDto.getTitle())))
                .andExpect(jsonPath("$[0].description", is(talkDto.getDescription())))
                .andExpect(jsonPath("$[0].topic", is(talkDto.getTopicName())))
                .andExpect(jsonPath("$[0].type", is(talkDto.getTypeName())))
                .andExpect(jsonPath("$[0].lang", is(talkDto.getLanguageName())))
                .andExpect(jsonPath("$[0].level", is(talkDto.getLevelName())))
                .andExpect(jsonPath("$[0].addon", is(talkDto.getAdditionalInfo())))
                .andExpect(jsonPath("$[0].status", is(talkDto.getStatusName())))
                .andExpect(jsonPath("$[0].assignee", is(talkDto.getAssignee())));
    }

    /**
     * Test getTalks() method for correct data return to organiser
     *
     * @throws Exception
     */
    @Test
    @WithMockUser(username = ORGANISER_EMAIL, roles = ORGANISER_ROLE)
    public void testSuccessfulGetTalksAsOrganiser() throws Exception {
        List<TalkDto> talkDtos = new ArrayList<>();
        correctTalkDto.setUserId(speakerUser.getId());
        correctTalkDto.setSpeakerFullName(speakerUser.getFullName());
        talkDtos.add(correctTalkDto);

        when(talkService.getTalksForOrganiser()).thenReturn(talkDtos);
        mockMvc.perform(prepareGetRequest(API_TALK))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is(correctTalkDto.getTitle())))
                .andExpect(jsonPath("$[0].speakerId", is(Integer.parseInt(correctTalkDto.getUserId().toString()))))
                .andExpect(jsonPath("$[0].name", is(correctTalkDto.getSpeakerFullName())))
                .andExpect(jsonPath("$[0].description", is(correctTalkDto.getDescription())))
                .andExpect(jsonPath("$[0].topic", is(correctTalkDto.getTopicName())))
                .andExpect(jsonPath("$[0].type", is(correctTalkDto.getTypeName())))
                .andExpect(jsonPath("$[0].lang", is(correctTalkDto.getLanguageName())))
                .andExpect(jsonPath("$[0].level", is(correctTalkDto.getLevelName())))
                .andExpect(jsonPath("$[0].addon", is(correctTalkDto.getAdditionalInfo())))
                .andExpect(jsonPath("$[0].status", is(correctTalkDto.getStatusName())))
                .andExpect(jsonPath("$[0].date", is(correctTalkDto.getDate())))
                .andExpect(jsonPath("$[0].comment", is(correctTalkDto.getOrganiserComment())))
                .andExpect(jsonPath("$[0].assignee", is(correctTalkDto.getAssignee())));
    }

    /**
     * Test getTalks() method for unauthorized access
     *
     * @throws Exception
     */
    @Test
    public void testUnauthorizedErrorGetMyTalks() throws Exception {
        mockMvc.perform(prepareGetRequest(API_TALK))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("error", is(ApplicationControllerAdvice.UNAUTHORIZED_MSG)));
    }

    /**
     * Test getTalkById()
     *
     * @throws Exception
     */
    @Test
    @WithMockUser(username = ORGANISER_EMAIL, roles = ORGANISER_ROLE)
    public void testSuccessfulGetTalkByIdAsOrganiser() throws Exception {
        correctTalkDto.setAssignee(organiserUser.getFullName());

        when(talkService.findById((anyLong()))).thenReturn(correctTalkDto);

        mockMvc.perform(prepareGetRequest(API_TALK + "/" + 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(Integer.parseInt(correctTalkDto.getId().toString()))))
                .andExpect(jsonPath("title", is(correctTalkDto.getTitle())))
                .andExpect(jsonPath("speakerId", is(Integer.parseInt(correctTalkDto.getUserId().toString()))))
                .andExpect(jsonPath("name", is(correctTalkDto.getSpeakerFullName())))
                .andExpect(jsonPath("description", is(correctTalkDto.getDescription())))
                .andExpect(jsonPath("topic", is(correctTalkDto.getTopicName())))
                .andExpect(jsonPath("type", is(correctTalkDto.getTypeName())))
                .andExpect(jsonPath("lang", is(correctTalkDto.getLanguageName())))
                .andExpect(jsonPath("level", is(correctTalkDto.getLevelName())))
                .andExpect(jsonPath("addon", is(correctTalkDto.getAdditionalInfo())))
                .andExpect(jsonPath("status", is(correctTalkDto.getStatusName())))
                .andExpect(jsonPath("date", is(correctTalkDto.getDate())))
                .andExpect(jsonPath("comment", is(correctTalkDto.getOrganiserComment())))
                .andExpect(jsonPath("assignee", is(correctTalkDto.getAssignee())));

    }

    /**
     * Test unsuccessful getTalkById() because of unauthorized access
     *
     * @throws Exception
     */
    @Test
    public void testUnauthorizedErrorGetTalkById() throws Exception {
        mockMvc.perform(prepareGetRequest(API_TALK + "/" + 1))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("error", is(ApplicationControllerAdvice.UNAUTHORIZED_MSG)));
    }

    /**
     * Test getTalkById() with TalkNotFoundException
     *
     * @throws Exception
     */
    @Test
    @WithMockUser(username = ORGANISER_EMAIL, roles = ORGANISER_ROLE)
    public void testTalkNotFoundExceptionGetTalkById() throws Exception {
        when(talkService.findById(anyLong())).thenThrow(new TalkNotFoundException());
        mockMvc.perform(prepareGetRequest(API_TALK + "/" + 1)).
                andExpect(status().isNotFound())
                .andExpect(jsonPath("error", is(TalkController.TALK_NOT_FOUND)));
    }

    /**
     * Test for actionOnTalk() method
     *
     * @throws Exception
     */
    @Test
    @WithMockUser(username = ORGANISER_EMAIL, roles = ORGANISER_ROLE)
    public void testSuccessfulUpdateTalkAsOrganiser() throws Exception {
        TalkDto talkDto = correctTalkDto;
        talkDto.setOrganiserComment("comment");
        talkDto.setStatusName(APPROVED);

        mockMvc.perform(preparePatchRequest(API_TALK + "/" + 1l, talkDto))
                .andExpect(status().isOk())
                .andExpect(jsonPath("result", is("successfully_updated")));
        verify(talkService, atLeastOnce()).updateAsOrganiser(talkDto, organiserUser);
    }

    /**
     * Test for actionOnTalk() method
     *
     * @throws Exception
     */
    @Test
    @WithMockUser(username = SPEAKER_EMAIL, roles = SPEAKER_ROLE)
    public void testSuccessfulUpdateTalkAsSpeaker() throws Exception {
        TalkDto talkDto = correctTalkDto;
        talkDto.setOrganiserComment("comment");
        talkDto.setStatusName(APPROVED);

        mockMvc.perform(preparePatchRequest(API_TALK + "/" + 1l, talkDto))
                .andExpect(status().isOk())
                .andExpect(jsonPath("result", is("successfully_updated")));
        verify(talkService, atLeastOnce()).updateAsSpeaker(talkDto, speakerUser);
    }

    /**
     * Test for actionOnTalk() method
     *
     * @throws Exception
     */
    @Test
    public void testUnauthorizedErrorWhenUpdateTalk() throws Exception {
        mockMvc.perform(preparePatchRequest(API_TALK + "/" + 1, correctTalkDto))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("error", is(ApplicationControllerAdvice.UNAUTHORIZED_MSG)));
    }

    /**
     * Test actionOnTalk() by speaker with TalkValidationException
     *
     * @throws Exception
     */
    @Test
    @WithMockUser(username = SPEAKER_EMAIL, roles = SPEAKER_ROLE)
    public void testTalkValidationExceptionActionOnTalkAsSpeaker() throws Exception {
        TalkDto talkDto = correctTalkDto;
        talkDto.setOrganiserComment("comment");
        talkDto.setStatusName(APPROVED);

        doThrow(new TalkValidationException(TalkValidationException.ADDITIONAL_COMMENT_TOO_LONG)).when(talkService).updateAsSpeaker(talkDto, speakerUser);

        mockMvc.perform(preparePatchRequest(API_TALK + "/" + 1l, talkDto))
                .andExpect(status().isPayloadTooLarge())
                .andExpect(jsonPath("error", is(TalkValidationException.ADDITIONAL_COMMENT_TOO_LONG)));
    }

    /**
     * Test actionOnTalk() with TalkNotFoundException
     *
     * @throws Exception
     */
    @Test
    @WithMockUser(username = ORGANISER_EMAIL, roles = ORGANISER_ROLE)
    public void testTalkNotFoundExceptionUserActionOnTalkAsOrganiser() throws Exception {
        doThrow(new TalkNotFoundException()).when(talkService).updateAsOrganiser(correctTalkDto, organiserUser);

        mockMvc.perform(preparePatchRequest(API_TALK + "/" + 1, correctTalkDto))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("error", is(TalkController.TALK_NOT_FOUND)));
    }

    @Test
    public void handleTalkNotFoundCorrectStatus() throws Exception {
        ResponseEntity<MessageDto> response = talkController.handleTalkNotFound();
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    public void handleTalkNotFoundCorrectMessage() throws Exception {
        ResponseEntity<MessageDto> response = talkController.handleTalkNotFound();
        assertThat(response.getBody().getError(), is(TalkController.TALK_NOT_FOUND));
    }

    private MockHttpServletRequestBuilder prepareGetRequest(String uri) throws Exception {
        return MockMvcRequestBuilders.get(uri)
                .contentType(MediaType.APPLICATION_JSON_UTF8);
    }

    private MockHttpServletRequestBuilder preparePatchRequest(String uri, TalkDto dto) throws JsonProcessingException {
        return MockMvcRequestBuilders.patch(uri)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(dto));
    }


    private TalkDto setupCorrectTalkDto() {
        TalkDto correctTalkDto = new TalkDto();
        correctTalkDto.setId(1L);
        correctTalkDto.setDescription("Description");
        correctTalkDto.setTitle("Title");
        correctTalkDto.setLanguageName("English");
        correctTalkDto.setLevelName("Beginner");
        correctTalkDto.setStatusName(TalkController.DEFAULT_TALK_STATUS);
        correctTalkDto.setTypeName("Regular Talk");
        correctTalkDto.setTopicName("JVM Languages and new programming paradigms");
        correctTalkDto.setDate(LocalDateTime.now().toString());
        correctTalkDto.setAdditionalInfo("Info");
        correctTalkDto.setOrganiserComment("Org comment");
        correctTalkDto.setUserId(1L);
        return correctTalkDto;
    }


    private byte[] convertObjectToJsonBytes(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

}


