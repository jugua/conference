package ua.rd.cm.web.controller.dto;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Mariia Lapovska
 */
public class PhotoDto {

    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
