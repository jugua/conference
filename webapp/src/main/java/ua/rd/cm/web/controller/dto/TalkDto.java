package ua.rd.cm.web.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TalkDto {
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
	
}
