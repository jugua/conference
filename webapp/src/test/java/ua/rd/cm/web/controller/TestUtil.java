package ua.rd.cm.web.controller;


import ua.rd.cm.domain.ContactType;
import ua.rd.cm.domain.Role;
import ua.rd.cm.domain.User;
import ua.rd.cm.domain.UserInfo;

import java.util.*;

public class TestUtil {
    private String alreadyRegisteredEmail = "registered@gmail.com";

    protected User createUser(Role role, UserInfo info) {
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        User user = new User(1L, "FirstName", "LastName", alreadyRegisteredEmail, "pass", "url", User.UserStatus.CONFIRMED, info, roles);
        return user;
    }

    protected Role createSpeakerRole(){
        return new Role(1L, "SPEAKER");
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
}
