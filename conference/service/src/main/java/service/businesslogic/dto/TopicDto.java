package service.businesslogic.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class TopicDto {
	@JsonProperty("name")
	private String name;
}
