package service.businesslogic.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class ConfirmPasswordPair {

    @JsonProperty("newPassword")
    @NonNull
    @Size(min = 1, max = 30)
    private String password;

    @JsonProperty("confirmPassword")
    @NotNull
    @Size(min = 1, max = 30)
    private String confirm;
}
