/*package ua.rd.cm.web.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.rd.cm.config.WebMvcConfig;
import ua.rd.cm.config.WebTestConfig;
import ua.rd.cm.domain.ContactType;
import ua.rd.cm.domain.Role;
import ua.rd.cm.domain.User;
import ua.rd.cm.domain.UserInfo;
import ua.rd.cm.services.PhotoService;
import ua.rd.cm.services.UserService;
import ua.rd.cm.web.controller.dto.PhotoDto;

import java.io.*;
import java.util.*;

import static org.mockito.Mockito.spy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

*//**
 * Created by Artem_Pryzhkov on 29-Nov-16.
 *//*

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebTestConfig.class, WebMvcConfig.class, })
@WebAppConfiguration
public class PhotoControllerTest {
    public static final String API_PHOTO = "/api/user/current/photo";
    private MockMvc mockMvc;
    private PhotoDto correctPhotoDto;
    private String alreadyRegisteredEmail = "registered@gmail.com";

    @Autowired
    private UserService userService;

    @Autowired
    private PhotoService photoService;

    @Autowired
    private PhotoController photoController;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(photoController).build();
        correctPhotoDto=setupCorrectPhotoDto();

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

    @Test
    public void run(){

    }

}
*/