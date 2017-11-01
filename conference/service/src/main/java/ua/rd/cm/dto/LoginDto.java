package ua.rd.cm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class LoginDto {
	
	@JsonProperty("firstName")
	@NonNull
	private String firstName;
	@JsonProperty("role")
	@NonNull
	private String role;
	@JsonProperty("conferenceCount")
	private long conferenceCount;
	@JsonProperty("talksCount")
	private long talksCount;

}
