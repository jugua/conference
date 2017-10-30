package ua.rd.cm.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.AllArgsConstructor;
import ua.rd.cm.domain.User;
import ua.rd.cm.domain.VerificationToken;
import ua.rd.cm.dto.MessageDto;
import ua.rd.cm.infrastructure.mail.MailService;
import ua.rd.cm.infrastructure.mail.preparator.ForgotMessagePreparator;
import ua.rd.cm.services.businesslogic.UserService;
import ua.rd.cm.services.businesslogic.impl.VerificationTokenService;

@RestController
@AllArgsConstructor(onConstructor = @__({@Autowired}))
@RequestMapping("/forgotPasswordPage")
public class ForgotPasswordController {

    private VerificationTokenService tokenService;
    private UserService userService;
    private MailService mailService;
    private ObjectMapper objectMapper;

    @PostMapping("/forgotPassword")
    public ResponseEntity forgotPassword(@RequestBody String mail, HttpServletRequest request) throws IOException {
        HttpStatus httpStatus;
        MessageDto responseMessage = new MessageDto();
        ObjectNode node = objectMapper.readValue(mail, ObjectNode.class);

        if (node.get("mail") != null) {
            if (!userService.isEmailExist(node.get("mail").textValue())) {
                httpStatus = HttpStatus.BAD_REQUEST;
                responseMessage.setError("email_not_found");
            } else {
                User currentUser = userService.getByEmail(node.get("mail").textValue());
                VerificationToken token = tokenService.createToken(currentUser, VerificationToken.TokenType
                        .FORGOT_PASS);
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
}