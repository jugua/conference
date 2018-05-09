package web.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import domain.model.Role;
import service.businesslogic.api.TypeService;
import service.businesslogic.dto.CreateTypeDto;
import web.config.TestConfig;
import web.config.WebMvcConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class, WebMvcConfig.class})
@WebAppConfiguration
public class TalkTypesControllerTest {

    private static final String API_TYPE = "/type";

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private Filter springSecurityFilterChain;

    @Autowired
    private TypeService typeService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilter(springSecurityFilterChain)
                .apply(springSecurity())
                .build();
    }

    @Test
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

}