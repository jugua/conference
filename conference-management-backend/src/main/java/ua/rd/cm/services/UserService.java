package ua.rd.cm.services;

import java.util.List;

import ua.rd.cm.domain.User;

public interface UserService {

	User find(Long id);
	
	List<User> findAll();
	
	List<User> getByName(String name);
	
	User getByEmail(String email);
	
	User getByLastName(String lastName);
	
	boolean isEmailExit(String email);
}
