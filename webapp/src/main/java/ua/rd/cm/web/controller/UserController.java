package ua.rd.cm.web.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.rd.cm.domain.ContactType;
import ua.rd.cm.domain.Role;
import ua.rd.cm.domain.User;
import ua.rd.cm.domain.UserInfo;
import ua.rd.cm.dto.RegistrationDto;
import ua.rd.cm.services.ContactTypeService;
import ua.rd.cm.services.UserInfoService;
import ua.rd.cm.services.UserService;
import ua.rd.cm.web.controller.dto.MessageDto;
import ua.rd.cm.web.controller.dto.UserBasicDto;
import ua.rd.cm.web.controller.dto.UserDto;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.*;

@AllArgsConstructor(onConstructor = @__({@Autowired}))
@RestController
@RequestMapping("/api/user")
@Log4j
public class UserController {
    private final ModelMapper mapper;
    private final UserService userService;
    private final UserInfoService userInfoService;
    private final ContactTypeService contactTypeService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity register(@Valid @RequestBody RegistrationDto dto,
                                   BindingResult bindingResult,
                                   HttpServletRequest request
    ) {
        dto.setUserStatus(User.UserStatus.UNCONFIRMED);
        dto.setRoleName(Role.SPEAKER);
        return processUserRegistration(dto, bindingResult, request);
    }

    @PreAuthorize("hasRole(\"ADMIN\")")
    @PostMapping("/create")
    public ResponseEntity registerByAdmin(@Valid @RequestBody RegistrationDto dto,
                                          BindingResult bindingResult,
                                          HttpServletRequest request
    ) {
        if (!Arrays.asList(Role.SPEAKER, Role.ORGANISER).contains(dto.getRoleName())) {
            MessageDto message = new MessageDto();
            message.setError("wrong_role_name");
            return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
        }
        dto.setUserStatus(User.UserStatus.CONFIRMED);
        return processUserRegistration(dto, bindingResult, request);
    }

    @GetMapping("/current")
    public ResponseEntity getCurrentUser(Principal principal) {
        if (principal == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        User currentUser = userService.getByEmail(principal.getName());
        if (currentUser == null) {
            log.error("Request for [api/user/current] is failed: User entity for current principal is not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(userToDto(currentUser), HttpStatus.ACCEPTED);
        }
    }

    @PostMapping(value = "/current")
    public ResponseEntity updateUserInfo(@Valid @RequestBody UserDto dto, Principal principal, BindingResult bindingResult) {
        HttpStatus status;
        if (bindingResult.hasFieldErrors()) {
            status = HttpStatus.BAD_REQUEST;
        } else if (principal == null) {
            status = HttpStatus.UNAUTHORIZED;
        } else {
            String userEmail = principal.getName();
            userInfoService.update(prepareNewUserInfo(userEmail, dto));
            userService.updateUserProfile(prepareNewUser(userEmail, dto));
            status = HttpStatus.OK;
        }
        return new ResponseEntity(status);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity getUserById(@PathVariable("id") Long userId, HttpServletRequest request) {
        MessageDto message = new MessageDto();
        if (!request.isUserInRole("ORGANISER")) {
            message.setError("unauthorized");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
        }
        User user = userService.find(userId);
        if (user == null) {
            message.setError("not_found_user");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

        UserDto userDto = userToDto(user);
        // userDto.setContactTypeService(contactTypeService);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/admin")
    public ResponseEntity getAllUsersForAdmin(HttpServletRequest request) {
        MessageDto message = new MessageDto();
        User currentUser = getAuthorizedUser(request);
        if (currentUser == null) {
            message.setError("unauthorized");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
        }
        List<User> users = userService.getByRolesExceptCurrent(currentUser, Role.ORGANISER, Role.SPEAKER);
        return new ResponseEntity<>(userToUserBasicDto(users), HttpStatus.OK);
    }

    private User getAuthorizedUser(HttpServletRequest request) {
        boolean inRole = request.isUserInRole(Role.ADMIN);
        if (inRole) {
            String userEmail = request.getUserPrincipal().getName();
            User user = userService.getByEmail(userEmail);
            if ((user != null) && (user.getEmail() != null)) {
                return user;
            }
        }
        return null;
    }

    private UserBasicDto userToUserBasicDto(User user) {
        UserBasicDto userBasicDto = mapper.map(user, UserBasicDto.class);
        userBasicDto.setRoles(convertRolesTypeToFirstLetters(user.getUserRoles()));
        return userBasicDto;
    }

    private List<UserBasicDto> userToUserBasicDto(List<User> users) {
        List<UserBasicDto> userDtoList = new ArrayList<>();
        if (users != null) {
            for (User user : users) {
                userDtoList.add(userToUserBasicDto(user));
            }
        }
        return userDtoList;
    }

    private ResponseEntity processUserRegistration(RegistrationDto dto, BindingResult bindingResult, HttpServletRequest request) {
        HttpStatus status;
        MessageDto message = new MessageDto();
        if (bindingResult.hasFieldErrors() || !isPasswordConfirmed(dto)) {
            status = HttpStatus.BAD_REQUEST;
            message.setError("empty_fields");
            log.error("Request for [api/user] is failed: validation is failed. [HttpServletRequest: " + request.toString() + "]");
        } else if (userService.isEmailExist(dto.getEmail().toLowerCase())) {
            status = HttpStatus.CONFLICT;
            message.setError("email_already_exists");
            log.error("Registration failed: " + dto.toString() +
                    ". Email '" + dto.getEmail() + "' is already in use. [HttpServletRequest: " + request.toString() + "]");
        } else {
            encodePassword(dto);
            userService.registerNewUser(dto);
            status = HttpStatus.ACCEPTED;
            message.setStatus("success");
        }
        return ResponseEntity.status(status).body(message);
    }

    private void encodePassword(RegistrationDto dto) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
    }

    private boolean isPasswordConfirmed(RegistrationDto dto) {
        return dto.getPassword().equals(dto.getConfirm());
    }

    private UserInfo prepareNewUserInfo(String email, UserDto dto) {
        User currentUser = userService.getByEmail(email);
        UserInfo currentUserInfo = userInfoDtoToEntity(dto);
        currentUserInfo.setId(currentUser.getUserInfo().getId());
        return currentUserInfo;
    }

    private User prepareNewUser(String email, UserDto dto) {
        User currentUser = userService.getByEmail(email);
        currentUser.setFirstName(dto.getFirstName());
        currentUser.setLastName(dto.getLastName());
        return currentUser;
    }

    private UserInfo userInfoDtoToEntity(UserDto dto) {
        UserInfo userInfo = mapper.map(dto, UserInfo.class);
        Map<ContactType, String> contacts = userInfo.getContacts();
        contacts.put(contactTypeService.findByName("LinkedIn").get(0), dto.getLinkedIn());
        contacts.put(contactTypeService.findByName("Twitter").get(0), dto.getTwitter());
        contacts.put(contactTypeService.findByName("FaceBook").get(0), dto.getFacebook());
        contacts.put(contactTypeService.findByName("Blog").get(0), dto.getBlog());
        userInfo.setContacts(contacts);
        return userInfo;
    }

    private UserDto userToDto(User user) {
        UserDto dto = mapper.map(user, UserDto.class);
        if (user.getPhoto() != null) {
            dto.setPhoto("api/user/current/photo/" + user.getId());
        }
        dto.setLinkedIn(user.getUserInfo().getContacts().get(contactTypeService.findByName("LinkedIn").get(0)));
        dto.setTwitter(user.getUserInfo().getContacts().get(contactTypeService.findByName("Twitter").get(0)));
        dto.setFacebook(user.getUserInfo().getContacts().get(contactTypeService.findByName("FaceBook").get(0)));
        dto.setBlog(user.getUserInfo().getContacts().get(contactTypeService.findByName("Blog").get(0)));
        dto.setRoles(convertRolesTypeToFirstLetters(user.getUserRoles()));
        return dto;
    }

    private String[] convertRolesTypeToFirstLetters(Set<Role> roles) {
        String[] rolesFirstLetters = new String[roles.size()];
        Role[] rolesFullNames = roles.toArray(new Role[roles.size()]);
        for (int i = 0; i < roles.size(); i++) {
            String role = rolesFullNames[i].getName().split("_")[1];
            rolesFirstLetters[i] = role.substring(0, 1).toLowerCase();
        }
        return rolesFirstLetters;
    }
}