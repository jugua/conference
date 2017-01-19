package ua.rd.cm.web.controller;


import ua.rd.cm.domain.ContactType;
import ua.rd.cm.domain.Role;
import ua.rd.cm.domain.User;
import ua.rd.cm.domain.UserInfo;
import ua.rd.cm.services.ContactTypeService;
import ua.rd.cm.services.SimpleContactTypeService;
import ua.rd.cm.services.UserService;

import java.util.*;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class TestUtil {
    private String alreadyRegisteredEmail = "ivanova@gmail.com";
    protected static final String ORGANISER_EMAIL = "trybel@gmail.com";
    protected static final String SPEAKER_EMAIL = "ivanova@gmail.com";

    protected User createUser(Role role, UserInfo info) {
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        User user = new User(1L,"Olya","Ivanova",
                alreadyRegisteredEmail, "123456",
                "api/user/current/photo/", User.UserStatus.CONFIRMED, createUserInfo(), roles);
        return user;
    }

    protected User createUser() {
        Set<Role> roles = new HashSet<>();
        roles.add(createSpeakerRole());
        User user = new User(1L,"Olya","Ivanova",
                alreadyRegisteredEmail, "123456",
                "api/user/current/photo/", User.UserStatus.CONFIRMED, createUserInfo(), roles);
        return user;
    }
    protected Role createSpeakerRole(){
        return new Role(1L, "ROLE_SPEAKER");
    }
    protected UserInfo createUserInfo(){
        ContactType contactType = new ContactType(1L, "LinkedIn");
        ContactType contactType2 = new ContactType(2L, "Twitter");
        ContactType contactType3 = new ContactType(3L, "FaceBook");
        ContactType contactType4 = new ContactType(4L, "Blog");

        Map<ContactType, String> contacts = new HashMap<ContactType, String>() {{
            put(contactType, "LinkedIn");
            put(contactType2, "Twitter");
            put(contactType3, "FaceBook");
            put(contactType4, "Blog");
        }};
        return new UserInfo(1L, "bio", "job", "pastConference", "EPAM", contacts, "addInfo");
    }

    protected void createSpeakerAndOrganiser(UserService userService){

        Set<Role> speakerRole = new HashSet<>();
        speakerRole.add(new Role(2L, Role.SPEAKER));
        User speakerUser = new User(1L,"Olya","Ivanova",
                "ivanova@gmail.com", "123456",
                "api/user/current/photo/", User.UserStatus.CONFIRMED, createUserInfo(), speakerRole);

        Set<Role> organiserRole = new HashSet<>();
        organiserRole.add(new Role(1L, Role.ORGANISER));
        User organiserUser = new User(1L,"Artem","Trybel",
                "trybel@gmail.com", "123456",
                "api/user/current/photo/", User.UserStatus.CONFIRMED, createUserInfo(), organiserRole);
        when(userService.getByEmail(eq(SPEAKER_EMAIL))).thenReturn(speakerUser);
        when(userService.getByEmail(eq(ORGANISER_EMAIL))).thenReturn(organiserUser);
    }

}
