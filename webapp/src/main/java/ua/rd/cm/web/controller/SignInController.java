package ua.rd.cm.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ua.rd.cm.domain.Role;
import ua.rd.cm.domain.User;
import ua.rd.cm.domain.VerificationToken;
import ua.rd.cm.services.MailService;
import ua.rd.cm.services.UserService;
import ua.rd.cm.services.VerificationTokenService;
import ua.rd.cm.services.preparator.OldEmailMessagePreparator;
import ua.rd.cm.web.controller.dto.MessageDto;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class SignInController {

    private VerificationTokenService tokenService;
    private UserService userService;
    private MailService mailService;

    @Autowired
    public SignInController(VerificationTokenService tokenService,
                            UserService userService, MailService mailService) {
        this.tokenService = tokenService;
        this.userService = userService;
        this.mailService = mailService;
    }

    @PostMapping("/login")
    public void signIn() {
    }

    @GetMapping("/registrationConfirm/{token}")
    public ResponseEntity confirmRegistration(@PathVariable String token) {
        VerificationToken verificationToken = tokenService.getToken(token);

        if (!tokenService.isTokenValid(verificationToken, VerificationToken.TokenType.CONFIRMATION)) {
            return ResponseEntity.badRequest().body(prepareMessageDto("invalid_link"));
        } else if (tokenService.isTokenExpired(verificationToken)) {
            return ResponseEntity.status(HttpStatus.GONE).body(prepareMessageDto("expired_link"));
        } else {
            User user = verificationToken.getUser();

            setUserStatusConfirmed(user);
            setTokenStatusExpired(verificationToken);
            authenticateUser(verificationToken.getUser());

            return ResponseEntity.ok().build();
        }
    }

    @GetMapping("/newEmailConfirm/{token}")
    public ResponseEntity confirmNewEmail(@PathVariable String token) {
        VerificationToken verificationToken = tokenService.getToken(token);

        if (!tokenService.isTokenValid(verificationToken, VerificationToken.TokenType.CHANGING_EMAIL)) {
            return ResponseEntity.badRequest().body(prepareMessageDto("invalid_link"));
        }
        if (tokenService.isTokenExpired(verificationToken)) {
            return ResponseEntity.status(HttpStatus.GONE).body(prepareMessageDto("expired_link"));
        }

        User user = verificationToken.getUser();
        String newEmail = tokenService.getEmail(token);
        String oldEmail = user.getEmail();

        user.setEmail(newEmail);
        userService.updateUserProfile(user);

        setTokenStatusExpired(verificationToken);

        Map<String, Object> messageValues = setupMessageValues(user.getFirstName(),
                newEmail, oldEmail, LocalDateTime.now().toString());
        mailService.sendEmail(new OldEmailMessagePreparator(), messageValues);

        authenticateUser(verificationToken.getUser());

        return ResponseEntity.ok().build();
    }

    private Map<String, Object> setupMessageValues(String name, String oldEmail, String newEmail, String dateTime) {
        Map<String, Object> values = new HashMap<>();
        values.put("name" , name);
        values.put("oldEmail", oldEmail);
        values.put("newEmail", newEmail);
        values.put("dateTime", dateTime);
        return values;
    }

    private void setTokenStatusExpired(VerificationToken verificationToken) {
        verificationToken.setStatus(VerificationToken.TokenStatus.EXPIRED);
        tokenService.updateToken(verificationToken);
    }

    private void setUserStatusConfirmed(User user) {
        user.setStatus(User.UserStatus.CONFIRMED);
        userService.updateUserProfile(user);
    }

    private MessageDto prepareMessageDto(String message) {
        MessageDto messageDto = new MessageDto();
        messageDto.setError(message);
        return  messageDto;
    }

    private void authenticateUser(User user) {
        Principal principal = user::getEmail;
        String credentials = user.getPassword();
        Set<Role> roleSet = user.getUserRoles();

        Authentication auth = new UsernamePasswordAuthenticationToken(principal, credentials, roleSet);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
