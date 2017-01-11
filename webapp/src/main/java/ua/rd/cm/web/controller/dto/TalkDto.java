package ua.rd.cm.web.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.rd.cm.domain.Status;
import ua.rd.cm.domain.Talk;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TalkDto {

	@JsonProperty("_id")
	private Long id;

	@NotNull
	@Size(min = 1, max = 250)
	@JsonProperty("title")
	private String title;

	@JsonProperty("speaker_id")
	private Long userId;

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
	@JsonProperty("org_comment")
	private String organiserComment;


}
