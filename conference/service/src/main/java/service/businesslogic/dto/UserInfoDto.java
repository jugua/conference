package service.businesslogic.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import domain.model.Contact;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserInfoDto extends UserBasicDto {

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
    
    @JsonProperty("info")
    @Size(max = 1000)
    private String userInfoAdditionalInfo;

    private List<Contact> contacts = new ArrayList<>();

    public void setContact(Contact contact) {
    	
    	if(contacts == null) {
    		contacts = new ArrayList<>();
    	}
    	contacts.add(contact);
   
    }
}
