package ua.rd.cm.services.businesslogic.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import ua.rd.cm.domain.Role;
import ua.rd.cm.domain.User;
import ua.rd.cm.domain.UserInfo;
import ua.rd.cm.domain.VerificationToken;
import ua.rd.cm.dto.RegistrationDto;
import ua.rd.cm.dto.UserBasicDto;
import ua.rd.cm.dto.UserDto;
import ua.rd.cm.infrastructure.mail.MailService;
import ua.rd.cm.infrastructure.mail.preparator.ConfirmAccountPreparator;
import ua.rd.cm.repository.RoleRepository;
import ua.rd.cm.repository.UserRepository;
import ua.rd.cm.services.businesslogic.UserService;
import ua.rd.cm.services.exception.EmailAlreadyExistsException;
import ua.rd.cm.services.exception.NoSuchUserException;
import ua.rd.cm.services.exception.PasswordMismatchException;
import ua.rd.cm.services.exception.WrongRoleException;


@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private MailService mailService;
    private ModelMapper mapper;
    private VerificationTokenService tokenService;
    private PasswordEncoder passwordEncoder;

    @Override
    public User find(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    @Transactional
    public void save(User user) {
        if (user.getRoles().size() == 0) {
            user.addRole(roleRepository.findByName(Role.SPEAKER));
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
        Role role = roleRepository.findByName(roleName);
        return userRepository.findAllByRolesIsIn(role).stream().filter(user -> user != currentUser).collect
                (Collectors.toList());
    }

    @Override
    public List<User> getByRolesExceptCurrent(User currentUser, String... roleNames) {
        List<Role> roles = new ArrayList<>();
        for (String roleName : roleNames) {
            Role role = roleRepository.findByName(roleName);
            if (role != null) {
                roles.add(role);
            }
        }
        return userRepository.findAllByRolesIsIn(roles).stream().filter(user -> user != currentUser).collect
                (Collectors.toList());
    }

    @Override
    public boolean isAuthenticated(User user, String password) {
        String hashedPassword = user.getPassword();
        return passwordEncoder.matches(password, hashedPassword);
    }

    @Override
    public void checkUserRegistration(RegistrationDto dto) {
        if (!isPasswordConfirmed(dto)) {
            throw new PasswordMismatchException("empty_fields");
        } else if (isEmailExist(dto.getEmail().toLowerCase())) {
            throw new EmailAlreadyExistsException("email_already_exists");
        } else {
            encodePassword(dto);
        }
    }

    @Override
    public void checkUserRegistrationByAdmin(RegistrationDto dto) {
        if (dto.getRoleName().equals(Role.ADMIN)) {
            throw new WrongRoleException("wrong_role_name");
        }

    }

    @Override
    public UserDto getUserDtoByEmail(String email) {
        User user = getByEmail(email);

        if (user == null) {
            throw new NoSuchUserException("No such user exists");
        }

        return userToDto(user);
    }

    @Override
    public UserDto getUserDtoById(Long userId) {
        User user = find(userId);
        return userToDto(user);
    }

    @Override
    public List<UserBasicDto> getUserBasicDtoByRoleExpectCurrent(User currentUser, String... roles) {
        List<User> users = getByRolesExceptCurrent(currentUser, roles);
        List<UserBasicDto> userDtoList = new ArrayList<>();
        if (users != null) {
            for (User user : users) {
                userDtoList.add(userToUserBasicDto(user));
            }
        }

        return userDtoList;
    }

    private User mapRegistrationDtoToUser(RegistrationDto dto) {
        User user = mapper.map(dto, User.class);
        user.setEmail(user.getEmail().toLowerCase());
        user.addRole(roleRepository.findByName(dto.getRoleName()));
        return user;
    }

    private boolean isPasswordConfirmed(RegistrationDto dto) {
        return dto.getPassword().equals(dto.getConfirm());
    }

    private void encodePassword(RegistrationDto dto) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
    }

    private UserDto userToDto(User user) {
        UserDto dto = mapper.map(user, UserDto.class);
        if (user.getPhoto() != null) {
            dto.setPhoto("myinfo/photo/" + user.getId());
        }
        user.getUserInfo().getContacts().forEach(dto::setContact);
        dto.setRoles(user.getRoleNames());
        return dto;
    }

    private UserBasicDto userToUserBasicDto(User user) {
        UserBasicDto userBasicDto = mapper.map(user, UserBasicDto.class);
        userBasicDto.setRoles(user.getRoleNames());
        return userBasicDto;
    }

}