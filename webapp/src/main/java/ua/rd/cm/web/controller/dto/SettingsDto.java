package ua.rd.cm.web.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Olha_Melnyk
 */
@Data
public class SettingsDto {

    @NotNull
    @JsonProperty("currentPassword")
    private String currentPassword;

    @NotNull
    @JsonProperty("newPassword")
    @Size(min = 6, max = 30)
    private String newPassword;

    @NotNull
    @JsonProperty("confirmNewPassword")
    private String confirmedNewPassword;
}
