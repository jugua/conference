package web.controller;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.AllArgsConstructor;

import domain.model.User;
import domain.model.VerificationToken;
import service.businesslogic.api.UserService;
import service.businesslogic.dto.ConfirmPasswordPair;
import service.businesslogic.dto.MessageDto;
import service.businesslogic.impl.VerificationTokenService;
import service.infrastructure.mail.MailService;
import service.infrastructure.mail.preparator.ForgotMessagePreparator;
import web.security.WithTokenGetRequestProcessor;

@RestController
@AllArgsConstructor(onConstructor = @__({@Autowired}))
@RequestMapping("/forgotPasswordPage")
public class ForgotPasswordController {

    private final WithTokenGetRequestProcessor withTokenGetRequestProcessor;
    private final VerificationTokenService tokenService;
    private final UserService userService;
    private final MailService mailService;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/forgotPassword")
    public ResponseEntity<MessageDto> forgotPassword(@RequestBody String mail) throws IOException {
        ObjectNode node = objectMapper.readValue(mail, ObjectNode.class);

        if (node.get("mail") != null) {
            if (!userService.isEmailExist(node.get("mail").textValue())) {
                return badRequest().body(new MessageDto("email_not_found"));
            } else {
                User currentUser = userService.getByEmail(node.get("mail").textValue());
                VerificationToken token = VerificationToken.of(currentUser, VerificationToken.TokenType.FORGOT_PASS);
                tokenService.setPreviousTokensExpired(token);
                tokenService.saveToken(token);
                mailService.sendEmail(currentUser, new ForgotMessagePreparator(token, mailService.getUrl()));
                MessageDto responseMessage = new MessageDto();
                responseMessage.setResult("success");
                return ok().body(responseMessage);
            }
        } else {
            return badRequest().body(new MessageDto("email_is_empty"));
        }

    }

    @GetMapping("/changePassword/{token}")
    public ResponseEntity<MessageDto> changePassword(@PathVariable String token) {
        return withTokenGetRequestProcessor.process(
                token, VerificationToken.TokenType.FORGOT_PASS, verificationToken -> {
                    //NOP
                });
    }

    @PostMapping("/changePassword/{token}")
    public ResponseEntity changePassword(@PathVariable String token,
                                         @Valid @RequestBody ConfirmPasswordPair passwordPair) {

        // TODO: we definitely should check passwords are same on the frontend
        if (passwordPair.isNotEqual()) {
            return badRequest().build();
        }

        User currentUser = getCurrentUser(token);
        currentUser.setPassword(passwordEncoder.encode(passwordPair.getPassword()));
        userService.updateUser(currentUser);

        return ok().build();
    }

    private User getCurrentUser(@PathVariable String token) {
        VerificationToken verificationToken = tokenService.findTokenBy(token);
        return verificationToken.getUser();
    }

}