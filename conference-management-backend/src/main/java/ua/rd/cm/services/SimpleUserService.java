package ua.rd.cm.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@Autowired
	public SimpleUserService(UserRepository userRepository,
							 RoleService roleService) {
		this.userRepository = userRepository;
		this.roleService = roleService;
	}

	@Override
	public User find(Long id) {
		return userRepository.findBySpecification(new UserById(id)).get(0);
	}

	@Override
	@Transactional
	public void save(User user) {
		if (user.getUserRoles().size() == 0) {
			user.addRole(roleService.getByName("SPEAKER"));
		}
		if (user.getPhoto() == null) {
			user.setPhoto("https://www.google.com.ua/url?sa=i&rct=j&q=&esrc=s&source=imgres&cd=&cad=rja&uact=8&ved=0ahUKEwiv6N6qmqvQAhWTKCwKHQRbDw0QjRwIBw&url=https%3A%2F%2Fwww.pinterest.com%2Fvolker513%2Fche-guevara%2F&psig=AFQjCNEny2Kuv7EyU_uiXwNkol1SCNWTqA&ust=1479314564214955");
		}
		if (user.getUserInfo() == null) {
		    user.setUserInfo(new UserInfo());
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
		List<User> users = userRepository.findBySpecification(new UserByEmail(email));
		if (users.isEmpty()){
			return null;
		}
		return users.get(0);
	}

	@Override
	public List<User> getByLastName(String lastName) {
		return userRepository.findBySpecification(new UserByLastName(lastName));
	}

	@Override
	public boolean isEmailExist(String email) {
		return !userRepository.findBySpecification(new UserByEmail(email)).isEmpty();
	}

	@Override
	@Transactional
	public void updateUserProfile(User user) {
		userRepository.updateUser(user);
	}


}
