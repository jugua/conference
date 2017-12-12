package service.businesslogic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InviteDto {
	
	private String email;
	
	private String conferenceName;

}
