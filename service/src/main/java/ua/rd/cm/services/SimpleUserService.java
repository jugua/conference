package ua.rd.cm.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.rd.cm.domain.Role;
import ua.rd.cm.domain.User;
import ua.rd.cm.domain.UserInfo;
import ua.rd.cm.domain.VerificationToken;
import ua.rd.cm.repository.UserRepository;
import ua.rd.cm.repository.specification.AndSpecification;
import ua.rd.cm.repository.specification.WhereSpecification;
import ua.rd.cm.repository.specification.user.*;
import ua.rd.cm.repository.specification.user.UserByEmail;
import ua.rd.cm.repository.specification.user.UserByFirstName;
import ua.rd.cm.repository.specification.user.UserById;
import ua.rd.cm.repository.specification.user.UserByLastName;
import ua.rd.cm.services.preparator.ConfirmAccountPreparator;

@Service
public class SimpleUserService implements UserService{
	
	private UserRepository userRepository;
	private RoleService roleService;
	private MailService mailService;
	private VerificationTokenService tokenService;

	@Autowired
	public SimpleUserService(UserRepository userRepository, RoleService roleService,
							 MailService mailService, VerificationTokenService tokenService) {
		this.userRepository = userRepository;
		this.roleService = roleService;
		this.mailService = mailService;
		this.tokenService = tokenService;
	}

	@Override
	public User find(Long id) {
		List<User> users = userRepository.findBySpecification(new WhereSpecification<>(new UserById(id)));
		return users.isEmpty() ? null : users.get(0);
	}

	@Override
	@Transactional
	public void save(User user) {
		if (user.getUserRoles().size() == 0) {
			user.addRole(roleService.getByName(Role.SPEAKER));
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
		List<User> users = userRepository.findBySpecification(new WhereSpecification<>(new UserByEmail(email)));
		if (users.isEmpty()){
			return null;
		}
		return users.get(0);
	}

	@Override
	public List<User> getByLastName(String lastName) {
		return userRepository.findBySpecification(new WhereSpecification<>(new UserByLastName(lastName)));
	}

	@Override
	public boolean isEmailExist(String email) {
		return !userRepository.findBySpecification(new WhereSpecification<>(new UserByEmail(email))).isEmpty();
	}

	@Override
	@Transactional
	public void registerNewUser(User user) {
		save(user);
		VerificationToken token = tokenService.createToken(user, VerificationToken.TokenType.CONFIRMATION);
		tokenService.saveToken(token);
		mailService.sendEmail(user, new ConfirmAccountPreparator(token));
	}

	@Override
	@Transactional
	public void updateUserProfile(User user) {
		userRepository.updateUser(user);
	}

	@Override
	public List<User> getByRole(String role) {
		return userRepository.findBySpecification(new UserByRoleJoin(role));
	}

	@Override
	public List<User> getByRoleExceptCurrent(User currentUser, String roleName) {
		return userRepository.findBySpecification(
				new AndSpecification<>(
						new UserByRoleJoin(roleName),
						new UserExceptThisById(currentUser.getId())
				)
		);
	}
}
