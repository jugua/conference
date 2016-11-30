package ua.rd.cm.web.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @JsonProperty("mail")
    private String email;

    @JsonProperty("fname")
    private String firstName;

    @JsonProperty("lname")
    private String lastName;

    @JsonProperty("bio")
    private String userInfoShortBio;

    @JsonProperty("job")
    private String userInfoJobTitle;

    @JsonProperty("past")
    private String userInfoPastConference;

    @JsonProperty("photo")
    private String photo;

    @JsonProperty("linkedin")
    private String linkedin;

    @JsonProperty("twitter")
    private String twitter;

    @JsonProperty("facebook")
    private String facebook;

    @JsonProperty("blog")
    private String blog;

    @JsonProperty("info")
    private String userInfoAdditionalInfo;

    @JsonProperty("company")
    private String userInfoCompany;

    private String[] roles;
}