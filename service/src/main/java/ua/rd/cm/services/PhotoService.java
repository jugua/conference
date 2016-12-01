package ua.rd.cm.services;

import org.springframework.web.multipart.MultipartFile;

public interface PhotoService {

    String savePhoto(MultipartFile photo, String fileName);

    boolean deletePhoto(String fileName);
}
