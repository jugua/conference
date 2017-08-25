package ua.rd.cm.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SignInDto {
    @NotNull
    @Pattern(regexp = "(?i)^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$")
    @JsonProperty("mail")
    private String email;

    @NotNull
    @Size(min = 1, max = 30)
    private String password;
}
