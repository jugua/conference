package web.security;

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

    public ResponseEntity<MessageDto> process(String token, VerificationToken.TokenType tokenType, Consumer<VerificationToken> action) {
        VerificationToken verificationToken = tokenService.getToken(token);

        if (!tokenService.isTokenValid(verificationToken, tokenType)) {
            return ResponseEntity.badRequest().body(prepareMessageDto("invalid_link"));
        }
        if (tokenService.isTokenExpired(verificationToken)) {
            return ResponseEntity.status(HttpStatus.GONE).body(prepareMessageDto("expired_link"));
        }
        action.accept(verificationToken);
        setTokenStatusExpired(verificationToken);
        authenticateUser(verificationToken.getUser());
        return ResponseEntity.ok().build();
    }

    private void setTokenStatusExpired(VerificationToken verificationToken) {
        verificationToken.setStatus(VerificationToken.TokenStatus.EXPIRED);
        tokenService.updateToken(verificationToken);
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