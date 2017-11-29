package service.businesslogic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class LoginDto {

	private Long id;
	
    @NonNull
    private String firstName;
    
    @NonNull
    private String role;
    
    private long conferenceCount;
    
    private long talksCount;

}
