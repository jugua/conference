package web.controller;

import static java.util.Optional.ofNullable;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import domain.model.Contact;
import domain.model.User;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import service.businesslogic.api.UserService;
import service.businesslogic.dto.MessageDto;
import service.businesslogic.dto.RegistrationDto;
import service.businesslogic.dto.UserInfoDto;
import service.businesslogic.exception.EmailAlreadyExistsException;
import service.businesslogic.exception.NoSuchUserException;
import service.businesslogic.exception.PasswordMismatchException;
import service.businesslogic.exception.WrongRoleException;

@AllArgsConstructor(onConstructor = @__({@Autowired}))
@RestController
@RequestMapping("/user")
@Log4j
public class UserController {

    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/contacts")
    public ResponseEntity<List<Contact>> getUserContacts(@PathVariable("id")long id){
    	List<Contact> contacts = userService.find(id).getUserInfo().getContacts();
    	return new ResponseEntity<>(contacts,HttpStatus.OK);
    }
    
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/usersNames")
    public ResponseEntity<List<String>> getUsersNames() {
        List<String> usersNames = userService.findAll().stream().map(m -> m.getFirstName()).collect(Collectors.toList());
        return new ResponseEntity<>(usersNames, HttpStatus.OK);
    }

    @PreAuthorize("hasRole(\"ADMIN\")")
    @PostMapping("/registerByAdmin")
    public ResponseEntity registerByAdmin(@Valid @RequestBody RegistrationDto dto,
                                          BindingResult bindingResult,
                                          HttpServletRequest request
    ) {
        try {
            userService.checkUserRegistrationByAdmin(dto);
            dto.setUserStatus(User.UserStatus.CONFIRMED);
            return processUserRegistration(dto, bindingResult, request);
        } catch (WrongRoleException ex) {
            MessageDto message = new MessageDto();
            message.setError(ex.getMessage());
            return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity getUserById(@PathVariable("id") Long userId, HttpServletRequest request) {
        MessageDto message = new MessageDto();
        if (!request.isUserInRole("ORGANISER")) {
            message.setError("unauthorized");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
        }

        UserInfoDto userInfoDto = ofNullable(userService.getUserDtoById(userId))
                .orElseThrow(() -> new NoSuchUserException("No User with such id."));

        return new ResponseEntity<>(userInfoDto, HttpStatus.OK);
    }

    private ResponseEntity<MessageDto> processUserRegistration(RegistrationDto dto, BindingResult bindingResult, HttpServletRequest request) {
        HttpStatus status;
        MessageDto message = new MessageDto();

        try {

            if (bindingResult.hasFieldErrors()) {
                status = HttpStatus.BAD_REQUEST;
                message.setError("empty_fields");
                log.error("Request for [user/registerByAdmin] is failed: validation is failed. [HttpServletRequest: " + request.toString() + "]");
            } else {
                userService.checkUserRegistration(dto);
                userService.registerNewUser(dto);
                status = HttpStatus.ACCEPTED;
                message.setResult("success");
            }
        } catch (PasswordMismatchException ex) {
            status = HttpStatus.BAD_REQUEST;
            message.setError(ex.getMessage());
            log.error("Request for [user/registerByAdmin] is failed: validation is failed. [HttpServletRequest: " + request.toString() + "]");
        } catch (EmailAlreadyExistsException ex) {
            status = HttpStatus.CONFLICT;
            message.setError(ex.getMessage());
            log.error("Registration failed: " + dto.toString() +
                    ". Email '" + dto.getEmail() + "' is already in use. [HttpServletRequest: " + request.toString() + "]");
        }

        return ResponseEntity.status(status).body(message);
    }
}