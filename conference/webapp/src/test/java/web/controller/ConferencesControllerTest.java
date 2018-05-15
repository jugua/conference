package web.controller;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
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

import domain.model.Conference;
import domain.model.Role;
import service.businesslogic.api.ConferenceService;
import service.businesslogic.dto.ConferenceDto;
import service.businesslogic.dto.ConferenceDtoBasic;
import web.config.TestConfig;
import web.config.WebMvcConfig;
import web.util.TestData;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class, WebMvcConfig.class})
@WebAppConfiguration
public class ConferencesControllerTest {

    private static final String API_CONFERENCE = "/conference";
    public static final String API_LEVEL = "/level";

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ConferenceService conferenceService;

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
        conference = TestData.conference();
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
        conferences.add(TestData.conference());

        when(conferenceService.findPast()).thenReturn(conferencesDto);

        mockMvc.perform(prepareGetRequest(API_CONFERENCE + "/past")).
                andExpect(status().isOk());
    }

    private MockHttpServletRequestBuilder prepareGetRequest(String uri) {
        return MockMvcRequestBuilders.get(uri)
                .contentType(MediaType.APPLICATION_JSON_UTF8);
    }

}
