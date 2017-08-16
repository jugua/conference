package ua.rd.cm.services;

import ua.rd.cm.domain.User;
import ua.rd.cm.domain.UserInfo;
import ua.rd.cm.dto.RegistrationDto;
import ua.rd.cm.dto.UserBasicDto;
import ua.rd.cm.dto.UserDto;

import java.util.List;

public interface UserService {

    User find(Long id);

    void save(User user);

    void updateUserProfile(User user);

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

    UserDto getUserDtoByEmail(String email);

    UserDto getUserDtoById(Long userId);

    List<UserBasicDto> getUserBasicDtoByRoleExpectCurrent(User currentUser, String... roles);

    UserInfo prepareNewUserInfoForUpdate(String email, UserDto dto);

    User prepareNewUserForUpdate(String email, UserDto dto);
}
