package web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
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
import service.businesslogic.dto.MessageDto;
import service.businesslogic.dto.NewPasswordDto;
import service.businesslogic.impl.VerificationTokenService;
import service.infrastructure.mail.MailService;
import service.infrastructure.mail.preparator.ForgotMessagePreparator;

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
    public ResponseEntity<MessageDto> forgotPassword(@RequestBody String mail, HttpServletRequest request) throws IOException {
        HttpStatus httpStatus;
        MessageDto responseMessage = new MessageDto();
        ObjectNode node = objectMapper.readValue(mail, ObjectNode.class);

        if (node.get("mail") != null) {
            if (!userService.isEmailExist(node.get("mail").textValue())) {
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

    @GetMapping("/changePassword/{token}")
    public ResponseEntity<MessageDto> changePassword(@PathVariable String token) {
        return withTokenGetRequestProcessor.process(
                token, VerificationToken.TokenType.FORGOT_PASS, verificationToken -> {
                    //NOP
                });
    }

    @PostMapping("/changePassword/{token}")
    public ResponseEntity changePassword(@PathVariable String token, @Valid @RequestBody NewPasswordDto dto,
                                         BindingResult bindingResult) {
        VerificationToken verificationToken = tokenService.getToken(token);
        if (!isPasswordConfirmed(dto))
            return ResponseEntity.badRequest().build();

        User currentUser = verificationToken.getUser();
        currentUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        userService.updateUserProfile(currentUser);

        return ResponseEntity.ok().build();
    }

    private boolean isPasswordConfirmed(NewPasswordDto dto) {
        return dto.getPassword().equals(dto.getConfirm());
    }

}