package ua.rd.cm.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TalkDto {

    @JsonProperty("id")
    private Long id;

    @NotNull
    @Size(min = 1, max = 250)
    @JsonProperty("title")
    private String title;

    @JsonProperty("speakerId")
    private Long userId;

    @JsonProperty("conferenceId")
    private Long conferenceId;

    @JsonProperty("conferenceName")
    private String conferenceName;

    @JsonProperty("name")
    private String speakerFullName;

    @NotNull
    @Size(min = 1, max = 3000)
    @JsonProperty("description")
    private String description;

    @NotNull
    @Size(min = 1, max = 255)
    @JsonProperty("topic")
    private String topicName;

    @NotNull
    @Size(min = 1, max = 255)
    @JsonProperty("type")
    private String typeName;

    @NotNull
    @Size(min = 1, max = 255)
    @JsonProperty("lang")
    private String languageName;

    @NotNull
    @Size(min = 1, max = 255)
    @JsonProperty("level")
    private String levelName;

    @Size(max = 1500)
    @JsonProperty("addon")
    private String additionalInfo;

    @JsonProperty("status")
    private String statusName;

    @JsonProperty("date")
    private String date;

    @Size(max = 1000)
    @JsonProperty("comment")
    private String organiserComment;

    @JsonProperty("assignee")
    private String assignee;

    @JsonProperty("file")
    private MultipartFile multipartFile;
}
