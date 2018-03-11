package web.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import domain.model.Contact;
import domain.model.ContactType;
import domain.model.Role;
import domain.model.User;
import domain.model.UserInfo;

public class TestData {

    public static final String ORGANISER_EMAIL = "trybel@gmail.com";
    public static final String SPEAKER_EMAIL = "ivanova@gmail.com";

    public static User speaker() {
        return user("Olya", "Ivanova", SPEAKER_EMAIL, speakerRole(), userInfo());
    }

    public static User organizer() {
        return user("Artem", "Trybel", ORGANISER_EMAIL, organizerRole(), userInfo());
    }

    private static User user(String firstname, String lastname, String email, Role role, UserInfo userInfo) {
        User organiser = new User();
        organiser.setId(1L);
        organiser.setFirstName(firstname);
        organiser.setLastName(lastname);
        organiser.setEmail(email);
        organiser.setPassword("123456");
        organiser.setPhoto("myinfo/photo/");
        organiser.setStatus(User.UserStatus.CONFIRMED);
        organiser.setUserInfo(userInfo);
        organiser.setRoles(Collections.singleton(role));
        return organiser;
    }

    private static Role speakerRole() {
        return new Role(1L, Role.ROLE_ORGANISER);
    }

    private static Role organizerRole() {
        return new Role(2L, Role.ROLE_ORGANISER);
    }

    protected static UserInfo userInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(1L);
        userInfo.setShortBio("bio");
        userInfo.setJobTitle("job");
        userInfo.setPastConference("pastConference");
        userInfo.setCompany("EPAM");
        userInfo.setContacts(contacts());
        userInfo.setAdditionalInfo("addInfo");
        return userInfo;
    }

    private static List<Contact> contacts() {
        ContactType contactType = new ContactType(1L, "LinkedIn");
        ContactType contactType2 = new ContactType(2L, "Twitter");
        ContactType contactType3 = new ContactType(3L, "Facebook");
        ContactType contactType4 = new ContactType(4L, "Blog");

        return Arrays.asList(
                Contact.builder().id(1L).value("url1").contactType(contactType).build(),
                Contact.builder().id(2L).value("url2").contactType(contactType2).build(),
                Contact.builder().id(3L).value("url3").contactType(contactType3).build(),
                Contact.builder().id(4L).value("url4").contactType(contactType4).build()
        );
    }

}
