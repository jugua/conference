package service.businesslogic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TalkStatusDto {

	@NonNull
	private Long id;
	@NonNull
	private String status;
	
}
