package ua.rd.cm.web.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import ua.rd.cm.domain.Role;
import ua.rd.cm.domain.User;
import ua.rd.cm.services.ContactTypeService;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;


@Data
@NoArgsConstructor
public class UserDto {

    @JsonProperty("mail")
    private String email;

    @JsonProperty("fname")
    private String firstName;

    @JsonProperty("lname")
    private String lastName;

    @NotNull
    @JsonProperty("bio")
    @Size( max = 2000)
    private String userInfoShortBio;

    @NotNull
    @JsonProperty("job")
    @Size( max = 256)
    private String userInfoJobTitle;

    @NotNull
    @JsonProperty("company")
    @Size( max = 256)
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

    private String[] roles;

//    private ContactTypeService contactTypeService;
//
//    public UserDto entityToDto(User user){
//        setFirstName(user.getFirstName());
//        setLastName(user.getLastName());
//        setEmail(user.getEmail());
//        setUserInfoShortBio(user.getUserInfo().getShortBio());
//        setUserInfoJobTitle(user.getUserInfo().getJobTitle());
//        setUserInfoCompany(user.getUserInfo().getCompany());
//        setUserInfoPastConference(user.getUserInfo().getPastConference());
//        setUserInfoAdditionalInfo(user.getUserInfo().getAdditionalInfo());
//        //UserDto dto = mapper.map(user, UserDto.class);
//        if (user.getPhoto() != null) {
//            setPhoto("api/user/current/photo/" + user.getId());
//        }
//        setLinkedIn(user.getUserInfo().getContacts().get(contactTypeService.findByName("LinkedIn").get(0)));
//        setTwitter(user.getUserInfo().getContacts().get(contactTypeService.findByName("Twitter").get(0)));
//        setFacebook(user.getUserInfo().getContacts().get(contactTypeService.findByName("FaceBook").get(0)));
//        setBlog(user.getUserInfo().getContacts().get(contactTypeService.findByName("Blog").get(0)));
//        setRoles(convertRolesTypeToFirstLetters(user.getUserRoles()));
//        return this;
//    }

    private String[] convertRolesTypeToFirstLetters(Set<Role> roles){
        String[] rolesFirstLetters = new String[roles.size()];
        Role[] rolesFullNames = roles.toArray(new Role[roles.size()]);
        for(int i = 0; i < roles.size(); i++){
            String role = rolesFullNames[i].getName().split("_")[1];
            rolesFirstLetters[i] = role.substring(0, 1).toLowerCase();
        }
        return rolesFirstLetters;
    }
}
