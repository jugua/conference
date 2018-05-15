package web.controller;

import static java.util.Optional.ofNullable;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

import java.util.List;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import domain.model.Contact;
import domain.model.User;
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
public class UsersController {

    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/contacts")
    public ResponseEntity<List<Contact>> getUserContacts(@PathVariable("id") long id) {
        List<Contact> contacts = userService.getById(id).getUserInfo().getContacts();
        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{id}/contacts")
    public ResponseEntity<MessageDto> updateUserContacts(@PathVariable("id") long id,
                                                         @RequestBody List<Contact> contacts, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return badRequest().body(new MessageDto("empty_fields"));
        }
        userService.updateContacts(id, contacts);
        MessageDto messageDto = new MessageDto();
        messageDto.setResult("success");
        return ok(messageDto);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/usersNames")
    public ResponseEntity<List<String>> getUsersNames() {
        return ok(userService.getUserNames());
    }

    @PreAuthorize("hasRole(\"ADMIN\")")
    @PostMapping("/registerByAdmin")
    public ResponseEntity registerByAdmin(@Valid @RequestBody RegistrationDto dto,
                                          BindingResult bindingResult,
                                          HttpServletRequest request) {
        if (bindingResult.hasFieldErrors()) {
            log.error("Request for [user/registerByAdmin] is failed: validation is failed. [HttpServletRequest: " + request.toString() + "]");
            return badRequest().body(new MessageDto("empty_fields"));
        }
        try {
            registerSpeaker(dto);
            MessageDto messageDto = new MessageDto();
            messageDto.setResult("success");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(messageDto);
        } catch (WrongRoleException ex) {
            return new ResponseEntity<>(new MessageDto(ex.getMessage()), HttpStatus.FORBIDDEN);
        } catch (PasswordMismatchException ex) {
            log.error("Request for [user/registerByAdmin] is failed: validation is failed. [HttpServletRequest: " + request.toString() + "]");
            return badRequest().body(new MessageDto(ex.getMessage()));
        } catch (EmailAlreadyExistsException ex) {
            log.error("Registration failed: " + dto.toString() +
                    ". Email '" + dto.getEmail() + "' is already in use. [HttpServletRequest: " + request.toString() + "]");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageDto(ex.getMessage()));
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity getUserById(@PathVariable("id") Long userId, HttpServletRequest request) {
        if (!request.isUserInRole("ORGANISER")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageDto("unauthorized"));
        }

        UserInfoDto userInfoDto = ofNullable(userService.getUserInfoDtoById(userId))
                .orElseThrow(() -> new NoSuchUserException("No User with such id."));

        return ok(userInfoDto);
    }

    private void registerSpeaker(RegistrationDto dto) {
        userService.checkUserRegistrationByAdmin(dto);
        dto.setUserStatus(User.UserStatus.CONFIRMED);
        userService.registerSpeaker(dto);
    }

}