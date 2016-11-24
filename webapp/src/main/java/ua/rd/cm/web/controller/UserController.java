package ua.rd.cm.web.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.rd.cm.domain.ContactType;
import ua.rd.cm.domain.Role;
import ua.rd.cm.domain.User;
import ua.rd.cm.services.UserService;
import ua.rd.cm.web.controller.dto.MessageDto;
import ua.rd.cm.web.controller.dto.RegistrationDto;
import ua.rd.cm.web.controller.dto.UserDto;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Set;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private ModelMapper mapper;
    private UserService userService;

    @Autowired
    public UserController(ModelMapper mapper, UserService userService) {
        this.mapper = mapper;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity register(@Valid @RequestBody RegistrationDto dto, BindingResult bindingResult){
        HttpStatus status;
        MessageDto message = new MessageDto();
        if (bindingResult.hasFieldErrors() || !isPasswordConfirmed(dto)){
            status = HttpStatus.BAD_REQUEST;
            message.setError("empty_fields");
        } else if (userService.isEmailExist(dto.getEmail())){
            status = HttpStatus.CONFLICT;
            message.setError("email_already_exists");
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
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(userToDto(currentUser), HttpStatus.ACCEPTED);
        }
    }

    private User dtoToEntity(RegistrationDto dto) {
        return mapper.map(dto, User.class);
    }

    private UserDto userToDto(User user){
        UserDto dto = mapper.map(user, UserDto.class);
        dto.setLinkedin(getContactLink(user, "linkedin"));
        dto.setBlog(getContactLink(user, "blog"));
        dto.setFacebook(getContactLink(user, "facebook"));
        dto.setTwitter(getContactLink(user, "twitter"));
        dto.setRoles(convertRolesTypeToFirstLetters(user.getUserRoles()));
        return  dto;
    }

    private String getContactLink(User user, String contactName) {
        ContactType contactType = new ContactType();
        contactType.setName(contactName);
        return user.getUserInfo().getContacts().get(contactType);
    }

    private String[] convertRolesTypeToFirstLetters(Set<Role> roles){
        String[] rolesFirstLetters = new String[roles.size()];
        Role[] rolesFullNames = roles.toArray(new Role[roles.size()]);
        for(int i = 0; i < roles.size(); i++){
            rolesFirstLetters[i] = rolesFullNames[i].getName().substring(0, 1).toLowerCase();
        }
        return rolesFirstLetters;
    }

    private boolean isPasswordConfirmed(RegistrationDto dto) {
        return dto.getPassword().equals(dto.getConfirm());
    }
}
