package ua.rd.cm.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;
import ua.rd.cm.domain.User;

@Data
@ToString(exclude = {"password", "confirm"})
public class RegistrationDto {

    @NotNull
    @Size(min = 1, max = 56)
    @JsonProperty("fname")
    private String firstName;

    @NotNull
    @Size(min = 1, max = 56)
    @JsonProperty("lname")
    private String lastName;

    @NotNull
    @Pattern(regexp = "(?i)^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$")
    @JsonProperty("mail")
    private String email;

    @NotNull
    @Size(min = 6, max = 30)
    private String password;

    @NotNull
    @Size(min = 6, max = 30)
    private String confirm;

    private String roleName;

    @JsonIgnore
    private User.UserStatus userStatus;
}
