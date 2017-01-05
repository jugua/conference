/*package ua.rd.cm.web.controller;


import org.junit.Before;
import org.junit.Ignore;
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

<<<<<<< HEAD
*//**
 * Created by Artem_Pryzhkov on 29-Nov-16.
 *//*
=======
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
>>>>>>> 3d384a291a780ef257d330e0704130b3a2dabd54

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebTestConfig.class, WebMvcConfig.class, })
@WebAppConfiguration
public class PhotoControllerTest extends TestUtil{
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

    @Ignore
    @Test
    @WithMockUser(username = SPEAKER_EMAIL, roles = "SPEAKER_ROLE")
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


}


    @Test
    @WithMockUser(username = SPEAKER_EMAIL, roles = "SPEAKER_ROLE")
    public void nullUploadPhoto() throws Exception {
        mockMvc.perform(fileUpload(API_PHOTO)
                .file(setupCorrectMultipartFile())
        ).andExpect(status().isBadRequest());
    }
}
*/
