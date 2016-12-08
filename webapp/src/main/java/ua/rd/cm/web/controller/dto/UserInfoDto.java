package ua.rd.cm.web.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Olha_Melnyk
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto {

    @NotNull
    @JsonProperty("bio")
    @Size(min = 1, max = 2000)
    private String userInfoShortBio;

    @NotNull
    @JsonProperty("job")
    @Size(min = 1, max = 256)
    private String userInfoJobTitle;

    @NotNull
    @JsonProperty("company")
    @Size(min = 1, max = 256)
    private String userInfoCompany;

    @JsonProperty("past")
    @Size(max = 1000)
    private String userInfoPastConference;

    @JsonProperty("photo")
    private String photo;

    @JsonProperty("linkedin")
    private String linkedIn;

    @JsonProperty("twitter")
    private String twitter;

    @JsonProperty("facebook")
    private String facebook;

    @JsonProperty("blog")
    private String blog;

    @JsonProperty("info")
    @Size(max = 1000)
    private String userInfoAdditionalInfo;

    @JsonProperty("mail")
    private String email;

    @JsonProperty("fname")
    private String firstName;

    @JsonProperty("lname")
    private String lastName;

    private String[] roles;

}
