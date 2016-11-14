package ua.rd.cm.services;

import java.util.List;

import ua.rd.cm.domain.User;

public interface UserService {

	User find(Long id);

	void save(User user);
	
	void updateUserProfile(User user);
	
	List<User> findAll();
	
	List<User> getByFirstName(String name);
	
	User getByEmail(String email);
	
	List<User> getByLastName(String lastName);
	
	boolean isEmailExit(String email);
}
