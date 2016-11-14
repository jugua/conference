package ua.rd.cm.services;

import java.util.List;

import ua.rd.cm.domain.User;
import ua.rd.cm.repository.UserRepository;
import ua.rd.cm.repository.specification.user.IsEmailExist;
import ua.rd.cm.repository.specification.user.UserByEmail;
import ua.rd.cm.repository.specification.user.UserByFirstName;
import ua.rd.cm.repository.specification.user.UserById;
import ua.rd.cm.repository.specification.user.UserByLastName;

public class SimpleUserService implements UserService{

	private UserRepository userRepository;
	
	public SimpleUserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User find(Long id) {
		return userRepository.findBySpecification(new UserById(id)).get(0);
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public List<User> getByFirstName(String name) {
		return userRepository.findBySpecification(new UserByFirstName(name));
	}

	@Override
	public User getByEmail(String email) {
		return userRepository.findBySpecification(new UserByEmail(email)).get(0);
	}

	@Override
	public User getByLastName(String lastName) {
		return userRepository.findBySpecification(new UserByLastName(lastName)).get(0);
	}

	@Override
	public boolean isEmailExit(String email) {
		return !userRepository.findBySpecification(new IsEmailExist(email)).isEmpty();
	}

}
