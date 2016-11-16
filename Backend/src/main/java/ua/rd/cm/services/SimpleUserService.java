package ua.rd.cm.services;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.rd.cm.domain.User;
import ua.rd.cm.repository.UserRepository;
import ua.rd.cm.repository.specification.user.UserByEmail;
import ua.rd.cm.repository.specification.user.UserByFirstName;
import ua.rd.cm.repository.specification.user.UserById;
import ua.rd.cm.repository.specification.user.UserByLastName;

@Service
public class SimpleUserService implements UserService{

	private UserRepository userRepository;
	private RoleService roleService;
	
	@Inject
	public SimpleUserService(UserRepository userRepository,
							 RoleService roleService) {
		this.userRepository = userRepository;
		this.roleService = roleService;
	}

	@Override
	public User find(Long id) {
		return userRepository.findBySpecification(new UserById(id)).get(0);
	}

	@Transactional
	@Override
	public void save(User user) {
		if (user.getUserRoles() == null) {
			user.addRole(roleService.getByName("SPEAKER").get(0));
		}

		userRepository.saveUser(user);
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
	public List<User> getByLastName(String lastName) {
		return userRepository.findBySpecification(new UserByLastName(lastName));
	}

	@Override
	public boolean isEmailExist(String email) {
		return !userRepository.findBySpecification(new UserByEmail(email)).isEmpty();
	}

	@Transactional
	@Override
	public void updateUserProfile(User user) {
		userRepository.updateUser(user);
	}


}
