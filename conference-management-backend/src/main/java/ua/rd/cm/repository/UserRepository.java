package ua.rd.cm.repository;

import java.util.List;

import ua.rd.cm.domain.User;
import ua.rd.cm.repository.specification.Specification;

/**
 * Repository 
 * 
 * @see User
 * 
 *
 */
public interface UserRepository {

	void saveUser(User user);
	
	void removeUser(User user);
	
	void updateUser(User user);
	
	List<User> findBySpecification(Specification<User> spec);
	
}
