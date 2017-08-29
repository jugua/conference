package ua.rd.cm.infrastructure.fileStorage;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ua.rd.cm.infrastructure.fileStorage.impl.FileStorageServiceImpl.FileType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import ua.rd.cm.infrastructure.fileStorage.exception.FileValidationException;
import ua.rd.cm.services.exception.ResourceNotFoundException;
import ua.rd.cm.infrastructure.fileStorage.impl.FileStorageServiceImpl;


@RunWith(MockitoJUnitRunner.class)
public class FileStorageServiceTest {
    private static final String PARENTFOLDER = System.getProperty("java.io.tmpdir");
    private static final String FOLDER = PARENTFOLDER + (PARENTFOLDER.endsWith(File.separator) ? "" : File.separator) + "fs";
    private static FileStorageService fileStorageService;
    @Rule
    public ExpectedException exception = ExpectedException.none();
    private MockMultipartFile mockedFile = mock(MockMultipartFile.class);
    private MockMultipartFile mockMultipartFile = new MockMultipartFile(
            "fileData.docx",
            "file.docx",
            "application/pdf", new byte[]{1, 2, 3});

    @Before
    public void set() throws IOException {
        recurrentDeleteIfExist(new File(FOLDER));
        new File(FOLDER).mkdir();

        FileStorageServiceImpl fileStorageServiceImpl = new FileStorageServiceImpl();
        fileStorageServiceImpl.setStoragePath(FOLDER);
        fileStorageService = fileStorageServiceImpl;

        when(mockedFile.getBytes()).thenReturn(new byte[]{});
    }

    @After
    public void clean() throws IOException {
        recurrentDeleteIfExist(new File(FOLDER));
    }

    //saveFile

    @Test
    public void uploadAttachment() throws IOException {
        final String FILENAME = "SomeAttachment.pdf";
        final String CONTENT_TYPE = "application/pdf";

        final String EXPECTED_FILENAME = "SomeAttachment.pdf";
        final String EXPECTED_RELATED_PATH = File.separator + (EXPECTED_FILENAME.hashCode() % 32) + File.separator;

        when(mockedFile.getOriginalFilename()).thenReturn(FILENAME);
        when(mockedFile.getContentType()).thenReturn(CONTENT_TYPE);

        assertEquals(FOLDER + EXPECTED_RELATED_PATH + EXPECTED_FILENAME,
                fileStorageService.saveFile(mockedFile, FileType.FILE));
    }

    @Test
    public void uploadTwiceAttachment() throws IOException {
        final String FILENAME = "SomeAttachment.pdf";
        final String CONTENT_TYPE = "application/pdf";
        final String EXPECTED_FILENAME = "SomeAttachment v1.pdf";
        final String EXPECTED_RELATED_PATH = File.separator + (EXPECTED_FILENAME.hashCode() % 32) + File.separator;

        when(mockedFile.getOriginalFilename()).thenReturn(FILENAME);
        when(mockedFile.getContentType()).thenReturn(CONTENT_TYPE);

        fileStorageService.saveFile(mockedFile, FileType.FILE);
        assertEquals(FOLDER + EXPECTED_RELATED_PATH + EXPECTED_FILENAME,
                fileStorageService.saveFile(mockedFile, FileType.FILE));
    }

    @Test
    public void checkFileCreation() throws IOException {
        final String FILENAME = "file.jpg";
        when(mockedFile.getOriginalFilename()).thenReturn(FILENAME);

        assertEquals(true, new File(fileStorageService.saveFile(mockedFile, FileType.PHOTO)).exists());
    }

    @Test
    public void uploadFile_1jpg() throws IOException {
        final String FILENAME = "1.jpg";
        final String EXPECTED_FILENAME = "1.jpg";
        final String EXPECTED_RELATED_PATH = File.separator + (EXPECTED_FILENAME.hashCode() % 32) + File.separator;

        when(mockedFile.getOriginalFilename()).thenReturn(FILENAME);

        assertEquals(FOLDER + EXPECTED_RELATED_PATH + EXPECTED_FILENAME,
                fileStorageService.saveFile(mockedFile, FileType.PHOTO));
    }

    @Test
    public void uploadFileTwice_1jpg() throws IOException {
        final String FILENAME = "1.jpg";
        final String CONTENT_TYPE = "application/pdf";
        final String EXPECTED_FILENAME = "1 v1.jpg";
        final String EXPECTED_RELATED_PATH = File.separator + (EXPECTED_FILENAME.hashCode() % 32) + File.separator;

        when(mockedFile.getOriginalFilename()).thenReturn(FILENAME);
        when(mockedFile.getContentType()).thenReturn(CONTENT_TYPE);

        fileStorageService.saveFile(mockedFile, FileType.PHOTO);
        assertEquals(FOLDER + EXPECTED_RELATED_PATH + EXPECTED_FILENAME, fileStorageService.saveFile(mockedFile, FileType.PHOTO));
    }

    @Test
    public void uploadFileThird_1jpg() throws IOException {
        final String FILENAME = "1.jpg";

        final String EXPECTED_FILENAME = "1 v2.jpg";
        final String EXPECTED_RELATED_PATH = File.separator + (EXPECTED_FILENAME.hashCode() % 32) + File.separator;

        when(mockedFile.getOriginalFilename()).thenReturn(FILENAME);
        when(mockedFile.getContentType()).thenReturn(MediaType.IMAGE_JPEG_VALUE);

        fileStorageService.saveFile(mockedFile, FileType.PHOTO);
        fileStorageService.saveFile(mockedFile, FileType.PHOTO);
        assertEquals(FOLDER + EXPECTED_RELATED_PATH + EXPECTED_FILENAME,
                fileStorageService.saveFile(mockedFile, FileType.PHOTO));
    }

    @Test
    public void uploadVersioned() throws IOException {
        final String FILENAME = "1 v1.jpg";
        final String EXPECTED_FILENAME = "1 v2.jpg";
        final String EXPECTED_RELATED_PATH = File.separator + (EXPECTED_FILENAME.hashCode() % 32) + File.separator;
        when(mockedFile.getOriginalFilename()).thenReturn(FILENAME);

        fileStorageService.saveFile(mockedFile, FileType.PHOTO);
        when(mockedFile.getContentType()).thenReturn(MediaType.IMAGE_JPEG_VALUE);

        assertEquals(FOLDER + EXPECTED_RELATED_PATH + EXPECTED_FILENAME,
                fileStorageService.saveFile(mockedFile, FileType.PHOTO));
    }

    @Test
    public void uploadFileTwice_v1s() throws IOException {
        final String FILENAME = "file v1s.jpg";
        final String EXPECTED_FILENAME = "file v1s v1.jpg";
        final String EXPECTED_RELATED_PATH = File.separator + (EXPECTED_FILENAME.hashCode() % 32) + File.separator;

        when(mockedFile.getOriginalFilename()).thenReturn(FILENAME);
        when(mockedFile.getContentType()).thenReturn(MediaType.IMAGE_JPEG_VALUE);

        fileStorageService.saveFile(mockedFile, FileType.PHOTO);
        assertEquals(FOLDER + EXPECTED_RELATED_PATH + EXPECTED_FILENAME,
                fileStorageService.saveFile(mockedFile, FileType.PHOTO));
    }

    //deleteFile

    @Test(expected = FileValidationException.class)
    public void deleteNullFile() {
        fileStorageService.deleteFile(null);
    }

    @Test(expected = FileValidationException.class)
    public void deleteEmptyPathFile() {
        fileStorageService.deleteFile("");
    }

    @Test
    public void deleteExistFile() throws IOException {
        final String FILENAME = "fileNameId.jpg";
        when(mockedFile.getOriginalFilename()).thenReturn(FILENAME);
        when(mockedFile.getContentType()).thenReturn(MediaType.IMAGE_JPEG_VALUE);

        String filePath = fileStorageService.saveFile(mockedFile, FileType.PHOTO);
        fileStorageService.deleteFile(filePath);
        assertEquals(false, new File(filePath).exists());
    }

    @Test(expected = FileValidationException.class)
    public void deleteNotExistentFile() {
        fileStorageService.deleteFile(FOLDER + "fileNameId.jpg");
    }

    //getFile

    @Test
    public void getFileByNullThrowsException() {
        exception.expect(ResourceNotFoundException.class);
        exception.expectMessage(ResourceNotFoundException.FILE_NOT_FOUND);
        fileStorageService.getFile(null);
    }

    @Test
    public void getFileByEmptyStringThrowsException() {
        exception.expect(ResourceNotFoundException.class);
        exception.expectMessage(ResourceNotFoundException.FILE_NOT_FOUND);
        fileStorageService.getFile("");
    }


    @Test
    public void getFile_WrongPath() {
        exception.expect(ResourceNotFoundException.class);
        exception.expectMessage(ResourceNotFoundException.FILE_NOT_FOUND);
        fileStorageService.getFile("AnyWrongPath");
    }

    @Test
    public void testFileValidationSuccess() throws IOException {
        fileStorageService.saveFile(mockMultipartFile, FileType.FILE);
    }

    @Test
    public void verifyFileNameNotChanged() throws IOException {
        final String FILENAME = "SomeAttachment.pdf";
        final String CONTENT_TYPE = "application/pdf";

        when(mockedFile.getOriginalFilename()).thenReturn(FILENAME);
        when(mockedFile.getContentType()).thenReturn(CONTENT_TYPE);

        String filePath = fileStorageService.saveFile(mockedFile, FileType.FILE);
        File readedFile = fileStorageService.getFile(filePath);

        assertEquals(FILENAME, readedFile.getName());
    }

    @Test
    public void verifyFileDataNotChanged() throws IOException {
        final String FILENAME = "SomeAttachment.pdf";
        final String CONTENT_TYPE = "application/pdf";

        final byte[] fileData = new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        when(mockedFile.getOriginalFilename()).thenReturn(FILENAME);
        when(mockedFile.getBytes()).thenReturn(fileData);
        when(mockedFile.getContentType()).thenReturn(CONTENT_TYPE);

        String filePath = fileStorageService.saveFile(mockedFile, FileType.FILE);
        File readedFile = fileStorageService.getFile(filePath);
        byte[] readedBytes = Files.readAllBytes(readedFile.toPath());

        assertArrayEquals(fileData, readedBytes);
    }


    private void recurrentDeleteIfExist(File f) throws IOException {
        if (f.exists()) {
            if (f.isDirectory()) {
                for (File c : f.listFiles())
                    recurrentDeleteIfExist(c);
            }
            if (!f.delete())
                throw new IOException("Couldn't delete " + f);
        }
    }

}


