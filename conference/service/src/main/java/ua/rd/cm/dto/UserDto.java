package ua.rd.cm.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;
import ua.rd.cm.domain.Contact;

@Data
@NoArgsConstructor
public class UserDto extends UserBasicDto {

    @NotNull
    @JsonProperty("bio")
    @Size(max = 2000)
    private String userInfoShortBio;

    @NotNull
    @JsonProperty("job")
    @Size(max = 256)
    private String userInfoJobTitle;

    @NotNull
    @JsonProperty("company")
    @Size(max = 256)
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

    public void setContact(Contact contact) {
        String name = contact.getContactType().getName();
        String value = contact.getValue();
        switch (name) {
            case "LinkedIn":
                linkedIn = value;
                break;
            case "Twitter":
                twitter = value;
                break;
            case "Facebook":
                facebook = value;
                break;
            case "Blog":
                blog = value;
                break;
            default:
                break;
        }
    }
}
