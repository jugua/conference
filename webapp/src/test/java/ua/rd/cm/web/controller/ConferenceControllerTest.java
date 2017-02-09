package ua.rd.cm.web.controller;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ua.rd.cm.config.TestSecurityConfig;
import ua.rd.cm.config.WebMvcConfig;
import ua.rd.cm.config.WebTestConfig;
import ua.rd.cm.domain.Conference;
import ua.rd.cm.services.ConferenceService;

import javax.servlet.Filter;
import java.util.List;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

/**
 * Created by Anastasiia_Milinchuk on 2/9/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebTestConfig.class, WebMvcConfig.class, TestSecurityConfig.class })
@WebAppConfiguration
public class ConferenceControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

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
    public void getUpcomingConferences() {

    }

    @Test
    public void getPastConferencesTest(){

    }

    @Test
    public void conferenceToDtoTest(){

    }

    @Test
    @Ignore
    public void conferenceToDtoConferenceTest(){

    }
}
