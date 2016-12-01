package ua.rd.cm.web.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TalkDto {
	@NotNull
	@Size(min = 1, max = 250)
	@JsonProperty("title")
	private String title;
	
	@NotNull
	@Size(min = 1, max = 3000)
	@JsonProperty("description")	
	private String description;
	
	@NotNull
	@Size(min = 1, max = 255)
	@JsonProperty("topic")
	private String topic;
	
	@NotNull
	@Size(min = 1, max = 255)
	@JsonProperty("type")
	private String type;
	
	@NotNull
	@Size(min = 1, max = 255)
	@JsonProperty("lang")
	private String language;
	
	@NotNull
	@Size(min = 1, max = 255)
	@JsonProperty("level")
	private String level;
	
	@Size( max = 1500)
	@JsonProperty("addon")
	private String additionalInfo;
	
}
