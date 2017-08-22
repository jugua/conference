package ua.rd.cm.services;

import org.springframework.web.multipart.MultipartFile;
import ua.rd.cm.dto.TalkDto;

import java.io.File;
import java.io.IOException;

import static ua.rd.cm.services.impl.FileStorageServiceImpl.*;
import static ua.rd.cm.services.impl.FileStorageServiceImpl.FileType.FILE;

public interface FileStorageService {

    String saveFile(MultipartFile file, FileType fileType) throws IOException;

    void deleteFile(String fileAbsolutePath);

    File getFile(String fileAbsolutePath);

    String getFileTypeIfSupported(File file);

    void checkFileValidation(MultipartFile file, FileType fileType);

    String getPhotoTypeIfSupported(File file);

}
