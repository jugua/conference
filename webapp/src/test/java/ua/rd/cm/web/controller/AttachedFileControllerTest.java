package ua.rd.cm.web.controller;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.rd.cm.config.WebMvcConfig;
import ua.rd.cm.config.WebTestConfig;
import ua.rd.cm.domain.Talk;
import ua.rd.cm.domain.User;
import ua.rd.cm.services.FileStorageService;
import ua.rd.cm.services.TalkService;
import ua.rd.cm.services.UserService;
import ua.rd.cm.web.controller.dto.PhotoDto;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Volodymyr_Kara on 1/31/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebTestConfig.class, WebMvcConfig.class,})
@WebAppConfiguration
public class AttachedFileControllerTest {
    private static final String FILE_URI = "/talks/1/file";
    private MockMvc mockMvc;
    private Talk talk;

    @Autowired
    private TalkService talkService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private AttachedFileController fileController;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(fileController).build();
        talk = new Talk();
        Mockito.when(talkService.findTalkById(1L)).thenReturn(talk);
    }

    @Ignore
    @Test
    @WithMockUser()
    public void UploadFileAndExpectOK() throws Exception {
        MockMultipartHttpServletRequestBuilder servletRequestBuilder = MockMvcRequestBuilders.fileUpload(FILE_URI).file(correctFile());
        mockMvc.perform(servletRequestBuilder).andExpect(MockMvcResultMatchers.status().isOk());
    }

    private MockMultipartFile correctFile() {
        try {
            File file = new File("src/test/resources/true.docx");
            FileInputStream fileInputStream = new FileInputStream(file);
            MockMultipartFile multipartFile = new MockMultipartFile("file","true.docx","application/pdf", new FileInputStream(file));
            return multipartFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
