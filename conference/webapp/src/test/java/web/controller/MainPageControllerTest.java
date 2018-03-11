package web.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static web.util.TestData.ORGANISER_EMAIL;

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

import domain.model.Conference;
import domain.model.Role;
import domain.model.Talk;
import domain.model.TalkStatus;
import service.businesslogic.api.ConferenceService;
import service.businesslogic.api.TopicService;
import service.businesslogic.api.TypeService;
import service.businesslogic.dto.ConferenceDto;
import service.businesslogic.dto.ConferenceDtoBasic;
import service.businesslogic.dto.CreateTopicDto;
import service.businesslogic.dto.CreateTypeDto;
import web.config.TestConfig;
import web.config.WebMvcConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class, WebMvcConfig.class})
@WebAppConfiguration
public class MainPageControllerTest {

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
    private Filter springSecurityFilterChain;

    private List<Conference> conferences;
    private List<ConferenceDto> conferencesDto;
    private List<ConferenceDtoBasic> conferenceDtoBasics;
    private Conference conference;
    private ConferenceDtoBasic conferenceDtoBasic;
    private ConferenceDto conferenceDto;

    @Before
    public void setup() {
        conferences = new ArrayList<>();
        conferencesDto = new ArrayList<>();
        conferenceDtoBasics = new ArrayList<>();

        conference = new Conference();
        conferences.add(conference);

        conferenceDtoBasic = new ConferenceDtoBasic();
        conferenceDtoBasics.add(conferenceDtoBasic);

        conferenceDto = new ConferenceDto();
        conferencesDto.add(conferenceDto);

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
    @WithMockUser(username = ORGANISER_EMAIL, roles = Role.ORGANISER)
    public void getUpcomingConferencesWithTalks() throws Exception {
        conference = createConference();
        conference.setCallForPaperEndDate(LocalDate.MAX);
        conferences.add(conference);

        when(conferenceService.findUpcoming()).thenReturn(conferencesDto);

        mockMvc.perform(prepareGetRequest(API_CONFERENCE + "/upcoming")).
                andExpect(status().isOk());
    }

    @Test
    public void getPastConferencesTestUnauthorized() throws Exception {
        mockMvc.perform(prepareGetRequest(API_CONFERENCE + "/past")).
                andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = Role.SPEAKER)
    public void getConferenceById() throws Exception {
        mockMvc.perform(prepareGetRequest("/conference/" + anyInt())).
                andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = ORGANISER_EMAIL, roles = Role.ORGANISER)
    public void getPastConferences() throws Exception {
        conferences = new ArrayList<>();
        conferences.add(createConference());

        when(conferenceService.findPast()).thenReturn(conferencesDto);

        mockMvc.perform(prepareGetRequest(API_CONFERENCE + "/past")).
                andExpect(status().isOk());
    }

    @WithMockUser(roles = Role.ADMIN)
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
    @WithMockUser(roles = Role.ORGANISER)
    public void createNewTypeShouldNotWorkForOrganiser() throws Exception {
        CreateTypeDto dto = new CreateTypeDto("schweine");
        mockMvc.perform(post(API_TYPE)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsBytes(dto))
        ).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = Role.SPEAKER)
    public void createNewTypeShouldNotWorkForSpeaker() throws Exception {
        CreateTypeDto dto = new CreateTypeDto("schweine");
        mockMvc.perform(post(API_TYPE)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsBytes(dto))
        ).andExpect(status().isUnauthorized());
    }

    @WithMockUser(roles = Role.ADMIN)
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
    @WithMockUser(roles = Role.ORGANISER)
    public void createNewTopicShouldNotWorkForOrganiser() throws Exception {
        CreateTopicDto dto = new CreateTopicDto("schweine");
        mockMvc.perform(post(API_TOPIC)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsBytes(dto))
        ).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = Role.SPEAKER)
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
        talk1.setStatus(TalkStatus.ACCEPTED);
        talks.add(talk1);
        talk1 = new Talk();
        talk1.setStatus(TalkStatus.ACCEPTED);
        talks.add(talk1);
        talk1 = new Talk();
        talk1.setStatus(TalkStatus.PENDING);
        talks.add(talk1);
        conference.setTalks(talks);
        return conference;
    }
}
