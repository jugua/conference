package ua.rd.cm.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserBasicDto {

    private Long id;

    @JsonProperty("mail")
    private String email;

    @JsonProperty("fname")
    private String firstName;

    @JsonProperty("lname")
    private String lastName;

    private List<String> roles;
}
