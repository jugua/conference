package ua.rd.cm.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.rd.cm.domain.User;
import ua.rd.cm.domain.VerificationToken;
import ua.rd.cm.dto.NewPasswordDto;
import ua.rd.cm.services.MailService;
import ua.rd.cm.services.UserService;
import ua.rd.cm.services.VerificationTokenService;
import ua.rd.cm.services.preparator.ForgotMessagePreparator;
import ua.rd.cm.services.preparator.OldEmailMessagePreparator;
import ua.rd.cm.dto.MessageDto;
import ua.rd.cm.web.security.AuthenticationFactory;
import ua.rd.cm.web.security.CustomAuthenticationProvider;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class SignInController {

    private final VerificationTokenService tokenService;
    private final UserService userService;
    private final MailService mailService;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;
	
    @Autowired
    public SignInController(VerificationTokenService tokenService,
                            UserService userService, MailService mailService,
                            ObjectMapper objectMapper, PasswordEncoder passwordEncoder) {
        this.tokenService = tokenService;
        this.userService = userService;
        this.mailService = mailService;
        this.objectMapper = objectMapper;
        this.passwordEncoder = passwordEncoder;
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

    @GetMapping("/newEmailConfirm/{token:.+}")
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
        mailService.sendEmail(user, new OldEmailMessagePreparator(oldEmail));
        authenticateUser(verificationToken.getUser());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/forgot-password")
    public ResponseEntity forgotPassword(@RequestBody String mail, HttpServletRequest request) throws IOException {
        HttpStatus httpStatus;
        MessageDto responseMessage = new MessageDto();
        ObjectNode node = objectMapper.readValue(mail, ObjectNode.class);

        if(node.get("mail") != null) {
            if(!userService.isEmailExist(node.get("mail").textValue())) {
                httpStatus = HttpStatus.BAD_REQUEST;
                responseMessage.setError("email_not_found");
            } else {
                User currentUser = userService.getByEmail(node.get("mail").textValue());
                VerificationToken token = tokenService.createToken(currentUser, VerificationToken.TokenType.FORGOT_PASS);
                tokenService.setPreviousTokensExpired(token);
                tokenService.saveToken(token);
                mailService.sendEmail(currentUser, new ForgotMessagePreparator(token, mailService.getUrl()));
                httpStatus = HttpStatus.OK;
                responseMessage.setResult("success");
            }
        } else {
            httpStatus = HttpStatus.BAD_REQUEST;
            responseMessage.setError("email_is_empty");
        }

        return ResponseEntity.status(httpStatus).body(responseMessage);
    }

    @GetMapping("/forgotPassword/{token}")
    public ResponseEntity changePassword(@PathVariable String token) {
        VerificationToken verificationToken = tokenService.getToken(token);

        if (!tokenService.isTokenValid(verificationToken, VerificationToken.TokenType.FORGOT_PASS)) {
            return ResponseEntity.badRequest().body(prepareMessageDto("invalid_link"));
        } else if (tokenService.isTokenExpired(verificationToken)) {
            return ResponseEntity.status(HttpStatus.GONE).body(prepareMessageDto("expired_link"));
        } else {
            User user = verificationToken.getUser();

            setTokenStatusExpired(verificationToken);
            authenticateUser(verificationToken.getUser());

            return ResponseEntity.ok().build();
        }
    }

    @PostMapping("/forgotPassword/{token}")
    public ResponseEntity changePassword(@PathVariable String token, @Valid @RequestBody NewPasswordDto dto, BindingResult bindingResult) {
        VerificationToken verificationToken = tokenService.getToken(token);
        if(!isPasswordConfirmed(dto))
            return ResponseEntity.badRequest().build();

        User currentUser = verificationToken.getUser();
        currentUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        userService.updateUserProfile(currentUser);

        return ResponseEntity.ok().build();
    }


    private void setTokenStatusExpired(VerificationToken verificationToken) {
        verificationToken.setStatus(VerificationToken.TokenStatus.EXPIRED);
        tokenService.updateToken(verificationToken);
    }

    private MessageDto prepareMessageDto(String message) {
        MessageDto messageDto = new MessageDto();
        messageDto.setError(message);
        return  messageDto;
    }

    private void authenticateUser(User user) {
        SecurityContextHolder.getContext().setAuthentication(
                AuthenticationFactory.createAuthentication(user)
        );
    }


    private boolean isPasswordConfirmed(NewPasswordDto dto) {
        return dto.getPassword().equals(dto.getConfirm());
    }

    private void setUserStatusConfirmed(User user) {
        user.setStatus(User.UserStatus.CONFIRMED);
        userService.updateUserProfile(user);
    }
}