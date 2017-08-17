package ua.rd.cm.web.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.rd.cm.domain.Role;
import ua.rd.cm.domain.User;
import ua.rd.cm.dto.RegistrationDto;
import ua.rd.cm.services.UserInfoService;
import ua.rd.cm.services.UserService;
import ua.rd.cm.dto.MessageDto;
import ua.rd.cm.dto.UserBasicDto;
import ua.rd.cm.dto.UserDto;
import ua.rd.cm.services.exception.EmailAlreadyExistsException;
import ua.rd.cm.services.exception.PasswordMismatchException;
import ua.rd.cm.services.exception.NoSuchUserException;
import ua.rd.cm.services.exception.WrongRoleException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.*;

@AllArgsConstructor(onConstructor = @__({@Autowired}))
@RestController
@RequestMapping("/api/user")
@Log4j
public class UserController {
    private final UserService userService;
    private final UserInfoService userInfoService;

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

    @GetMapping("/current")
    public ResponseEntity getCurrentUser(Principal principal) {
        if (principal == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try{
            UserDto userDto = userService.getUserDtoByEmail(principal.getName());
            return new ResponseEntity<>(userDto, HttpStatus.ACCEPTED);
        } catch (NoSuchUserException ex) {
            log.error("Request for [api/user/current] is failed: User entity for current principal is not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/current")
    public ResponseEntity updateUserInfo(@Valid @RequestBody UserDto dto,
                                         Principal principal, BindingResult bindingResult) {
        HttpStatus status;
        if (bindingResult.hasFieldErrors()) {
            status = HttpStatus.BAD_REQUEST;
        } else if (principal == null) {
            status = HttpStatus.UNAUTHORIZED;
        } else {
            String userEmail = principal.getName();
            userInfoService.update(userService.prepareNewUserInfoForUpdate(userEmail, dto));
            userService.updateUserProfile(userService.prepareNewUserForUpdate(userEmail, dto));
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

        UserDto userDto = userService.getUserDtoById(userId);
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
        List<UserBasicDto> userDtoList = userService.getUserBasicDtoByRoleExpectCurrent(
                currentUser, Role.ORGANISER, Role.SPEAKER);
        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
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


    private ResponseEntity processUserRegistration(RegistrationDto dto, BindingResult bindingResult, HttpServletRequest request) {
        HttpStatus status;
        MessageDto message = new MessageDto();

        try{

            if (bindingResult.hasFieldErrors()) {
                status = HttpStatus.BAD_REQUEST;
                message.setError("empty_fields");
                log.error("Request for [api/user] is failed: validation is failed. [HttpServletRequest: " + request.toString() + "]");
            } else {
                userService.checkUserRegistration(dto);
                userService.registerNewUser(dto);
                status = HttpStatus.ACCEPTED;
                message.setResult("success");
            }
        } catch (PasswordMismatchException ex) {
            status = HttpStatus.BAD_REQUEST;
            message.setError(ex.getMessage());
            log.error("Request for [api/user] is failed: validation is failed. [HttpServletRequest: " + request.toString() + "]");
        } catch (EmailAlreadyExistsException ex) {
            status = HttpStatus.CONFLICT;
            message.setError(ex.getMessage());
            log.error("Registration failed: " + dto.toString() +
                    ". Email '" + dto.getEmail() + "' is already in use. [HttpServletRequest: " + request.toString() + "]");
        }

        return ResponseEntity.status(status).body(message);
    }
}