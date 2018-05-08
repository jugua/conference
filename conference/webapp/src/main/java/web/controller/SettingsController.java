package web.controller;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import domain.model.User;
import domain.model.VerificationToken;
import service.businesslogic.api.UserService;
import service.businesslogic.dto.MessageDto;
import service.businesslogic.dto.SettingsDto;
import service.businesslogic.impl.VerificationTokenService;
import service.infrastructure.mail.MailService;
import service.infrastructure.mail.preparator.ChangePasswordPreparator;
import service.infrastructure.mail.preparator.NewEmailMessagePreparator;

@Log4j
@RestController
@RequestMapping("/settings")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SettingsController {

    private ObjectMapper mapper;
    private UserService userService;
    private MailService mailService;
    private PasswordEncoder passwordEncoder;
    private VerificationTokenService tokenService;

    @PostMapping("/password")
    public ResponseEntity<MessageDto> changePassword(@Valid @RequestBody SettingsDto dto, Principal principal,
                                                     BindingResult bindingResult, HttpServletRequest request) {
        if (principal == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        User user = userService.getByEmail(principal.getName());

        if (!userService.isAuthenticated(user, dto.getCurrentPassword())) {
            log.error("Changing password failed: current password doesn't match user's password. [HttpServletRequest:" +
                    " " + request.toString() + "]");
            MessageDto messageDto = new MessageDto("wrong_password");
            messageDto.setFields(Arrays.asList("currentPassword"));
            return badRequest().body(messageDto);
        }
        if (!checkPasswordConfirmed(dto)) {
            log.error("Changing password failed: confirmed password doesn't match new password. [HttpServletRequest: " +
                    "" + request.toString() + "]");
            MessageDto messageDto = new MessageDto();
            messageDto.setResult("password_math");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(messageDto);
        }
        if (bindingResult.hasFieldErrors()) {
            log.error("Request for [settings/password] is failed: validation is failed. [HttpServletRequest: " +
                    request.toString() + "]");
            return badRequest().body(new MessageDto("fields_error"));
        }
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userService.updateUser(user);
        mailService.sendEmail(user, new ChangePasswordPreparator());
        return ok().build();
    }

    @PostMapping("/email")
    public ResponseEntity<MessageDto> changeEmail(@RequestBody String mail, Principal principal) {
        if (principal == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        User user = userService.getByEmail(principal.getName());

        String email = parseMail(mail);
        if (email == null || !email.matches("(?i)^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$")) {
            return badRequest().build();
        }
        if (userService.getByEmail(email) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageDto("email_already_exists"));
        }
        VerificationToken token = VerificationToken.createChangeEmailToken(
                user, VerificationToken.TokenType.CHANGING_EMAIL, email);

        tokenService.setPreviousTokensExpired(token);
        tokenService.saveToken(token);
        mailService.sendEmail(user, new NewEmailMessagePreparator(token, mailService.getUrl()));
        return ok().build();
    }

    @GetMapping("/email")
    public ResponseEntity<MessageDto> getEmailVerificationState(Principal principal) {
        if (principal == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        User user = userService.getByEmail(principal.getName());

        VerificationToken token = tokenService.getValidTokenByUserIdAndType(
                user.getId(), VerificationToken.TokenType.CHANGING_EMAIL);
        MessageDto messageDto = new MessageDto();

        if (token == null) {
            messageDto.setResult("no_pending_email_changes");
            return ok(messageDto);
        }

        messageDto.setResult("pending_email_change_found");
        messageDto.setSecondsToExpiry(String.valueOf(token.secondsToExpiry()));
        return ok(messageDto);
    }

    private String parseMail(String mail) {
        try {
            JsonNode node = mapper.readValue(mail, ObjectNode.class).get("mail");

            if (node == null) {
                return null;
            }
            return node.textValue();
        } catch (IOException e) {
            return null;
        }
    }

    private boolean checkPasswordConfirmed(SettingsDto dto) {
        return dto.getNewPassword().equals(dto.getConfirmedNewPassword());
    }
}