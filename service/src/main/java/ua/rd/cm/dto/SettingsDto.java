package ua.rd.cm.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

/**
 * @author Olha_Melnyk
 */
@Data
@ToString
public class SettingsDto {

    @NotNull(message = "required")
    @JsonProperty("currentPassword")
    private String currentPassword;

    @NotNull(message = "required")
    @JsonProperty("newPassword")
    @Size(min = 6, max = 30)
    private String newPassword;

    @NotNull(message = "required")
    @JsonProperty("confirmNewPassword")
    @Size(min = 6, max = 30)
    private String confirmedNewPassword;
}
