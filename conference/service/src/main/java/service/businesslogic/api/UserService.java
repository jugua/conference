package service.businesslogic.api;

import java.util.List;

import domain.model.Contact;
import domain.model.User;
import service.businesslogic.dto.InviteDto;
import service.businesslogic.dto.RegistrationDto;
import service.businesslogic.dto.UserBasicDto;
import service.businesslogic.dto.UserInfoDto;

public interface UserService {

    List<User> getAll();

    User getById(Long id);

    User getByEmail(String email);

    List<User> getByFirstName(String firstName);

    List<User> getByLastName(String lastName);

    List<User> getOtherUsersWithSameRole(User currentUser, String roleName);

    List<User> getOtherUsersByRoles(User currentUser, String... rolesNames);

    void createSpeaker(User user);

    void confirm(User user);

    void updateUser(User user);

    void updateContacts(long id, List<Contact> contacts);

    boolean isEmailExist(String email);

    void inviteUser(InviteDto invite);

    void registerSpeaker(RegistrationDto dto);

    void checkUserRegistrationByAdmin(RegistrationDto dto);

    @Deprecated //TODO: this method is part of representation. Move it on upper level.
    List<String> getUserNames();

    @Deprecated //TODO: this method should be part of SecurityService or similar.
    boolean isAuthenticated(User user, String password);

    @Deprecated //TODO: this method should be moved to UserInfoService or must not exposed like this.
    UserInfoDto getUserInfoDtoByEmail(String email);

    @Deprecated //TODO: this method should be moved to UserInfoService or must not exposed like this.
    UserInfoDto getUserInfoDtoById(Long userId);

    @Deprecated //TODO: this method is part of representation. Move it on upper level.
    List<UserBasicDto> getUserBasicDtoByRolesExceptCurrent(User currentUser, String... roles);

}
