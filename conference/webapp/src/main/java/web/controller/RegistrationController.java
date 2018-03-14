package web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

import domain.model.Role;
import domain.model.User;
import service.businesslogic.api.UserService;
import service.businesslogic.dto.InviteDto;
import service.businesslogic.dto.MessageDto;
import service.businesslogic.dto.RegistrationDto;
import service.businesslogic.exception.EmailAlreadyExistsException;
import service.businesslogic.exception.PasswordMismatchException;

@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
@RestController
@RequestMapping("/registration")
@Log4j
public class RegistrationController {

    private static final String VALIDATION_IS_FAILED = "Request for [registration] is failed: validation is failed. " +
            "[HttpServletRequest:";

    @NonNull
    private UserService userService;

    @PostMapping
    public ResponseEntity<MessageDto> register(@Valid @RequestBody RegistrationDto dto,
                                               BindingResult bindingResult,
                                               HttpServletRequest request) {
        dto.setUserStatus(User.UserStatus.UNCONFIRMED);
        dto.setRoleName(Role.ROLE_SPEAKER);
        return processUserRegistration(dto, bindingResult, request);
    }

    public void registration() {

    }

    public void confirmationProcess() {

    }
    
    @PostMapping("/invitation")
    public ResponseEntity<MessageDto> sendInvite(@RequestBody InviteDto invite) {
        userService.inviteUser(invite);
        return new ResponseEntity<MessageDto>(HttpStatus.OK);
    }


    private ResponseEntity<MessageDto> processUserRegistration(RegistrationDto dto, BindingResult bindingResult,
                                                               HttpServletRequest request) {
        HttpStatus status;
        MessageDto message = new MessageDto();

        try {

            if (bindingResult.hasFieldErrors()) {
                status = HttpStatus.BAD_REQUEST;
                message.setError("empty_fields");
                log.error(VALIDATION_IS_FAILED + " " + request.toString() + "]");
            } else {
                userService.checkUserRegistration(dto);
                userService.registerNewUser(dto);
                status = HttpStatus.ACCEPTED;
                message.setResult("success");
            }
        } catch (PasswordMismatchException ex) {
            status = HttpStatus.BAD_REQUEST;
            message.setError(ex.getMessage());
            log.error(VALIDATION_IS_FAILED + " " + request.toString() + "]");
        } catch (EmailAlreadyExistsException ex) {
            status = HttpStatus.CONFLICT;
            message.setError(ex.getMessage());
            log.error("Registration failed: " + dto.toString() +
                    ". Email '" + dto.getEmail() + "' is already in use. [HttpServletRequest: "
                    + request.toString() + "]");
        }

        return ResponseEntity.status(status).body(message);
    }


}
