package ua.rd.cm.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

import static org.junit.Assert.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class SimplePhotoServiceTest {
    public static final String ROOT = "/";
    public static final String FOLDER = "home/cm/user/photos/";
    private PhotoService photoService= new SimplePhotoService();


    @Before
    public void set(){
        photoService= new SimplePhotoService();
    }

    @Test
    public void uploadFile() throws IOException {
        MockMultipartFile multipartFile=mock(MockMultipartFile.class);
        when(multipartFile.getOriginalFilename()).thenReturn("1.jpg");
        when(multipartFile.getBytes()).thenReturn(new byte[]{});

        assertEquals(ROOT+FOLDER+"fileNameId.jpg",
                photoService.savePhoto(multipartFile,
                        "fileNameId",null));
    }


    @Test
    public void deleteNullFile(){
        assertEquals(false,photoService.deletePhoto(null));
    }

    @Test
    public void deleteNotNullFile(){
        assertEquals(true,photoService.deletePhoto(ROOT+FOLDER+"fileNameId.jpg"));
    }

    @Test
    public void deleteNotAFile(){
        assertEquals(false,photoService.deletePhoto("1.jpg"));
    }

    @Test
    public void getNullPhoto(){
        assertEquals(null,photoService.getPhoto(null));
    }

    @Test
    public void getPhoto(){
        assertEquals(ROOT+FOLDER+"fileNameId.jpg",
                photoService.getPhoto(ROOT+FOLDER+"fileNameId.jpg").getAbsolutePath());
    }

    @Test
    public void getNotAPhoto(){
        assertEquals(null,photoService.getPhoto("1.jpg"));
    }
}
