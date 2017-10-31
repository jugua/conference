package ua.rd.cm.dto;

import java.util.List;
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
	private List<String> role;
	@JsonProperty("conferenceCount")
	private long conferenceCount;
	@JsonProperty("talksCount")
	private long talksCount;

}
