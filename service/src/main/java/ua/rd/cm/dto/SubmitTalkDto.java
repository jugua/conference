package ua.rd.cm.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SubmitTalkDto {

    @NotNull
    @Size(min = 1, max = 250)
    private String title;

    @NotNull
    @Size(min = 1, max = 3000)
    private String description;

    @NotNull
    @Size(min = 1, max = 255)
    private String topic;

    @NotNull
    @Size(min = 1, max = 255)
    private String type;

    @NotNull
    @Size(min = 1, max = 255)
    private String lang;

    @NotNull
    @Size(min = 1, max = 255)
    private String level;

    @Size(max = 1500)
    private String addon;

    private Long conferenceId;

    private String status;
    private String date;
    private MultipartFile file;

}
