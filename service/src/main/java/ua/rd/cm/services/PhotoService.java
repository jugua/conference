package ua.rd.cm.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface PhotoService {

    String savePhoto(MultipartFile photo, String fileNameId);

    boolean deletePhoto(String fileNameId);

    File getPhoto(String fileNameId);
}
