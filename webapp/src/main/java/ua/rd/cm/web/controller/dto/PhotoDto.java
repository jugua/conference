package ua.rd.cm.web.controller.dto;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author Mariia Lapovska
 */
public class PhotoDto {

//    @Size(max=2097152)
//    @Pattern(regexp = "/jp(e)?g|gif|png$/")
    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
