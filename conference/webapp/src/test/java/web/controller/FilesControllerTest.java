package web.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.Filter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import service.businesslogic.api.TalkService;
import service.businesslogic.dto.TalkDto;
import service.infrastructure.fileStorage.FileStorageService;
import service.infrastructure.fileStorage.impl.FileStorageServiceImpl;
import web.config.TestConfig;
import web.config.WebMvcConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class, WebMvcConfig.class})
@WebAppConfiguration
public class FilesControllerTest {

    private static final String MY_TALKS_PAGE_URL = "/talk";
    private static final String SPEAKER_EMAIL = "ivanova@gmail.com";
    private static final long TEST_TALK_ID = 1L;

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private Filter springSecurityFilterChain;

    @Autowired
    private TalkService talkService;
    @Autowired
    private FileStorageService fileStorageService;

    private MockMvc mockMvc;

    private TalkDto correctTalkDto;
    private MockMultipartFile multipartFile;

    @Before
    public void setUp() throws IOException {
        multipartFile = createMultipartFile();
        correctTalkDto = setupCorrectTalkDto(multipartFile);

        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilter(springSecurityFilterChain)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(username = SPEAKER_EMAIL, roles = Role.SPEAKER)
    public void testTakeFile() throws Exception {
        when(talkService.findById(TEST_TALK_ID)).thenReturn(correctTalkDto);

        String filePath = "file path";
        when(talkService.getFilePath(correctTalkDto)).thenReturn(filePath);

        File file = new File("src/test/resources/trybel_master.JPG");
        when(fileStorageService.getFile(filePath)).thenReturn(file);

        String mimeType = MediaType.IMAGE_PNG_VALUE;
        when(fileStorageService.getFileTypeIfSupported(file)).thenReturn(mimeType);

        mockMvc.perform(prepareGetRequest(MY_TALKS_PAGE_URL + "/" + TEST_TALK_ID + "/takeFile"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = SPEAKER_EMAIL, roles = Role.SPEAKER)
    public void testTakeFileWithIOException() throws Exception {
        when(talkService.findById(TEST_TALK_ID)).thenReturn(correctTalkDto);

        String filePath = "file path";
        when(talkService.getFilePath(correctTalkDto)).thenReturn(filePath);

        File file = new File("wrong path");
        when(fileStorageService.getFile(filePath)).thenReturn(file);

        String mimeType = MediaType.IMAGE_PNG_VALUE;
        when(fileStorageService.getFileTypeIfSupported(file)).thenReturn(mimeType);

        mockMvc.perform(prepareGetRequest(MY_TALKS_PAGE_URL + "/" + TEST_TALK_ID + "/takeFile"))
                .andExpect(status().isBadRequest());

    }

    @Test
    @WithMockUser(username = SPEAKER_EMAIL, roles = Role.SPEAKER)
    public void testFileUpload() throws Exception {
        when(talkService.findById(TEST_TALK_ID)).thenReturn(correctTalkDto);

        String filePath = "file path";
        when(fileStorageService.saveFile(multipartFile, FileStorageServiceImpl.FileType.FILE)).thenReturn(filePath);

        mockMvc.perform(fileUpload(MY_TALKS_PAGE_URL + "/" + TEST_TALK_ID + "/uploadFile")
                .file(multipartFile))
                .andExpect(status().isOk());

        verify(talkService, times(1)).addFile(correctTalkDto, filePath);
    }

    @Test
    @WithMockUser(username = SPEAKER_EMAIL, roles = Role.SPEAKER)
    public void testFileDelete() throws Exception {
        when(talkService.findById(TEST_TALK_ID)).thenReturn(correctTalkDto);

        String filePath = "file path";
        when(talkService.getFilePath(correctTalkDto)).thenReturn(filePath);

        mockMvc.perform(delete(MY_TALKS_PAGE_URL + "/" + TEST_TALK_ID + "/deleteFile"))
                .andExpect(status().isOk());

        verify(fileStorageService, times(1)).deleteFile(filePath);
        verify(talkService, times(1)).deleteFile(correctTalkDto, true);
    }

    @Test
    @WithMockUser(username = SPEAKER_EMAIL, roles = Role.SPEAKER)
    public void testFindFileName() throws Exception {
        Talk talk = new Talk();
        String filePath = "file path";

        talk.setPathToAttachedFile(filePath);
        when(talkService.findTalkById(TEST_TALK_ID)).thenReturn(talk);

        File file = new File("wrong path");
        when(fileStorageService.getFile(filePath)).thenReturn(file);

        mockMvc.perform(get(MY_TALKS_PAGE_URL + "/" + TEST_TALK_ID + "/takeFileName"))
                .andExpect(status().isOk());
    }

    private MockMultipartFile createMultipartFile() throws IOException {
        File file = new File("src/test/resources/trybel_master.JPG");
        FileInputStream fileInputStream = new FileInputStream(file);
        return new MockMultipartFile("file", fileInputStream);
    }

    private MockHttpServletRequestBuilder prepareGetRequest(String uri) {
        return MockMvcRequestBuilders.get(uri)
                .contentType(MediaType.APPLICATION_JSON_UTF8);
    }

    private TalkDto setupCorrectTalkDto(MockMultipartFile multipartFile) {
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
        correctTalkDto.setMultipartFile(multipartFile);
        return correctTalkDto;
    }

}