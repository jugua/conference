package ua.rd.cm.web.controller;


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

import ua.rd.cm.config.WebMvcConfig;
import ua.rd.cm.config.WebTestConfig;
import ua.rd.cm.services.UserService;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestMethod;

import ua.rd.cm.domain.ContactType;
import ua.rd.cm.domain.Role;
import ua.rd.cm.domain.User;
import ua.rd.cm.domain.UserInfo;
import ua.rd.cm.services.PhotoService;
import ua.rd.cm.web.controller.dto.PhotoDto;

import java.io.*;
import java.util.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebTestConfig.class, WebMvcConfig.class, })
@WebAppConfiguration
public class PhotoControllerTest {
    public static final String API_PHOTO = "/api/user/current/photo";
    private static final String SPEAKER_EMAIL = "ivanova@gmail.com";
    private MockMvc mockMvc;
    private PhotoDto photoDto;
    private String alreadyRegisteredEmail = "registered@gmail.com";
    private User user;

    @Autowired
    private UserService userService;

    @Autowired
    private PhotoService photoService;

    @Autowired
    private PhotoController photoController;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(photoController).build();
        photoDto =setupCorrectPhotoDto();
        user=createUser(createSpeakerRole(),createUserInfo());
    }

    private PhotoDto setupCorrectPhotoDto(){
        PhotoDto photoDto=new PhotoDto();
        try {
            File file= new File("src/test/resources/trybel_master.JPG");
            FileInputStream fileInputStream=new FileInputStream(file);
            MockMultipartFile mockFile=new MockMultipartFile("trybel_master",fileInputStream);
            photoDto.setFile(mockFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return photoDto;
    }

    MockMultipartFile setupCorrectMultipartFile(){
        try {
            File file= new File("src/test/resources/trybel_master.JPG");
            FileInputStream fileInputStream=new FileInputStream(file);
            MockMultipartFile mockFile=new MockMultipartFile("trybel_master",fileInputStream);
            return mockFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    @WithMockUser(username = SPEAKER_EMAIL, roles = "SPEAKER")
    public void correctUploadPhoto() throws Exception {
        when(userService.getByEmail(user.getEmail())).thenReturn(user);

        System.out.println(photoDto.getFile().getName());
//        mockMvc.perform(post(API_PHOTO)
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .content(convertObjectToJsonBytes(photoDto))
//        ).andExpect(status().isOk());

        mockMvc.perform(fileUpload(API_PHOTO)
                        .file(setupCorrectMultipartFile())
                //.contentType(MediaType.MULTIPART_FORM_DATA)
                //.param("photoDto", String.valueOf(photoDto.getFile().getBytes()))
                //.content(convertObjectToJsonBytes(photoDto))
        ).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = SPEAKER_EMAIL, roles = "SPEAKER")
    public void nullUploadPhoto() throws Exception {
        mockMvc.perform(fileUpload(API_PHOTO)
                .file(setupCorrectMultipartFile())
        ).andExpect(status().isBadRequest());
    }

    private void checkForBadRequest(String uri, RequestMethod method, PhotoDto dto) {
        try {
            System.out.println("dto="+dto);
            if (method == RequestMethod.POST) {
                mockMvc.perform(fileUpload(uri)
                        .file((MockMultipartFile) dto.getFile())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                ).andExpect(status().isBadRequest());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private User createUser(Role role, UserInfo info) {
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        User user = new User(1L, "FirstName", "LastName", alreadyRegisteredEmail, "src/test/trybel_master.JPG", "url",
                User.UserStatus.CONFIRMED, info, roles);
        return user;
    }

    private Role createSpeakerRole(){
        return new Role(1L, "SPEAKER");
    }
    private UserInfo createUserInfo(){
        ContactType contactType = new ContactType(1L, "LinkedIn");
        ContactType contactType2 = new ContactType(2L, "Twitter");
        ContactType contactType3 = new ContactType(3L, "FaceBook");
        ContactType contactType4 = new ContactType(4L, "Blog");


        Map<ContactType, String> contacts = new HashMap<ContactType, String>() {{
            put(contactType, "LinkedIn");
            put(contactType2, "Twitter");
            put(contactType3, "FaceBook");
            put(contactType4, "Blog");
        }};
        return new UserInfo(1L, "bio", "job", "pastConference", "EPAM", contacts, "addInfo");
    }
}