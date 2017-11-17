package service.businesslogic.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

	private Long id;	
	@NonNull
	private String message;
	@NonNull
	private Long userId;
	@NonNull
	private Long talkId;
	@NonNull
	private LocalDateTime time;
	
}
