package ua.rd.cm.web.controller.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NonNull;

@Data
public class NewPasswordDto {

	@NonNull
	@Size(min = 1, max = 30)
	private String password;
	
	@NotNull
	@Size(min = 1, max = 30)
	private String confirm;
}
