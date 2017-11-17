package service.businesslogic.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

	@JsonProperty("id")
	private Long id;
	@JsonProperty("message")
	@NonNull
	private String message;
	@JsonProperty("userId")
	@NonNull
	private Long userId;
	@JsonProperty("talkId")
	@NonNull
	private Long talkId;
	@JsonProperty("time")
	@NonNull
	private LocalDateTime time;
	
}
