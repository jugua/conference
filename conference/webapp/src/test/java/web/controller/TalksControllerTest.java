package web.controller;

import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import domain.model.Conference;
import domain.model.Role;
import domain.model.Talk;
import domain.model.User;
import domain.model.UserInfo;
import service.businesslogic.api.TalkService;
import service.businesslogic.api.UserInfoService;
import service.businesslogic.api.UserService;
import service.businesslogic.dto.TalkDto;
import service.businesslogic.exception.ResourceNotFoundException;
import service.businesslogic.exception.TalkNotFoundException;
import service.businesslogic.exception.TalkValidationException;
import web.config.TestConfig;
import web.config.WebMvcConfig;
import web.controller.advice.ExceptionAdvice;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class, WebMvcConfig.class})
@WebAppConfiguration
public class TalksControllerTest {

    private static final String MY_TALKS_PAGE_URL = "/talk";
    private static final String SPEAKER_EMAIL = "ivanova@gmail.com";
    private static final String ORGANISER_EMAIL = "trybel@gmail.com";
    private static final String APPROVED = "Approved";
    private static final long TEST_TALK_ID = 1L;

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

    private MockMvc mockMvc;

    private User speaker;
    private User organizer;
    private TalkDto correctTalkDto;

    @Before
    public void setUp() {
        correctTalkDto = setupCorrectTalkDto();

        UserInfo userInfo = createUserInfo();
        speaker = createSpeaker(userInfo);
        organizer = createOrganizer(userInfo);

        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilter(springSecurityFilterChain)
                .apply(springSecurity())
                .build();
        when(userService.getByEmail(eq(SPEAKER_EMAIL))).thenReturn(speaker);
        when(userService.getByEmail(eq(ORGANISER_EMAIL))).thenReturn(organizer);
        when(userInfoService.getById(anyLong())).thenReturn(userInfo);
    }

    @After
    public void resetMocks() {
        Mockito.reset(talkService, userService);
    }

    /**
     * Test getTalks() method for unauthorized access
     *
     * @throws Exception
     */
    @Test
    public void testUnauthorizedErrorGetMyTalks() throws Exception {
        mockMvc.perform(prepareGetRequest(MY_TALKS_PAGE_URL))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("error", is(ExceptionAdvice.UNAUTHORIZED_MSG)));
    }

    /**
     * Test getTalkById()
     *
     * @throws Exception
     */
    @Test
    @WithMockUser(username = ORGANISER_EMAIL, roles = Role.ORGANISER)
    public void testSuccessfulGetTalkByIdAsOrganiser() throws Exception {
        correctTalkDto.setAssignee(organizer.getFullName());
        when(userService.getByEmail(ORGANISER_EMAIL)).thenReturn(organizer);
        when(talkService.findById((TEST_TALK_ID))).thenReturn(correctTalkDto);

        mockMvc.perform(prepareGetRequest(MY_TALKS_PAGE_URL + "/" + TEST_TALK_ID))
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
        mockMvc.perform(prepareGetRequest(MY_TALKS_PAGE_URL + "/" + TEST_TALK_ID))
                .andExpect(status().isUnauthorized());
    }

    /**
     * Test getTalkById() with TalkNotFoundException
     *
     * @throws Exception
     */
    @Test
    @WithMockUser(username = ORGANISER_EMAIL, roles = Role.ORGANISER)
    public void testTalkNotFoundExceptionGetTalkById() throws Exception {
        when(userService.getByEmail(ORGANISER_EMAIL)).thenReturn(organizer);
        when(talkService.findById(TEST_TALK_ID)).thenThrow(new TalkNotFoundException());
        mockMvc.perform(prepareGetRequest(MY_TALKS_PAGE_URL + "/" + TEST_TALK_ID)).
                andExpect(status().isNotFound())
                .andExpect(jsonPath("error", is(ResourceNotFoundException.TALK_NOT_FOUND)));
    }

    /**
     * Test for updateTalk() method
     *
     * @throws Exception
     */
    @Test
    @WithMockUser(username = ORGANISER_EMAIL, roles = Role.ORGANISER)
    public void testSuccessfulUpdateTalkAsOrganiser() throws Exception {
        TalkDto talkDto = correctTalkDto;
        talkDto.setOrganiserComment("comment");
        talkDto.setStatusName(APPROVED);

        mockMvc.perform(preparePatchRequest(MY_TALKS_PAGE_URL + "/" + TEST_TALK_ID, talkDto))
                .andExpect(status().isOk())
                .andExpect(jsonPath("result", is("successfully_updated")));
        verify(talkService, atLeastOnce()).updateAsOrganiser(talkDto, organizer);
    }

    /**
     * Test for updateTalk() method
     *
     * @throws Exception
     */
    @Test
    @WithMockUser(username = SPEAKER_EMAIL, roles = Role.SPEAKER)
    public void testSuccessfulUpdateTalkAsSpeaker() throws Exception {
        TalkDto talkDto = correctTalkDto;
        talkDto.setOrganiserComment("comment");
        talkDto.setStatusName(APPROVED);

        mockMvc.perform(preparePatchRequest(MY_TALKS_PAGE_URL + "/" + TEST_TALK_ID, talkDto))
                .andExpect(status().isOk())
                .andExpect(jsonPath("result", is("successfully_updated")));
        verify(talkService, atLeastOnce()).updateAsSpeaker(talkDto, speaker);
    }

    /**
     * Test for updateTalk() method
     *
     * @throws Exception
     */
    @Test
    public void testUnauthorizedErrorWhenUpdateTalk() throws Exception {
        mockMvc.perform(preparePatchRequest(MY_TALKS_PAGE_URL + "/" + TEST_TALK_ID, correctTalkDto))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("error", is(ExceptionAdvice.UNAUTHORIZED_MSG)));
    }

    /**
     * Test updateTalk() by speaker with TalkValidationException
     *
     * @throws Exception
     */
    @Test
    @WithMockUser(username = SPEAKER_EMAIL, roles = Role.SPEAKER)
    public void testTalkValidationExceptionActionOnTalkAsSpeaker() throws Exception {
        TalkDto talkDto = correctTalkDto;
        talkDto.setOrganiserComment("comment");
        talkDto.setStatusName(APPROVED);

        doThrow(new TalkValidationException(TalkValidationException.ADDITIONAL_COMMENT_TOO_LONG))
                .when(talkService).updateAsSpeaker(talkDto, speaker);

        mockMvc.perform(preparePatchRequest(MY_TALKS_PAGE_URL + "/" + TEST_TALK_ID, talkDto))
                .andExpect(status().isPayloadTooLarge())
                .andExpect(jsonPath("error", is(TalkValidationException.ADDITIONAL_COMMENT_TOO_LONG)));
    }

    /**
     * Test updateTalk() with TalkNotFoundException
     *
     * @throws Exception
     */
    @Test
    @WithMockUser(username = ORGANISER_EMAIL, roles = Role.ORGANISER)
    public void testTalkNotFoundExceptionUserActionOnTalkAsOrganiser() throws Exception {
        doThrow(new TalkNotFoundException()).when(talkService).updateAsOrganiser(correctTalkDto, organizer);

        mockMvc.perform(preparePatchRequest(MY_TALKS_PAGE_URL + "/" + TEST_TALK_ID, correctTalkDto))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("error", is(ResourceNotFoundException.TALK_NOT_FOUND)));
    }

    private UserInfo createUserInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(1L);
        userInfo.setShortBio("bio");
        userInfo.setJobTitle("job");
        userInfo.setPastConference("pastConference");
        userInfo.setCompany("EPAM");
        userInfo.setAdditionalInfo("addInfo");
        return userInfo;
    }

    private User createOrganizer(UserInfo userInfo) {
        User result = new User();
        result.setId(1L);
        result.setFirstName("Artem");
        result.setLastName("Trybel");
        result.setEmail("trybel@gmail.com");
        result.setPassword("123456");
        result.setStatus(User.UserStatus.CONFIRMED);
        result.setUserInfo(userInfo);

        Set<Role> roles = new HashSet<>();
        roles.add(new Role(1L, Role.ROLE_ORGANISER));
        result.setRoles(roles);

        HashSet<Conference> organizerConferences = new HashSet<>();
        organizerConferences.add(createConference());
        result.setOrganizerConferences(organizerConferences);

        return result;
    }

    private User createSpeaker(UserInfo userInfo) {
        User result = new User();
        result.setId(1L);
        result.setFirstName("Olya");
        result.setLastName("Ivanova");
        result.setEmail("ivanova@gmail.com");
        result.setPassword("123456");
        result.setStatus(User.UserStatus.CONFIRMED);
        result.setUserInfo(userInfo);

        Set<Role> roles = new HashSet<>();
        roles.add(new Role(2L, Role.ROLE_SPEAKER));
        result.setRoles(roles);

        return result;
    }

    private MockHttpServletRequestBuilder prepareGetRequest(String uri) {
        return MockMvcRequestBuilders.get(uri)
                .contentType(MediaType.APPLICATION_JSON_UTF8);
    }

    private MockHttpServletRequestBuilder preparePatchRequest(String uri, TalkDto dto) throws JsonProcessingException {
        return MockMvcRequestBuilders.patch(uri)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(dto));
    }


    private Conference createConference() {
        Conference conference = new Conference();
        ArrayList<Talk> talks = new ArrayList<>();
        talks.add(createTalk());
        conference.setTalks(talks);
        return conference;
    }

    private Talk createTalk() {
        Talk talk = new Talk();
        talk.setId(TEST_TALK_ID);
        return talk;
    }

    private TalkDto setupCorrectTalkDto() {
        TalkDto correctTalkDto = new TalkDto();
        correctTalkDto.setId(TEST_TALK_ID);
        correctTalkDto.setDescription("Description");
        correctTalkDto.setTitle("Title");
        correctTalkDto.setLanguageName("English");
        correctTalkDto.setLevelName("Beginner");
        correctTalkDto.setStatusName("New");
        correctTalkDto.setTypeName("Regular Talk");
        correctTalkDto.setTopicName("JVM Languages and new programming paradigms");
        correctTalkDto.setDate(LocalDateTime.now().toString());
        correctTalkDto.setAdditionalInfo("Info");
        correctTalkDto.setOrganiserComment("Org comment");
        correctTalkDto.setUserId(TEST_TALK_ID);
        return correctTalkDto;
    }

    private byte[] convertObjectToJsonBytes(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

}


