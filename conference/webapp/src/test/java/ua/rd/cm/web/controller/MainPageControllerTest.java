package ua.rd.cm.web.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import ua.rd.cm.config.TestSecurityConfig;
import ua.rd.cm.config.WebMvcConfig;
import ua.rd.cm.config.WebTestConfig;
import ua.rd.cm.domain.Conference;
import ua.rd.cm.domain.Talk;
import ua.rd.cm.domain.TalkStatus;
import ua.rd.cm.dto.*;
import ua.rd.cm.services.businesslogic.ConferenceService;
import ua.rd.cm.services.resources.LevelService;
import ua.rd.cm.services.businesslogic.TopicService;
import ua.rd.cm.services.businesslogic.TypeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebTestConfig.class, WebMvcConfig.class, TestSecurityConfig.class})
@WebAppConfiguration
public class MainPageControllerTest extends TestUtil {
    public static final String API_CONFERENCE = "/conference";
    public static final String API_LEVEL = "/level";
    public static final String API_TOPIC = "/topic";
    public static final String API_TYPE = "/type";

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ConferenceService conferenceService;

    @Autowired
    private TypeService typeService;
    @Autowired
    private TopicService topicService;
    @Autowired
    private LevelService levelService;
    @Autowired
    private Filter springSecurityFilterChain;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilter(springSecurityFilterChain)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void getUpcomingConferencesUnauthorized() throws Exception {
        mockMvc.perform(prepareGetRequest(API_CONFERENCE + "/upcoming")).
                andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = ORGANISER_EMAIL, roles = ADMIN_ROLE)
    public void getUpcomingConferencesWithNoTalks() throws Exception {
        List<Conference> conferences = new ArrayList<>();
        conferences.add(new Conference());
        List<ConferenceDto> conferencesDto =new ArrayList<>();
        List<ConferenceDtoBasic> conferenceDtoBasics = new ArrayList<>();
        conferenceDtoBasics.add(new ConferenceDtoBasic());
        conferencesDto.add(new ConferenceDto());
        when(conferenceService.findUpcoming()).thenReturn(conferences);
        when(conferenceService.conferenceListToDto(conferences)).thenReturn(conferencesDto);
        when(conferenceService.conferenceListToDtoBasic(conferences)).thenReturn(conferenceDtoBasics);
        mockMvc.perform(prepareGetRequest(API_CONFERENCE + "/upcoming")).
                andExpect(status().isOk()).
                andExpect(jsonPath("$[0].new", is(0))).
                andExpect(jsonPath("$[0].approved", is(0))).
                andExpect(jsonPath("$[0].in-progress", is(0))).
                andExpect(jsonPath("$[0].rejected", is(0)));
    }

    @Test
    @WithMockUser(username = ORGANISER_EMAIL, roles = ORGANISER_ROLE)
    public void getUpcomingConferencesWithTalks() throws Exception {
        List<Conference> conferences = new ArrayList<>();
        Conference conference = createConference();
        conference.setCallForPaperEndDate(LocalDate.MAX);
        conferences.add(conference);
        when(conferenceService.findUpcoming()).thenReturn(conferences);
        mockMvc.perform(prepareGetRequest(API_CONFERENCE + "/upcoming")).
                andExpect(status().isOk());
    }

    @Test
    public void getPastConferencesTestUnauthorized() throws Exception {
        mockMvc.perform(prepareGetRequest(API_CONFERENCE + "/past")).
                andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = SPEAKER_ROLE)
    public void getConferenceById() throws Exception {
        mockMvc.perform(prepareGetRequest("/conference/" + anyInt())).
            andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = ORGANISER_EMAIL, roles = ORGANISER_ROLE)
    public void getPastConferences() throws Exception {
        List<Conference> conferences = new ArrayList<>();
        conferences.add(createConference());
        when(conferenceService.findPast()).thenReturn(conferences);
        mockMvc.perform(prepareGetRequest(API_CONFERENCE + "/past")).
                andExpect(status().isOk());
    }

    @WithMockUser(roles = ADMIN_ROLE)
    public void createNewTypeShouldWorkForAdmin() throws Exception {
        CreateTypeDto dto = new CreateTypeDto("schweine");
        when(typeService.save(dto)).thenReturn(1L);
        mockMvc.perform(post(API_TYPE)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsBytes(dto))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)));
    }

    @Test
    public void createNewTypeShouldNotWorkForUnauthorized() throws Exception {
        CreateTypeDto dto = new CreateTypeDto("schweine");
        mockMvc.perform(post(API_TYPE)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsBytes(dto))
        ).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = ORGANISER_ROLE)
    public void createNewTypeShouldNotWorkForOrganiser() throws Exception {
        CreateTypeDto dto = new CreateTypeDto("schweine");
        mockMvc.perform(post(API_TYPE)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsBytes(dto))
        ).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = SPEAKER_ROLE)
    public void createNewTypeShouldNotWorkForSpeaker() throws Exception {
        CreateTypeDto dto = new CreateTypeDto("schweine");
        mockMvc.perform(post(API_TYPE)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsBytes(dto))
        ).andExpect(status().isUnauthorized());
    }
  
    @WithMockUser(roles = ADMIN_ROLE)
    public void createNewTopicShouldWorkForAdmin() throws Exception {
        CreateTopicDto dto = new CreateTopicDto("schweine");
        when(topicService.save(dto)).thenReturn(1L);
        mockMvc.perform(post(API_TOPIC)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsBytes(dto))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)));
    }

    @Test
    public void createNewTopicShouldNotWorkForUnauthorized() throws Exception {
        CreateTopicDto dto = new CreateTopicDto("schweine");
        mockMvc.perform(post(API_TOPIC)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsBytes(dto))
        ).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = ORGANISER_ROLE)
    public void createNewTopicShouldNotWorkForOrganiser() throws Exception {
        CreateTopicDto dto = new CreateTopicDto("schweine");
        mockMvc.perform(post(API_TOPIC)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsBytes(dto))
        ).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = SPEAKER_ROLE)
    public void createNewTopicShouldNotWorkForSpeaker() throws Exception {
        CreateTopicDto dto = new CreateTopicDto("schweine");
        mockMvc.perform(post(API_TOPIC)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsBytes(dto))
        ).andExpect(status().isUnauthorized());
    }

    private MockHttpServletRequestBuilder prepareGetRequest(String uri) {
        return MockMvcRequestBuilders.get(uri)
                .contentType(MediaType.APPLICATION_JSON_UTF8);
    }

    private Conference createConference() {
        Conference conference = new Conference();
        conference.setTitle("JUG UA");
        conference.setLocation("Location");
        conference.setDescription("Description");
        conference.setCallForPaperEndDate(LocalDate.MIN);
        List<Talk> talks = new ArrayList<>();
        Talk talk1 = new Talk();
        talk1.setStatus(TalkStatus.APPROVED);
        talks.add(talk1);
        talk1 = new Talk();
        talk1.setStatus(TalkStatus.APPROVED);
        talks.add(talk1);
        talk1 = new Talk();
        talk1.setStatus(TalkStatus.IN_PROGRESS);
        talks.add(talk1);
        conference.setTalks(talks);
        return conference;
    }
}
