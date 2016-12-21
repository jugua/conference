package ua.rd.cm.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface PhotoService {

    String savePhoto(MultipartFile photo, String fileNameId, String oldFileAbsolutePath);

    boolean deletePhoto(String fileAbsolutePath);

    File getPhoto(String fileAbsolutePath);
}
