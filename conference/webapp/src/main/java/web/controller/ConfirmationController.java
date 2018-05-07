package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

import domain.model.User;
import domain.model.VerificationToken;
import service.businesslogic.api.UserService;
import service.businesslogic.dto.MessageDto;
import service.infrastructure.mail.MailService;
import service.infrastructure.mail.preparator.OldEmailMessagePreparator;
import web.security.WithTokenGetRequestProcessor;

@RestController
@AllArgsConstructor(onConstructor = @__({@Autowired}))
@RequestMapping("/confirmation")
public class ConfirmationController {

    private final WithTokenGetRequestProcessor withTokenGetRequestProcessor;
    private final UserService userService;
    private final MailService mailService;

    @GetMapping("/registrationConfirm/{token}")
    public ResponseEntity<MessageDto> confirmRegistration(@PathVariable String token) {
        return withTokenGetRequestProcessor.process(token, VerificationToken.TokenType.CONFIRMATION, verificationToken -> {
            userService.confirm(verificationToken.getUser());
        });
    }

    @GetMapping("/newEmailConfirm/{token:.+}")
    public ResponseEntity<MessageDto> confirmNewEmail(@PathVariable String token) {
        return withTokenGetRequestProcessor.process(token, VerificationToken.TokenType.CHANGING_EMAIL, verificationToken -> {
            User user = verificationToken.getUser();
            String newEmail = extractEmailOf(token);
            String oldEmail = user.getEmail();
            user.setEmail(newEmail);
            userService.updateUser(user);
            mailService.sendEmail(user, new OldEmailMessagePreparator(oldEmail));
        });
    }

    private static String extractEmailOf(String token) {
        int index = token.indexOf('|');
        return (index == -1) ? null : token.substring(index + 1);
    }
}