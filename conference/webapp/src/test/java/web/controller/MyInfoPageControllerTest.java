package web.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static domain.model.Role.SPEAKER;
import static service.infrastructure.fileStorage.impl.FileStorageServiceImpl.FileType.PHOTO;
import static web.util.TestData.SPEAKER_EMAIL;
import static web.util.TestData.speaker;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.Principal;

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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import domain.model.User;
import lombok.extern.log4j.Log4j;
import service.businesslogic.api.UserService;
import service.businesslogic.dto.MessageDto;
import service.businesslogic.dto.PhotoDto;
import service.businesslogic.dto.UserInfoDto;
import service.infrastructure.fileStorage.FileStorageService;
import service.infrastructure.fileStorage.exception.FileValidationException;
import web.config.TestConfig;
import web.config.WebMvcConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class, WebMvcConfig.class})
@WebAppConfiguration
@Log4j
public class MyInfoPageControllerTest {
    private static final String API_PHOTO = "/myinfo/photo";
    private static final String API_USER_CURRENT = "/myinfo";

    private User user;
    private UserInfoDto correctUserInfoDto;

    @Autowired
    private UserService userService;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private MyInfoPageController myInfoPageController;

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
        user = speaker();
        correctUserInfoDto = setupCorrectUserInfoDto();
        when(userService.getByEmail(SPEAKER_EMAIL)).thenReturn(user);
    }

    @After
    public void resetMocks() {
        Mockito.reset(fileStorageService, userService);
    }

    @Test
    @WithMockUser(username = SPEAKER_EMAIL, roles = SPEAKER)
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
                .andExpect(jsonPath("result", is("/photo/" + user.getId())));

        verify(fileStorageService, times(1)).deleteFile(previousPhotoPath);
        verify(userService, times(1)).updateUser(user);
        assertEquals(user.getPhoto(), newPhotoPath);
    }

    @Test
    @WithMockUser(username = SPEAKER_EMAIL, roles = SPEAKER)
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
        verify(userService, never()).updateUser(user);
    }

    @Test
    @WithMockUser(username = SPEAKER_EMAIL, roles = SPEAKER)
    public void testSuccessfulDeletePhoto() throws Exception {
        user.setPhoto("photo");

        mockMvc.perform(delete(API_PHOTO))
                .andExpect(status().isOk());

        verify(fileStorageService, times(1)).deleteFile("photo");
        assertNull(user.getPhoto());
        verify(userService, times(1)).updateUser(user);
    }

    @Test
    @WithMockUser(username = SPEAKER_EMAIL, roles = SPEAKER)
    public void testGet() throws Exception {
        when(userService.getById(anyLong())).thenReturn(user);

        File file = new File("src/test/resources/trybel_master.JPG");
        String mimeType = MediaType.IMAGE_PNG_VALUE;

        when(fileStorageService.getFile("myinfo/photo/")).thenReturn(file);
        when((fileStorageService.getPhotoTypeIfSupported(file))).thenReturn(mimeType);

        mockMvc.perform(get(API_PHOTO + "/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = SPEAKER_EMAIL, roles = SPEAKER)
    public void testGetFileNotFound() throws Exception {
        when(userService.getById(anyLong())).thenReturn(user);

        File file = new File("wrong path");
        String mimeType = MediaType.IMAGE_PNG_VALUE;

        when(fileStorageService.getFile("myinfo/photo/")).thenReturn(file);
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
    @WithMockUser(username = SPEAKER_EMAIL, roles = SPEAKER)
    public void correctPrincipalGetCurrentUserTest() throws Exception {
        User user = speaker();
        Principal correctPrincipal = user::getEmail;

        when(userService.getUserInfoDtoByEmail(correctPrincipal.getName()))
                .thenReturn(setupCorrectUserInfoDto());

        mockMvc.perform(get(API_USER_CURRENT)
                .principal(correctPrincipal)
        ).andExpect(status().isAccepted());
    }

    @Test
    @WithMockUser(username = SPEAKER_EMAIL, roles = SPEAKER)
    public void correctFillUserInfoTest() throws Exception {
        User user = speaker();
        Principal correctPrincipal = user::getEmail;

        when(userService.getByEmail(user.getEmail())).thenReturn(user);

        mockMvc.perform(post(API_USER_CURRENT)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(correctUserInfoDto))
                .principal(correctPrincipal)
        ).andExpect(status().isOk());
    }

    @Test
    public void incorrectPrincipalFillInfoTest() throws Exception {
        mockMvc.perform(post(API_USER_CURRENT)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(correctUserInfoDto))
        ).andExpect(status().isUnauthorized());
    }

    @Test
    public void nullBioTest() {
        correctUserInfoDto.setUserInfoShortBio(null);
        checkForBadRequest(API_USER_CURRENT, RequestMethod.POST, correctUserInfoDto);
    }

    @Test
    public void tooLongBioTest() {
        correctUserInfoDto.setUserInfoShortBio(createStringWithLength(2001));
        checkForBadRequest(API_USER_CURRENT, RequestMethod.POST, correctUserInfoDto);
    }

    @Test
    public void nullJobTest() {
        correctUserInfoDto.setUserInfoJobTitle(null);
        checkForBadRequest(API_USER_CURRENT, RequestMethod.POST, correctUserInfoDto);
    }

    @Test
    public void tooLongJobTest() {
        correctUserInfoDto.setUserInfoJobTitle(createStringWithLength(257));
        checkForBadRequest(API_USER_CURRENT, RequestMethod.POST, correctUserInfoDto);
    }

    @Test
    public void nullCompanyTest() {
        correctUserInfoDto.setUserInfoCompany(null);
        checkForBadRequest(API_USER_CURRENT, RequestMethod.POST, correctUserInfoDto);
    }


    @Test
    public void tooLongCompanyTest() {
        correctUserInfoDto.setUserInfoCompany(createStringWithLength(257));
        checkForBadRequest(API_USER_CURRENT, RequestMethod.POST, correctUserInfoDto);
    }

    @Test
    public void tooLongPastConferenceTest() {
        correctUserInfoDto.setUserInfoPastConference(createStringWithLength(1001));
        checkForBadRequest(API_USER_CURRENT, RequestMethod.POST, correctUserInfoDto);
    }

    @Test
    public void tooLongAdditionalInfoTest() {
        correctUserInfoDto.setUserInfoAdditionalInfo(createStringWithLength(1001));
        checkForBadRequest(API_USER_CURRENT, RequestMethod.POST, correctUserInfoDto);
    }

    @Test
    public void handleTalkNotFoundCorrectStatus() {
        ResponseEntity<MessageDto> response = myInfoPageController.handleFileValidationException(new FileValidationException(FileValidationException.UNSUPPORTED_MEDIA_TYPE));
        assertThat(response.getStatusCode(), is(HttpStatus.UNSUPPORTED_MEDIA_TYPE));
    }

    private UserInfoDto setupCorrectUserInfoDto() {
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setUserInfoShortBio("bio");
        userInfoDto.setUserInfoJobTitle("job");
        userInfoDto.setUserInfoCompany("company");
        return userInfoDto;
    }

    private byte[] convertObjectToJsonBytes(Object object) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

    private void checkForBadRequest(String uri, RequestMethod method, Object dto) {
        try {
            if (method == RequestMethod.GET) {
                mockMvc.perform(get(uri)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(convertObjectToJsonBytes(dto))
                ).andExpect(status().isBadRequest());
            } else if (method == RequestMethod.POST) {
                mockMvc.perform(post(uri)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(convertObjectToJsonBytes(dto))
                ).andExpect(status().isBadRequest());
            }
        } catch (Exception e) {
            log.info(e);
        }
    }

    private String createStringWithLength(int length) {
        StringBuilder builder = new StringBuilder();
        for (int index = 0; index < length; index++) {
            builder.append("a");
        }
        return builder.toString();
    }

    private MockMultipartFile setupCorrectMultipartFile() {
        try {
            File file = new File("src/test/resources/trybel_master.JPG");
            FileInputStream fileInputStream = new FileInputStream(file);
            return new MockMultipartFile("file", fileInputStream);
        } catch (IOException e) {
            log.info(e);
        }
        return null;
    }
}

