package ua.rd.cm.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ua.rd.cm.domain.User;
import ua.rd.cm.domain.VerificationToken;
import ua.rd.cm.services.MailService;
import ua.rd.cm.services.UserService;
import ua.rd.cm.services.VerificationTokenService;
import ua.rd.cm.services.preparator.OldEmailMessagePreparator;

@RestController
@RequestMapping("/api")
public class ConfirmationController {

    private final WithTokenGetRequestProcessor withTokenGetRequestProcessor;
    private final VerificationTokenService tokenService;
    private final UserService userService;
    private final MailService mailService;

    @Autowired
    public ConfirmationController(WithTokenGetRequestProcessor withTokenGetRequestProcessor,
                                  VerificationTokenService tokenService,
                                  UserService userService, MailService mailService) {
        this.withTokenGetRequestProcessor = withTokenGetRequestProcessor;
        this.tokenService = tokenService;
        this.userService = userService;
        this.mailService = mailService;
    }

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