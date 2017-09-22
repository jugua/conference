package ua.rd.cm.web.controller;


import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ua.rd.cm.domain.ContactType;
import ua.rd.cm.domain.Role;
import ua.rd.cm.domain.User;
import ua.rd.cm.domain.UserInfo;
import ua.rd.cm.services.businesslogic.UserService;

public class TestUtil {
    protected static final String ORGANISER_EMAIL = "trybel@gmail.com";
    protected static final String SPEAKER_EMAIL = "ivanova@gmail.com";
    public static final String SPEAKER_ROLE = "SPEAKER";
    public static final String ORGANISER_ROLE = "ORGANISER";
    public static final String ADMIN_ROLE = "ADMIN";

    protected User createUser(Role role, UserInfo info) {
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        User user = new User();
        user.setId(1L);
        user.setFirstName("Olya");
        user.setLastName("Ivanova");
        String alreadyRegisteredEmail = "ivanova@gmail.com";
        user.setEmail(alreadyRegisteredEmail);
        user.setPassword("123456");
        user.setPhoto("myinfo/photo/");
        user.setStatus(User.UserStatus.CONFIRMED);
        user.setUserInfo(createUserInfo());
        user.setUserRoles(roles);
        return user;
    }

    protected User createUser() {
        return createUser(createSpeakerRole(), createUserInfo());
    }

    protected Role createSpeakerRole() {
        return new Role(1L, "ROLE_SPEAKER");
    }

    protected UserInfo createUserInfo() {
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

        UserInfo userInfo = new UserInfo();
        userInfo.setId(1L);
        userInfo.setShortBio("bio");
        userInfo.setJobTitle("job");
        userInfo.setPastConference("pastConference");
        userInfo.setCompany("EPAM");
        userInfo.setContacts(contacts);
        userInfo.setAdditionalInfo("addInfo");
        return userInfo;
    }

    protected void createSpeakerAndOrganiser(UserService userService) {

        Set<Role> speakerRole = new HashSet<>();
        speakerRole.add(new Role(2L, Role.SPEAKER));

        User speakerUser = new User();
        speakerUser.setId(1L);
        speakerUser.setFirstName("Olya");
        speakerUser.setLastName("Ivanova");
        speakerUser.setEmail("ivanova@gmail.com");
        speakerUser.setPassword("123456");
        speakerUser.setPhoto("myinfo/photo/");
        speakerUser.setStatus(User.UserStatus.CONFIRMED);
        speakerUser.setUserInfo(createUserInfo());
        speakerUser.setUserRoles(speakerRole);

        Set<Role> organiserRole = new HashSet<>();
        organiserRole.add(new Role(1L, Role.ORGANISER));

        User organiserUser = new User();
        organiserUser.setId(1L);
        organiserUser.setFirstName("Artem");
        organiserUser.setLastName("Trybel");
        organiserUser.setEmail("trybel@gmail.com");
        organiserUser.setPassword("123456");
        organiserUser.setPhoto("myinfo/photo/");
        organiserUser.setStatus(User.UserStatus.CONFIRMED);
        organiserUser.setUserInfo(createUserInfo());
        organiserUser.setUserRoles(speakerRole);
        when(userService.getByEmail(eq(SPEAKER_EMAIL))).thenReturn(speakerUser);
        when(userService.getByEmail(eq(ORGANISER_EMAIL))).thenReturn(organiserUser);
    }

}
