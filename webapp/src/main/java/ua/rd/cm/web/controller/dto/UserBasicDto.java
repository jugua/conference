package ua.rd.cm.web.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Anastasiia_Milinchuk on 1/30/2017.
 */
@Data
@NoArgsConstructor
public class UserBasicDto {
    @JsonProperty("mail")
    private String email;

    @JsonProperty("fname")
    private String firstName;

    @JsonProperty("lname")
    private String lastName;

    private String[] roles;
}
