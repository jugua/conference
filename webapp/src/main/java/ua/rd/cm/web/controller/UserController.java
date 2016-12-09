package ua.rd.cm.web.controller;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.rd.cm.domain.ContactType;
import ua.rd.cm.domain.Role;
import ua.rd.cm.domain.User;
import ua.rd.cm.domain.UserInfo;
import ua.rd.cm.services.ContactTypeService;
import ua.rd.cm.services.UserInfoService;
import ua.rd.cm.services.UserService;
import ua.rd.cm.web.controller.dto.MessageDto;
import ua.rd.cm.web.controller.dto.RegistrationDto;
import ua.rd.cm.web.controller.dto.UserDto;
import ua.rd.cm.web.controller.dto.UserInfoDto;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private ModelMapper mapper;
    private UserService userService;
    private UserInfoService userInfoService;
    private ContactTypeService contactTypeService;
    private Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    public UserController(ModelMapper mapper, UserService userService, UserInfoService userInfoService,
                          ContactTypeService contactTypeService) {
        this.mapper = mapper;
        this.userService = userService;
        this.userInfoService = userInfoService;
        this.contactTypeService = contactTypeService;
    }

    @PostMapping
    public ResponseEntity register(@Valid @RequestBody RegistrationDto dto, BindingResult bindingResult, HttpServletRequest request){
        HttpStatus status;
        MessageDto message = new MessageDto();
        if (bindingResult.hasFieldErrors() || !isPasswordConfirmed(dto)){
            status = HttpStatus.BAD_REQUEST;
            message.setError("empty_fields");
            logger.error("Request for [api/user] is failed: validation is failed. [HttpServletRequest: " + request.toString() + "]");
        } else if (userService.isEmailExist(dto.getEmail().toLowerCase())){
            status = HttpStatus.CONFLICT;
            message.setError("email_already_exists");
            logger.error("Registration failed: " + dto.toString() +
                        ". Email '" + dto.getEmail() + "' is already in use. [HttpServletRequest: " + request.toString() + "]");
        } else {
            userService.save(dtoToEntity(dto));
            status = HttpStatus.ACCEPTED;
            message.setStatus("success");
        }
        return  ResponseEntity.status(status).body(message);
    }

    @GetMapping("/current")
    public ResponseEntity<UserDto> getCurrentUser(Principal principal){
        if (principal == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        User currentUser = userService.getByEmail(principal.getName());
        if (currentUser == null) {
            logger.error("Request for [api/user/current] is failed: User entity for current principal is not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } if (isUserUnconfirmed(currentUser)) {
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        } else {
            return new ResponseEntity<>(userToDto(currentUser), HttpStatus.ACCEPTED);
        }
    }

    @PostMapping("/current")
    public ResponseEntity updateUserInfo(@Valid @RequestBody UserInfoDto dto, Principal principal, BindingResult bindingResult) {
        HttpStatus status;
        if (bindingResult.hasFieldErrors()) {
            status = HttpStatus.BAD_REQUEST;
        } else if (principal == null) {
            status = HttpStatus.UNAUTHORIZED;
        } else {
            String userEmail = principal.getName();
            userInfoService.update(prepareNewUserInfo(userEmail, dto));
            status = HttpStatus.OK;
        }
        return new ResponseEntity(status);
    }

    private boolean isPasswordConfirmed(RegistrationDto dto) {
        return dto.getPassword().equals(dto.getConfirm());
    }

    private boolean isUserUnconfirmed(User currentUser) {
        return User.UserStatus.UNCONFIRMED.equals(currentUser.getStatus());
    }

    private UserInfo prepareNewUserInfo(String email, UserInfoDto dto) {
        User currentUser = userService.getByEmail(email);
        UserInfo currentUserInfo = userInfoDtoToEntity(dto);
        currentUserInfo.setId(currentUser.getUserInfo().getId());
        return currentUserInfo;
    }

    private User dtoToEntity(RegistrationDto dto) {
        User user = mapper.map(dto, User.class);
        user.setEmail(user.getEmail().toLowerCase());
        user.setStatus(User.UserStatus.UNCONFIRMED);
        return user;
    }

    private UserInfo userInfoDtoToEntity(UserInfoDto dto) {
        UserInfo userInfo = mapper.map(dto, UserInfo.class);
        Map<ContactType, String> contacts = userInfo.getContacts();
        contacts.put(contactTypeService.findByName("LinkedIn").get(0), dto.getLinkedIn());
        contacts.put(contactTypeService.findByName("Twitter").get(0), dto.getTwitter());
        contacts.put(contactTypeService.findByName("FaceBook").get(0), dto.getFacebook());
        contacts.put(contactTypeService.findByName("Blog").get(0), dto.getBlog());
        userInfo.setContacts(contacts);
        return userInfo;
    }

    private UserDto userToDto(User user){
        UserDto dto = mapper.map(user, UserDto.class);
        if (user.getPhoto() != null) {
            dto.setPhoto("api/user/current/photo/" + user.getId());
        }
        dto.setLinkedin(user.getUserInfo().getContacts().get(contactTypeService.findByName("LinkedIn").get(0)));
        dto.setTwitter(user.getUserInfo().getContacts().get(contactTypeService.findByName("Twitter").get(0)));
        dto.setFacebook(user.getUserInfo().getContacts().get(contactTypeService.findByName("FaceBook").get(0)));
        dto.setBlog(user.getUserInfo().getContacts().get(contactTypeService.findByName("Blog").get(0)));
        dto.setRoles(convertRolesTypeToFirstLetters(user.getUserRoles()));
        return  dto;
    }

    private String[] convertRolesTypeToFirstLetters(Set<Role> roles){
        String[] rolesFirstLetters = new String[roles.size()];
        Role[] rolesFullNames = roles.toArray(new Role[roles.size()]);
        for(int i = 0; i < roles.size(); i++){
            rolesFirstLetters[i] = rolesFullNames[i].getName().substring(0, 1).toLowerCase();
        }
        return rolesFirstLetters;
    }
}