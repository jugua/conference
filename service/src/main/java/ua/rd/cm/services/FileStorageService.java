package ua.rd.cm.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface FileStorageService {

    String saveFile(MultipartFile file) throws IOException;

    void deleteFile(String fileAbsolutePath);

    File getFile(String fileAbsolutePath);

    String getTypeIfSupported(File file);

    String getTypeIfSupported(MultipartFile file);

    boolean isFileSizeGreaterThanMaxSize(MultipartFile multipartFile);

    void checkFileValidation(MultipartFile file);
}
