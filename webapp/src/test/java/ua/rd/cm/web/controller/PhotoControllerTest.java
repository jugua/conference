package ua.rd.cm.web.controller;

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

/**
 * Created by Artem_Pryzhkov on 29-Nov-16.
 */

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
            System.out.println(file.getAbsolutePath());
            FileInputStream fileInputStream=new FileInputStream(file);
            System.out.println(fileInputStream.toString());
            MockMultipartFile mockFile=new MockMultipartFile("trybel_master",fileInputStream);
            System.out.println(mockFile.getName());
            photoDto.setFile(mockFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return photoDto;
    }

//    @Test
//    public void correctPhoto() throws Exception {
////        Role speaker = createSpeakerRole();
////        UserInfo info = createUserInfo();
////        User user = createUser(speaker, info);
////        Principal correctPrincipal = () -> user.getEmail();
//        mockMvc.perform(post(API_PHOTO)
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//        ).andExpect(status().isAccepted());
//    }
//    @Test
//    public void addCorrectPhoto(){
//


    @Test
    public void nullPhoto(){
        correctPhotoDto.setFile(null);
        checkForBadRequest(API_PHOTO, RequestMethod.POST, correctPhotoDto);
    }
    private void checkForBadRequest(String uri, RequestMethod method, Object dto) {
        try {
            if (method == RequestMethod.POST) {
                mockMvc.perform(post(uri)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(convertObjectToJsonBytes(dto))
                ).andExpect(status().isBadRequest());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private byte[] convertObjectToJsonBytes(Object object) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

    private User createUser(Role role , UserInfo info){
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

        Map<ContactType, String> contacts = new HashMap<ContactType, String>(){{
            put(contactType, "LinkedIn");
            put(contactType2, "Twitter");
            put(contactType3, "FaceBook");
            put(contactType4, "Blog");
        }};
        return new UserInfo(1L, "bio", "job", "pastConference", "EPAM", contacts, "addInfo");
    }


}
