package ua.rd.cm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.rd.cm.domain.User;
import ua.rd.cm.domain.VerificationToken;
import ua.rd.cm.repository.VerificationTokenRepository;
import ua.rd.cm.repository.specification.verificationtoken.VerificationTokenByToken;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
public class VerificationTokenService {

    private VerificationTokenRepository tokenRepository;

    @Autowired
    public VerificationTokenService(VerificationTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Transactional
    public VerificationToken createToken(User user, VerificationToken.TokenType tokenType) {
        VerificationToken token = new VerificationToken();
        token.setUser(user);
        token.setExpiryDate(calculateExpiryDate(VerificationToken.EXPIRATION_IN_MINUTES));
        token.setToken(UUID.randomUUID().toString());
        token.setType(tokenType);
        return token;
    }

    @Transactional
    public void saveToken(VerificationToken token) {
        tokenRepository.saveToken(token);
    }

    public boolean isTokenValid(VerificationToken verificationToken, VerificationToken.TokenType currentType) {
        return (verificationToken != null && verificationToken.getType().equals(currentType));
    }

    public boolean isTokenExpired(VerificationToken verificationToken) {
        return (ChronoUnit.MINUTES.between(LocalDateTime.now(ZoneId.systemDefault()),
                verificationToken.getExpiryDate()) >= VerificationToken.EXPIRATION_IN_MINUTES);
    }

    public VerificationToken getToken(String token) {
        List<VerificationToken> tokens = tokenRepository.findBySpecification(new VerificationTokenByToken(token));
        if (tokens.isEmpty()) {
            return null;
        }
        return tokens.get(0);
    }

    private LocalDateTime calculateExpiryDate(int expiryTimeInHours) {
        LocalDateTime currentTime = LocalDateTime.now(ZoneId.systemDefault());
        return currentTime.plusMinutes(VerificationToken.EXPIRATION_IN_MINUTES);
    }
}
