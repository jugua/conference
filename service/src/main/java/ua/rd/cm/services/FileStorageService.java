package ua.rd.cm.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import static ua.rd.cm.services.impl.FileStorageServiceImpl.*;

public interface FileStorageService {

    String saveFile(MultipartFile file) throws IOException;

    void deleteFile(String fileAbsolutePath);

    File getFile(String fileAbsolutePath);

    String getFileTypeIfSupported(File file);

    void checkFileValidation(MultipartFile file, FileType fileType);

    String getPhotoTypeIfSupported(File file);
}
