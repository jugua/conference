package ua.rd.cm.web.controller;

import lombok.extern.log4j.Log4j;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ua.rd.cm.config.TestSecurityConfig;
import ua.rd.cm.config.WebMvcConfig;
import ua.rd.cm.config.WebTestConfig;
import ua.rd.cm.domain.User;
import ua.rd.cm.dto.MessageDto;
import ua.rd.cm.dto.PhotoDto;
import ua.rd.cm.services.FileStorageService;
import ua.rd.cm.services.UserService;
import ua.rd.cm.services.exception.FileValidationException;
import ua.rd.cm.services.exception.TalkNotFoundException;

import javax.servlet.Filter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLConnection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.rd.cm.services.impl.FileStorageServiceImpl.FileType.PHOTO;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebTestConfig.class, WebMvcConfig.class, TestSecurityConfig.class})
@WebAppConfiguration
@Log4j
public class MyInfoControllerTest extends TestUtil {
    private static final String API_PHOTO = "/api/user/current/photo";
    private static final String SPEAKER_EMAIL = "ivanova@gmail.com";
    public static final String SPEAKER_ROLE = "SPEAKER";
    private User user;


    @Autowired
    private UserService userService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private MyInfoController myInfoController;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private Filter springSecurityFilterChain;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilter(springSecurityFilterChain)
                .apply(springSecurity())
                .build();
        user = createUser();
        when(userService.getByEmail(SPEAKER_EMAIL)).thenReturn(user);
    }
    @After
    public void resetMocks() {
        Mockito.reset(fileStorageService, userService);
    }

    @Test
    @WithMockUser(username = SPEAKER_EMAIL, roles = SPEAKER_ROLE)
    public void testSuccessfulUploadPhoto() throws Exception {
        MockMultipartFile multipartFile = setupCorrectMultipartFile();
        String previousPhotoPath = "previous photo pass";
        String newPhotoPath = "new photo pass";
        when(userService.getByEmail(SPEAKER_EMAIL)).thenReturn(user);
        user.setPhoto(previousPhotoPath);
        when(fileStorageService.saveFile(multipartFile, PHOTO)).thenReturn(newPhotoPath);

        mockMvc.perform(fileUpload(API_PHOTO)
                .file(multipartFile)
                .contentType(MediaType.IMAGE_JPEG)
        ).andExpect(status().isOk())
                .andExpect(jsonPath("result", is("api/user/current/photo/" + user.getId())));

        verify(fileStorageService, times(1)).deleteFile(previousPhotoPath);
        verify(userService, times(1)).updateUserProfile(user);
        assertTrue(user.getPhoto().equals(newPhotoPath));
    }

    @Test
    @WithMockUser(username = SPEAKER_EMAIL, roles = SPEAKER_ROLE)
    public void testUnsuccessfulUploadPhoto() throws Exception {
        MockMultipartFile multipartFile = setupCorrectMultipartFile();
        String previousPhotoPath = "previous photo pass";
        user.setPhoto(previousPhotoPath);
        when(fileStorageService.saveFile(multipartFile, PHOTO)).thenReturn("");

        mockMvc.perform(fileUpload(API_PHOTO)
                .file(multipartFile)
                .contentType(MediaType.IMAGE_JPEG)
        ).andExpect(status().isNotFound());

        verify(fileStorageService, never()).deleteFile(previousPhotoPath);
        verify(userService, never()).updateUserProfile(user);
    }

    @Test
    @WithMockUser(username = SPEAKER_EMAIL, roles = SPEAKER_ROLE)
    public void testSuccessfulDeletePhoto() throws Exception {
        user.setPhoto("photo");

        mockMvc.perform(delete(API_PHOTO))
                .andExpect(status().isOk());

        verify(fileStorageService, times(1)).deleteFile("photo");
        assertTrue(user.getPhoto() == null);
        verify(userService, times(1)).updateUserProfile(user);
    }

    @Test
    @WithMockUser(username = SPEAKER_EMAIL, roles = SPEAKER_ROLE)
    public void testGet() throws Exception {
        when(userService.find(anyLong())).thenReturn(user);

        File file = new File("src/test/resources/trybel_master.JPG");
        String mimeType = MediaType.IMAGE_PNG_VALUE;

        when(fileStorageService.getFile("api/user/current/photo/")).thenReturn(file);
        when((fileStorageService.getPhotoTypeIfSupported(file))).thenReturn(mimeType);

        mockMvc.perform(get(API_PHOTO + "/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = SPEAKER_EMAIL, roles = SPEAKER_ROLE)
    public void testGetFileNotFound() throws Exception {
        when(userService.find(anyLong())).thenReturn(user);

        File file = new File("wrong path");
        String mimeType = MediaType.IMAGE_PNG_VALUE;

        when(fileStorageService.getFile("api/user/current/photo/")).thenReturn(file);
        when((fileStorageService.getPhotoTypeIfSupported(file))).thenReturn(mimeType);

        mockMvc.perform(get(API_PHOTO + "/1"))
                .andExpect(status().isBadRequest());
    }

    private PhotoDto setupCorrectPhotoDto() {
        PhotoDto photoDto = new PhotoDto();
        try {
            File file = new File("src/test/resources/trybel_master.JPG");
            FileInputStream fileInputStream = new FileInputStream(file);
            MockMultipartFile mockFile = new MockMultipartFile("trybel_master", fileInputStream);
            photoDto.setFile(mockFile);
        } catch (IOException e) {
            log.info(e);
        }
        return photoDto;
    }


    @Test
    public void handleTalkNotFoundCorrectStatus() throws Exception {
        ResponseEntity<MessageDto> response = myInfoController.handleFileValidationException(new FileValidationException(FileValidationException.UNSUPPORTED_MEDIA_TYPE));
        assertThat(response.getStatusCode(), is(HttpStatus.UNSUPPORTED_MEDIA_TYPE));
    }

    private MockMultipartFile setupCorrectMultipartFile() {
        try {
            File file = new File("src/test/resources/trybel_master.JPG");
            FileInputStream fileInputStream = new FileInputStream(file);
            MockMultipartFile mockFile = new MockMultipartFile("file", fileInputStream);
            return mockFile;
        } catch (IOException e) {
            log.info(e);
        }
        return null;
    }
}

