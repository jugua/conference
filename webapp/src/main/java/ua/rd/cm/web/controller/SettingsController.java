package ua.rd.cm.web.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.rd.cm.domain.User;
import ua.rd.cm.domain.VerificationToken;
import ua.rd.cm.services.MailService;
import ua.rd.cm.services.UserService;
import ua.rd.cm.services.VerificationTokenService;
import ua.rd.cm.services.preparator.ChangePasswordPreparator;
import ua.rd.cm.services.preparator.NewEmailMessagePreparator;
import ua.rd.cm.web.controller.dto.MessageDto;
import ua.rd.cm.web.controller.dto.SettingsDto;

import javax.servlet.http.HttpServletRequest;
import javax.validation.*;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Olha_Melnyk
 */
@RestController
@RequestMapping("/api/user/current")
public class SettingsController {
    private ObjectMapper mapper;
    private UserService userService;
    private MailService mailService;
    private VerificationTokenService tokenService;
    private Logger logger = Logger.getLogger(SettingsController.class);

    @Autowired
    public SettingsController(ObjectMapper mapper, UserService userService,
                              MailService mailService,
                              VerificationTokenService tokenService) {
        this.mapper = mapper;
        this.userService = userService;
        this.mailService = mailService;
        this.tokenService = tokenService;
    }

    @PostMapping("/password")
    public ResponseEntity changePassword(@Valid @RequestBody SettingsDto dto, Principal principal, BindingResult bindingResult, HttpServletRequest request) {
        MessageDto messageDto = new MessageDto();
        if (principal == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        User user = userService.getByEmail(principal.getName());

        if (user == null) {
            logger.error("Request for [api/user/current/password] is failed: User entity for current principal is not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (!checkCurrentPasswordMatches(dto, user)) {
            logger.error("Changing password failed: current password doesn't match user's password. [HttpServletRequest: " + request.toString() + "]");
            messageDto.setError("wrong_password");
            messageDto.setFields(new ArrayList<String>() {{
                add("currentPassword");
            }});
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageDto);
        }
        if (!checkPasswordConfirmed(dto)) {
            logger.error("Changing password failed: confirmed password doesn't match new password. [HttpServletRequest: " + request.toString() + "]");
            messageDto.setAnswer("password_math");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(messageDto);
        }
        if (bindingResult.hasFieldErrors()) {
            System.out.println("Field error: " + bindingResult.getFieldError());
            System.out.println("Field error default message: " + bindingResult.getFieldError().getDefaultMessage());
            logger.error("Request for [api/user/current/password] is failed: validation is failed. [HttpServletRequest: " + request.toString() + "]");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("fields_error");
        }
        user.setPassword(dto.getNewPassword());
        userService.updateUserProfile(user);

        Map<String, Object> model = new HashMap<>();
        model.put("name", userService.getByEmail(principal.getName()).getFirstName());
        model.put("email", principal.getName());
        mailService.sendEmail(new ChangePasswordPreparator(), model);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/email")
    public ResponseEntity changeEmail(@RequestBody String mail, Principal principal) {
        if (principal == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        User user = userService.getByEmail(principal.getName());
        if (user == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        String email = parseMail(mail);
        if (email == null || !email.matches("(?i)^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$")) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        if (userService.getByEmail(email) != null) {
            MessageDto messageDto = new MessageDto();
            messageDto.setError("email_already_exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(messageDto);
        }

        VerificationToken token = tokenService.createNewEmailToken(user,
                VerificationToken.TokenType.CONFIRMATION, email);
        tokenService.saveToken(token);
        tokenService.setPreviousTokensExpired(token);

        Map<String, Object> messageValues = setupMessageValues(user.getFirstName(), email,
                "http://localhost:8025/#/newEmailConfirm/" + token.getToken());
        mailService.sendEmail(new NewEmailMessagePreparator(), messageValues);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/email")
    public ResponseEntity getEmailVerificationState(Principal principal) {
        if (principal == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        User user = userService.getByEmail(principal.getName());
        if (user == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        VerificationToken token = tokenService.getValidTokenByUserIdAndType
                (user.getId(), VerificationToken.TokenType.CHANGING_EMAIL);
        MessageDto messageDto = new MessageDto();

        if (token == null) {
            messageDto.setAnswer("no_pending_email_changes");
            return ResponseEntity.ok(messageDto);
        }

        messageDto.setAnswer("pending_email_change_found");
        messageDto.setSecondsToExpiry(String.valueOf(
                ChronoUnit.SECONDS.between(
                        LocalDateTime.now(), token.getExpiryDate())));
        return ResponseEntity.ok(messageDto);
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

    private Map<String, Object> setupMessageValues(String name, String email,
                                                   String link) {
        Map<String, Object> values = new HashMap<>();
        values.put("name" , name);
        values.put("email", email);
        values.put("link", link);
        return values;
    }

    private boolean checkCurrentPasswordMatches(SettingsDto dto, User user) {
        return dto.getCurrentPassword().equals(user.getPassword());
    }

    private boolean checkPasswordConfirmed(SettingsDto dto) {
        return dto.getNewPassword().equals(dto.getConfirmedNewPassword());
    }
}
