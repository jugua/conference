package ua.rd.cm.infrastructure.fileStorage;

import static ua.rd.cm.infrastructure.fileStorage.impl.FileStorageServiceImpl.FileType;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    String saveFile(MultipartFile file, FileType fileType) throws IOException;

    void deleteFile(String fileAbsolutePath);

    File getFile(String fileAbsolutePath);

    String getFileTypeIfSupported(File file);

    void checkFileValidation(MultipartFile file, FileType fileType);

    String getPhotoTypeIfSupported(File file);

}
