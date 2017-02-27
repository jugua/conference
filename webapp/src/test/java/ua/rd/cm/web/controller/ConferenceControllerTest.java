package ua.rd.cm.web.controller;

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
import ua.rd.cm.config.TestSecurityConfig;
import ua.rd.cm.config.WebMvcConfig;
import ua.rd.cm.config.WebTestConfig;
import ua.rd.cm.domain.Conference;
import ua.rd.cm.domain.Talk;
import ua.rd.cm.domain.TalkStatus;
import ua.rd.cm.services.ConferenceService;

import javax.servlet.Filter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Anastasiia_Milinchuk on 2/9/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebTestConfig.class, WebMvcConfig.class, TestSecurityConfig.class })
@WebAppConfiguration
public class ConferenceControllerTest extends TestUtil {

    public static final String API_CONFERENCE = "/api/conference";

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ConferenceService conferenceService;

    @Autowired
    private Filter springSecurityFilterChain;

    @Autowired
    private ConferenceController conferenceController;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(conferenceController).build();
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
        when(conferenceService.findUpcoming()).thenReturn(conferences);
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
    @WithMockUser(username = ORGANISER_EMAIL, roles = ORGANISER_ROLE)
    public void getPastConferences() throws Exception {
        List<Conference> conferences = new ArrayList<>();
        conferences.add(createConference());
        when(conferenceService.findPast()).thenReturn(conferences);
        mockMvc.perform(prepareGetRequest(API_CONFERENCE + "/past")).
                andExpect(status().isOk());
    }

    private MockHttpServletRequestBuilder prepareGetRequest(String uri) throws Exception{
        return MockMvcRequestBuilders.get(uri)
                .contentType(MediaType.APPLICATION_JSON_UTF8);
    }

    private Conference createConference(){
        Conference conference = new Conference();
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
