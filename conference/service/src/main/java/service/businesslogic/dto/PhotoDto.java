package service.businesslogic.dto;

import org.springframework.web.multipart.MultipartFile;

public class PhotoDto {

    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
