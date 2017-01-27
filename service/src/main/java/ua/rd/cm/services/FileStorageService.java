package ua.rd.cm.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface FileStorageService {

    String saveFile(MultipartFile file) throws IOException;

    boolean deleteFile(String fileAbsolutePath);

    File getFile(String fileAbsolutePath);
}
