package service.businesslogic.api;

import java.util.List;

import domain.model.Contact;
import domain.model.User;
import service.businesslogic.dto.InviteDto;
import service.businesslogic.dto.RegistrationDto;
import service.businesslogic.dto.UserBasicDto;
import service.businesslogic.dto.UserInfoDto;

public interface UserService {

    User find(Long id);

    void createSpeaker(User user);

    void confirm(User user);

    void updateUser(User user);

    List<User> findAll();

    List<String> getUserNames();

    void updateContacts(long id, List<Contact> contacts);

    List<User> getByFirstName(String name);

    User getByEmail(String email);

    List<User> getByLastName(String lastName);

    boolean isEmailExist(String email);

    void registerSpeaker(RegistrationDto dto);

    List<User> findOtherUsersWithSameRole(User currentUser, String roleName);

    List<User> findOtherUsersByRoles(User currentUser, String... rolesNames);

    boolean isAuthenticated(User user, String password);

    void checkUserRegistrationByAdmin(RegistrationDto dto);

    UserInfoDto getUserDtoByEmail(String email);

    UserInfoDto getUserDtoById(Long userId);

    List<UserBasicDto> getUserBasicDtoByRolesExpectCurrent(User currentUser, String... roles);

    void inviteUser(InviteDto invite);

    User findUserByEmail(String email);

}
