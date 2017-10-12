package ua.rd.cm.web.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ua.rd.cm.domain.User;
import ua.rd.cm.domain.VerificationToken;
import ua.rd.cm.infrastructure.mail.MailService;
import ua.rd.cm.services.businesslogic.UserService;
import ua.rd.cm.services.businesslogic.impl.VerificationTokenService;
import ua.rd.cm.infrastructure.mail.preparator.OldEmailMessagePreparator;

@RestController
@AllArgsConstructor(onConstructor = @__({@Autowired}))
@RequestMapping("/confirmation")
public class ConfirmationController {

    private final WithTokenGetRequestProcessor withTokenGetRequestProcessor;
    private final VerificationTokenService tokenService;
    private final UserService userService;
    private final MailService mailService;

    @GetMapping("/registrationConfirm/{token}")
    public ResponseEntity confirmRegistration(@PathVariable String token) {
        return withTokenGetRequestProcessor.process(token, VerificationToken.TokenType.CONFIRMATION, verificationToken -> {
            User user = verificationToken.getUser();
            setUserStatusConfirmed(user);
        });
    }

    @GetMapping("/newEmailConfirm/{token:.+}")
    public ResponseEntity confirmNewEmail(@PathVariable String token) {
        return withTokenGetRequestProcessor.process(token, VerificationToken.TokenType.CHANGING_EMAIL, verificationToken -> {
            User user = verificationToken.getUser();
            String newEmail = tokenService.getEmail(token);
            String oldEmail = user.getEmail();
            user.setEmail(newEmail);
            userService.updateUserProfile(user);
            mailService.sendEmail(user, new OldEmailMessagePreparator(oldEmail));
        });
    }

    private void setUserStatusConfirmed(User user) {
        user.setStatus(User.UserStatus.CONFIRMED);
        userService.updateUserProfile(user);
    }
}