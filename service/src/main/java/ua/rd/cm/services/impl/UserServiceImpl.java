package ua.rd.cm.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.rd.cm.domain.Role;
import ua.rd.cm.domain.User;
import ua.rd.cm.domain.UserInfo;
import ua.rd.cm.domain.VerificationToken;
import ua.rd.cm.dto.RegistrationDto;
import ua.rd.cm.repository.UserRepository;
import ua.rd.cm.services.MailService;
import ua.rd.cm.services.RoleService;
import ua.rd.cm.services.UserService;
import ua.rd.cm.services.VerificationTokenService;
import ua.rd.cm.services.preparator.ConfirmAccountPreparator;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleService roleService;
    private MailService mailService;
    private ModelMapper mapper;
    private VerificationTokenService tokenService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleService roleService,
                           MailService mailService, VerificationTokenService tokenService,
                           ModelMapper mapper, PasswordEncoder passwordEncode) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.mailService = mailService;
        this.tokenService = tokenService;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncode;
    }

    @Override
    public User find(Long id) {
        return userRepository.findOne(id);
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
        userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getByFirstName(String name) {
        return userRepository.findAllByFirstName(name);
    }

    @Override
    public User getByEmail(String email) {
       return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getByLastName(String lastName) {
        return userRepository.findAllByLastName(lastName);
    }

    @Override
    public boolean isEmailExist(String email) {
        return userRepository.findByEmail(email) != null;
    }

    @Override
    @Transactional
    public void registerNewUser(RegistrationDto dto) {
        User newUser = mapRegistrationDtoToUser(dto);
        save(newUser);
        if (User.UserStatus.UNCONFIRMED.equals(dto.getUserStatus())) {
            VerificationToken token = tokenService.createToken(newUser, VerificationToken.TokenType.CONFIRMATION);
            tokenService.saveToken(token);
            mailService.sendEmail(newUser, new ConfirmAccountPreparator(token, mailService.getUrl()));
        }
    }

    @Override
    @Transactional
    public void updateUserProfile(User user) {
        userRepository.save(user);
    }

    @Override
    public List<User> getByRoleExceptCurrent(User currentUser, String roleName) {
        Role role = roleService.getByName(roleName);
        return  userRepository.findAllByUserRoles(role).stream().filter(user -> user != currentUser).collect(Collectors.toList());
    }

    @Override
    public List<User> getByRolesExceptCurrent(User currentUser, String... roleNames) {
        List<Role> roles = new ArrayList<>();
        for(String roleName : roleNames){
            Role role = roleService.getByName(roleName);
            if (role != null){
                roles.add(role);
            }
        }
        return  userRepository.findAllByUserRoles(roles).stream().filter(user -> user != currentUser).collect(Collectors.toList());
    }

    @Override
    public boolean isAuthenticated(User user, String password) {
        String hashedPassword = user.getPassword();
        return passwordEncoder.matches(password, hashedPassword);
    }

    private User mapRegistrationDtoToUser(RegistrationDto dto) {
        User user = mapper.map(dto, User.class);
        user.setEmail(user.getEmail().toLowerCase());
        user.addRole(roleService.getByName(dto.getRoleName()));
        return user;
    }
}