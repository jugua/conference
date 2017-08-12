package ua.rd.cm.services;

import java.util.List;

import ua.rd.cm.domain.User;
import ua.rd.cm.domain.UserInfo;
import ua.rd.cm.dto.RegistrationDto;
import ua.rd.cm.dto.UserBasicDto;
import ua.rd.cm.dto.UserDto;

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

    List<User> getByRole(String role);

    List<User> getByRoleExceptCurrent(User currentUser, String roleName);

    List<User> getByRolesExceptCurrent(User currentUser, String... roles);

    boolean isAuthenticated(User user, String password);

	void checkUserRegistration(RegistrationDto dto);

	void checkUserRegistrationByAdmin(RegistrationDto dto);

	UserDto getUserDtoByEmail(String email);

	UserDto getUserDtoById(Long userId);

	List<UserBasicDto> getUserBasicDtoByRoleExpectCurrent(User currentUser, String... roles);

	UserInfo prepareNewUserInfoForUpdate(String email, UserDto dto);

	User prepareNewUserForUpdate(String email, UserDto dto);
}
