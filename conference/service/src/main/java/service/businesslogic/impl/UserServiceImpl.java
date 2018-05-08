package service.businesslogic.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

import domain.model.Contact;
import domain.model.Role;
import domain.model.User;
import domain.model.UserInfo;
import domain.model.VerificationToken;
import domain.repository.RoleRepository;
import domain.repository.UserRepository;
import service.businesslogic.api.UserService;
import service.businesslogic.dto.InviteDto;
import service.businesslogic.dto.RegistrationDto;
import service.businesslogic.dto.UserBasicDto;
import service.businesslogic.dto.UserInfoDto;
import service.businesslogic.exception.EmailAlreadyExistsException;
import service.businesslogic.exception.NoSuchUserException;
import service.businesslogic.exception.PasswordMismatchException;
import service.businesslogic.exception.WrongRoleException;
import service.infrastructure.mail.MailService;
import service.infrastructure.mail.preparator.ConfirmAccountPreparator;
import service.infrastructure.mail.preparator.InvitePreparator;

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
    public void createSpeaker(User user) {
        if (user.getRoles().isEmpty()) {
            user.addRole(roleRepository.findByName(Role.ROLE_SPEAKER));
        }
        if (user.getUserInfo() == null) {
            user.setUserInfo(new UserInfo());
        }
        userRepository.save(user);
    }

    @Override
    public void confirm(User user) {
        user.setStatus(User.UserStatus.CONFIRMED);
        updateUser(user);
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
    public void registerSpeaker(RegistrationDto dto) {
        checkUserRegistration(dto);
        registerNewUser(dto);
    }

    private void registerNewUser(RegistrationDto dto) {
        User newUser = mapRegistrationDtoToUser(dto);
        createSpeaker(newUser);

        if (User.UserStatus.UNCONFIRMED.equals(dto.getUserStatus())) {
            VerificationToken token = VerificationToken.of(newUser, VerificationToken.TokenType.CONFIRMATION);
            tokenService.saveToken(token);
            mailService.sendEmail(newUser, new ConfirmAccountPreparator(token, mailService.getUrl()));
        }
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public List<User> getByRoleExceptCurrent(User currentUser, String roleName) {
        Role role = roleRepository.findByName(roleName);
        return userRepository.findAllByRolesIsIn(role).stream().filter(user -> user != currentUser)
                .collect(Collectors.toList());
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
        return userRepository.findAllByRolesIsIn(roles).stream().filter(user -> user != currentUser)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAuthenticated(User user, String password) {
        String hashedPassword = user.getPassword();
        return passwordEncoder.matches(password, hashedPassword);
    }

    @Override
    public void checkUserRegistration(RegistrationDto dto) {
        if (dto.passwordsDoNotMatch()) {
            throw new PasswordMismatchException("empty_fields");
        } else if (isEmailExist(dto.getEmail().toLowerCase())) {
            throw new EmailAlreadyExistsException("email_already_exists");
        } else {
            encodePassword(dto);
        }
    }

    @Override
    public void checkUserRegistrationByAdmin(RegistrationDto dto) {
        if (dto.getRoleName().equals(Role.ROLE_ADMIN)) {
            throw new WrongRoleException("wrong_role_name");
        }

    }

    @Override
    public UserInfoDto getUserDtoByEmail(String email) {
        User user = getByEmail(email);

        if (user == null) {
            throw new NoSuchUserException("No such user exists");
        }

        return userToDto(user);
    }

    @Override
    public UserInfoDto getUserDtoById(Long userId) {
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

    private void encodePassword(RegistrationDto dto) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
    }

    private UserInfoDto userToDto(User user) {
        if (user == null) {
            return null;
        }

        UserInfoDto dto = mapper.map(user, UserInfoDto.class);
        if (user.getPhoto() != null) {
            dto.setPhoto("myinfo/photo/" + user.getId());
        }
        List<Contact> contacts = user.getUserInfo().getContacts();
        contacts.forEach(dto::setContact);
        dto.setContacts(contacts);
        dto.setRole(user.getRoleNames().get(0));
        return dto;
    }

    private UserBasicDto userToUserBasicDto(User user) {
        UserBasicDto userBasicDto = mapper.map(user, UserBasicDto.class);
        userBasicDto.setRole(user.getRoleNames().get(0));
        return userBasicDto;
    }

    @Override
    public void inviteUser(InviteDto invite) {
        User user = new User();
        user.setEmail(invite.getEmail());
        String conferenceName = invite.getConferenceName();
        mailService.sendEmail(user, new InvitePreparator(conferenceName, mailService.getUrl()));
    }

    @Transactional
    @Override
    public boolean isTalkOrganiser(String userEmail, Long talkId) {
        User user = userRepository.findByEmail(userEmail);
        return user.isOrganizerForTalk(talkId);
    }

}