package service.businesslogic.api;

import java.util.List;

import domain.model.User;
import service.businesslogic.dto.InviteDto;
import service.businesslogic.dto.RegistrationDto;
import service.businesslogic.dto.UserBasicDto;
import service.businesslogic.dto.UserInfoDto;

public interface UserService {

    User find(Long id);

    void save(User user);

    void confirm(User user);

    void updateUser(User user);

    List<User> findAll();

    List<User> getByFirstName(String name);

    User getByEmail(String email);

    List<User> getByLastName(String lastName);

    boolean isEmailExist(String email);

    void registerNewUser(RegistrationDto dto);

    List<User> getByRoleExceptCurrent(User currentUser, String roleName);

    List<User> getByRolesExceptCurrent(User currentUser, String... rolesNames);

    boolean isAuthenticated(User user, String password);

    void checkUserRegistration(RegistrationDto dto);

    void checkUserRegistrationByAdmin(RegistrationDto dto);

    UserInfoDto getUserDtoByEmail(String email);

    UserInfoDto getUserDtoById(Long userId);

    List<UserBasicDto> getUserBasicDtoByRoleExpectCurrent(User currentUser, String... roles);
    
    void inviteUser(InviteDto invite);

	boolean isTalkOrganiser(String userMail, Long talkId);

}
