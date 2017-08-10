package ua.rd.cm.services;

import java.util.List;

import ua.rd.cm.domain.User;
import ua.rd.cm.dto.RegistrationDto;

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


}
