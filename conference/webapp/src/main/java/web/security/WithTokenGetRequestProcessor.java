package web.security;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

import domain.model.User;
import domain.model.VerificationToken;
import service.businesslogic.dto.MessageDto;
import service.businesslogic.impl.VerificationTokenService;

@AllArgsConstructor(onConstructor = @__({@Autowired}))
@Component
public class WithTokenGetRequestProcessor {

    private final VerificationTokenService tokenService;

    public ResponseEntity<MessageDto> process(String tokenString,
                                              VerificationToken.TokenType tokenType,
                                              Consumer<VerificationToken> action) {

        VerificationToken token = tokenService.findTokenBy(tokenString);
        if (token == null) {
            return badRequest().body(prepareMessageDto("invalid_link"));
        } else if (token.isExpired()) {
            return ResponseEntity.status(HttpStatus.GONE).body(prepareMessageDto("expired_link"));
        } else if (token.isInvalid(tokenType)) {
            return badRequest().body(prepareMessageDto("invalid_link"));
        }

        action.accept(token);
        expireToken(token);
        authenticateUser(token.getUser());
        return ok().build();
    }

    private void expireToken(VerificationToken token) {
        token.expire();
        tokenService.saveToken(token);
    }

    private MessageDto prepareMessageDto(String message) {
        MessageDto messageDto = new MessageDto();
        messageDto.setError(message);
        return messageDto;
    }

    private void authenticateUser(User user) {
        SecurityContextHolder.getContext().setAuthentication(
                AuthenticationFactory.createAuthentication(user)
        );
    }
}