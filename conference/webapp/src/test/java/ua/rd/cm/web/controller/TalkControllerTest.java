package ua.rd.cm.web.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.rd.cm.infrastructure.fileStorage.exception.FileValidationException.UNSUPPORTED_MEDIA_TYPE;
import static ua.rd.cm.services.exception.TalkValidationException.NOT_ALLOWED_TO_UPDATE;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.Filter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ua.rd.cm.config.TestSecurityConfig;
import ua.rd.cm.config.WebMvcConfig;
import ua.rd.cm.config.WebTestConfig;
import ua.rd.cm.domain.Role;
import ua.rd.cm.domain.Talk;
import ua.rd.cm.domain.User;
import ua.rd.cm.domain.UserInfo;
import ua.rd.cm.dto.MessageDto;
import ua.rd.cm.dto.TalkDto;
import ua.rd.cm.infrastructure.fileStorage.FileStorageService;
import ua.rd.cm.services.businesslogic.TalkService;
import ua.rd.cm.services.businesslogic.UserInfoService;
import ua.rd.cm.services.businesslogic.UserService;
import ua.rd.cm.infrastructure.fileStorage.exception.FileValidationException;
import ua.rd.cm.services.exception.ResourceNotFoundException;
import ua.rd.cm.services.exception.TalkNotFoundException;
import ua.rd.cm.services.exception.TalkValidationException;
import ua.rd.cm.infrastructure.fileStorage.impl.FileStorageServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebTestConfig.class, WebMvcConfig.class, TestSecurityConfig.class})
@WebAppConfiguration
public class TalkControllerTest extends TestUtil {
    private static final String MY_TALKS_PAGE_URL = "/talk";
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
    @Autowired
    private FileStorageService fileStorageService;

    private MockMvc mockMvc;

    private User speakerUser;
    private User organiserUser;
    private TalkDto correctTalkDto;
    private MockMultipartFile multipartFile;

    MockHttpServletRequestBuilder requestBuilder;

    @Before
    public void setUp() {
        correctTalkDto = setupCorrectTalkDto();
        multipartFile = createMultipartFile();
        requestBuilder = fileUpload(MY_TALKS_PAGE_URL).
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
        speakerRole.add(new Role(2L, Role.SPEAKER));
        speakerUser = new User();
        speakerUser.setId(1L);
        speakerUser.setFirstName("Olya");
        speakerUser.setLastName("Ivanova");
        speakerUser.setEmail("ivanova@gmail.com");
        speakerUser.setPassword("123456");
        speakerUser.setStatus(User.UserStatus.CONFIRMED);
        speakerUser.setUserInfo(userInfo);
        speakerUser.setRoles(speakerRole);

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
        organiserUser.setRoles(organiserRole);

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
        mockMvc.perform(prepareGetRequest(MY_TALKS_PAGE_URL))
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
        mockMvc.perform(prepareGetRequest(MY_TALKS_PAGE_URL))
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
        mockMvc.perform(prepareGetRequest(MY_TALKS_PAGE_URL))
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

        mockMvc.perform(prepareGetRequest(MY_TALKS_PAGE_URL + "/" + 1))
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
        mockMvc.perform(prepareGetRequest(MY_TALKS_PAGE_URL + "/" + 1))
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
        mockMvc.perform(prepareGetRequest(MY_TALKS_PAGE_URL + "/" + 1)).
                andExpect(status().isNotFound())
                .andExpect(jsonPath("error", is(ResourceNotFoundException.TALK_NOT_FOUND)));
    }

    /**
     * Test for updateTalk() method
     *
     * @throws Exception
     */
    @Test
    @WithMockUser(username = ORGANISER_EMAIL, roles = ORGANISER_ROLE)
    public void testSuccessfulUpdateTalkAsOrganiser() throws Exception {
        TalkDto talkDto = correctTalkDto;
        talkDto.setOrganiserComment("comment");
        talkDto.setStatusName(APPROVED);

        mockMvc.perform(preparePatchRequest(MY_TALKS_PAGE_URL + "/" + 1L, talkDto))
                .andExpect(status().isOk())
                .andExpect(jsonPath("result", is("successfully_updated")));
        verify(talkService, atLeastOnce()).updateAsOrganiser(talkDto, organiserUser);
    }

    /**
     * Test for updateTalk() method
     *
     * @throws Exception
     */
    @Test
    @WithMockUser(username = SPEAKER_EMAIL, roles = SPEAKER_ROLE)
    public void testSuccessfulUpdateTalkAsSpeaker() throws Exception {
        TalkDto talkDto = correctTalkDto;
        talkDto.setOrganiserComment("comment");
        talkDto.setStatusName(APPROVED);

        mockMvc.perform(preparePatchRequest(MY_TALKS_PAGE_URL + "/" + 1L, talkDto))
                .andExpect(status().isOk())
                .andExpect(jsonPath("result", is("successfully_updated")));
        verify(talkService, atLeastOnce()).updateAsSpeaker(talkDto, speakerUser);
    }

    /**
     * Test for updateTalk() method
     *
     * @throws Exception
     */
    @Test
    public void testUnauthorizedErrorWhenUpdateTalk() throws Exception {
        mockMvc.perform(preparePatchRequest(MY_TALKS_PAGE_URL + "/" + 1, correctTalkDto))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("error", is(ApplicationControllerAdvice.UNAUTHORIZED_MSG)));
    }

    /**
     * Test updateTalk() by speaker with TalkValidationException
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

        mockMvc.perform(preparePatchRequest(MY_TALKS_PAGE_URL + "/" + 1L, talkDto))
                .andExpect(status().isPayloadTooLarge())
                .andExpect(jsonPath("error", is(TalkValidationException.ADDITIONAL_COMMENT_TOO_LONG)));
    }

    /**
     * Test updateTalk() with TalkNotFoundException
     *
     * @throws Exception
     */
    @Test
    @WithMockUser(username = ORGANISER_EMAIL, roles = ORGANISER_ROLE)
    public void testTalkNotFoundExceptionUserActionOnTalkAsOrganiser() throws Exception {
        doThrow(new TalkNotFoundException()).when(talkService).updateAsOrganiser(correctTalkDto, organiserUser);

        mockMvc.perform(preparePatchRequest(MY_TALKS_PAGE_URL + "/" + 1, correctTalkDto))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("error", is(ResourceNotFoundException.TALK_NOT_FOUND)));
    }

    @Test
    @WithMockUser(username = SPEAKER_EMAIL, roles = SPEAKER_ROLE)
    public void testTakeFile() throws Exception {
        when(talkService.findById(1L)).thenReturn(correctTalkDto);

        String filePath = "file path";
        when(talkService.getFilePath(correctTalkDto)).thenReturn(filePath);

        File file = new File("src/test/resources/trybel_master.JPG");
        when(fileStorageService.getFile(filePath)).thenReturn(file);

        String mimeType = MediaType.IMAGE_PNG_VALUE;
        when(fileStorageService.getFileTypeIfSupported(file)).thenReturn(mimeType);

        mockMvc.perform(prepareGetRequest(MY_TALKS_PAGE_URL + "/1/takeFile"))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = SPEAKER_EMAIL, roles = SPEAKER_ROLE)
    public void testTakeFileWithIOException() throws Exception {
        when(talkService.findById(1L)).thenReturn(correctTalkDto);

        String filePath = "file path";
        when(talkService.getFilePath(correctTalkDto)).thenReturn(filePath);

        File file = new File("wrong path");
        when(fileStorageService.getFile(filePath)).thenReturn(file);

        String mimeType = MediaType.IMAGE_PNG_VALUE;
        when(fileStorageService.getFileTypeIfSupported(file)).thenReturn(mimeType);

        mockMvc.perform(prepareGetRequest(MY_TALKS_PAGE_URL + "/1/takeFile"))
                .andExpect(status().isBadRequest());

    }

    @Test
    @WithMockUser(username = SPEAKER_EMAIL, roles = SPEAKER_ROLE)
    public void testFileUpload() throws Exception {
        when(talkService.findById(1L)).thenReturn(correctTalkDto);

        String filePath = "file path";
        when(fileStorageService.saveFile(multipartFile, FileStorageServiceImpl.FileType.FILE)).thenReturn(filePath);

        mockMvc.perform(fileUpload(MY_TALKS_PAGE_URL + "/1/uploadFile")
                .file(multipartFile))
                .andExpect(status().isOk());

        verify(talkService, times(1)).addFile(correctTalkDto, filePath);
    }

    @Test
    @WithMockUser(username = SPEAKER_EMAIL, roles = SPEAKER_ROLE)
    public void testFileDelete() throws Exception {
        when(talkService.findById(1L)).thenReturn(correctTalkDto);

        String filePath = "file path";
        when(talkService.getFilePath(correctTalkDto)).thenReturn(filePath);

        mockMvc.perform(delete(MY_TALKS_PAGE_URL + "/1/deleteFile"))
                .andExpect(status().isOk());

        verify(fileStorageService, times(1)).deleteFile(filePath);
        verify(talkService, times(1)).deleteFile(correctTalkDto, true);
    }

    @Test
    @WithMockUser(username = SPEAKER_EMAIL, roles = SPEAKER_ROLE)
    public void testFindFileName() throws Exception {
        Talk talk = new Talk();
        String filePath = "file path";

        talk.setPathToAttachedFile(filePath);
        when(talkService.findTalkById(1L)).thenReturn(talk);

        File file = new File("wrong path");
        when(fileStorageService.getFile(filePath)).thenReturn(file);

        mockMvc.perform(get(MY_TALKS_PAGE_URL + "/1/takeFileName"))
                .andExpect(status().isOk());

    }

    @Test
    public void handleTalkNotFoundCorrectStatusAndMessage() throws Exception {
        ResponseEntity<MessageDto> response = talkController.handleResourceNotFound(new TalkNotFoundException());
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
        assertThat(response.getBody().getError(), is(ResourceNotFoundException.TALK_NOT_FOUND));

    }

    @Test
    public void handleTalkValidExceptionCorrectStatusAndMessage() throws Exception {
        ResponseEntity<MessageDto> response = talkController.handleTalkValidationException(new TalkValidationException(NOT_ALLOWED_TO_UPDATE));
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
        assertThat(response.getBody().getError(), is(NOT_ALLOWED_TO_UPDATE));

    }

    @Test
    public void handleFileValidExceptionCorrectStatusAndMessage() throws Exception {
        ResponseEntity<MessageDto> response = talkController.handleFileValidationException(new FileValidationException(UNSUPPORTED_MEDIA_TYPE));
        assertThat(response.getStatusCode(), is(HttpStatus.UNSUPPORTED_MEDIA_TYPE));
        assertThat(response.getBody().getError(), is(UNSUPPORTED_MEDIA_TYPE));

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

    private MockMultipartFile createMultipartFile() {
        try {
            File file = new File("src/test/resources/trybel_master.JPG");
            FileInputStream fileInputStream = new FileInputStream(file);
            return new MockMultipartFile("file", fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private byte[] convertObjectToJsonBytes(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

}


