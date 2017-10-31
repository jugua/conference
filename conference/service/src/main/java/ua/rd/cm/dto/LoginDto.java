package ua.rd.cm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import ua.rd.cm.domain.Role;

@Data
@AllArgsConstructor
public class LoginDto {
	
	@JsonProperty("firstName")
	@NonNull
	private String firstName;
	@JsonProperty("role")
	@NonNull
	private Role role;
	@JsonProperty("conferenceCount")
	private int conferenceCount;
	@JsonProperty("talksCount")
	private int talksCount;

}
