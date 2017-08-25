package ua.rd.cm.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NonNull;

@Data
public class NewPasswordDto {

    @JsonProperty("newPassword")
    @NonNull
    @Size(min = 1, max = 30)
    private String password;

    @JsonProperty("confirmPassword")
    @NotNull
    @Size(min = 1, max = 30)
    private String confirm;
}
