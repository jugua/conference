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
import ua.rd.cm.services.UserService;
import ua.rd.cm.services.VerificationTokenService;
import ua.rd.cm.web.controller.dto.MessageDto;

import java.security.Principal;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class SignInController {

    private VerificationTokenService tokenService;
    private UserService userService;

    @Autowired
    public SignInController(VerificationTokenService tokenService, UserService userService) {
        this.tokenService = tokenService;
        this.userService = userService;
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
