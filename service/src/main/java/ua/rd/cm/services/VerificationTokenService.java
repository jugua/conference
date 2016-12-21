package ua.rd.cm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.rd.cm.domain.User;
import ua.rd.cm.domain.VerificationToken;
import ua.rd.cm.repository.VerificationTokenRepository;
import ua.rd.cm.repository.specification.AndSpecification;
import ua.rd.cm.repository.specification.verificationtoken.VerificationTokenByStatus;
import ua.rd.cm.repository.specification.verificationtoken.VerificationTokenByToken;
import ua.rd.cm.repository.specification.verificationtoken.VerificationTokenByType;
import ua.rd.cm.repository.specification.verificationtoken.VerificationTokenByUserId;
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

    public VerificationToken createToken(User user, VerificationToken.TokenType tokenType) {
        VerificationToken token = new VerificationToken();
        token.setUser(user);
        token.setExpiryDate(calculateExpiryDate(VerificationToken.EXPIRATION_IN_MINUTES));
        token.setToken(UUID.randomUUID().toString());
        token.setType(tokenType);
        token.setStatus(VerificationToken.TokenStatus.VALID);
        return token;
    }

    public VerificationToken createNewEmailToken(User user, VerificationToken
            .TokenType tokenType, String newEmail) {
        VerificationToken token = createToken(user, tokenType);
        token.setToken(token.getToken() + "|" + newEmail);
        return token;
    }

    @Transactional
    public void saveToken(VerificationToken token) {
        tokenRepository.saveToken(token);
    }

    @Transactional
    public void setPreviousTokensExpired(VerificationToken token) {
        List<VerificationToken> tokens = tokenRepository.findBySpecification
                (new AndSpecification<>(new AndSpecification<>(new
                        VerificationTokenByUserId(token.getUser().getId())
                        , new VerificationTokenByStatus(VerificationToken.TokenStatus.VALID))
                        , new VerificationTokenByType(token.getType())));

        if (!tokens.isEmpty()) {
            for (VerificationToken t : tokens) {
                t.setStatus(VerificationToken.TokenStatus.EXPIRED);
                tokenRepository.updateToken(t);
            }
        }
    }

    public boolean isTokenValid(VerificationToken verificationToken, VerificationToken.TokenType currentType) {
        return (verificationToken != null && verificationToken.getType().equals(currentType) 
        		&& verificationToken.getStatus().equals(VerificationToken.TokenStatus.VALID));
    }

    public boolean isTokenExpired(VerificationToken verificationToken) {
        return (verificationToken.getStatus().equals(VerificationToken.TokenStatus.EXPIRED)) ||
                (verificationToken.getExpiryDate().isBefore(LocalDateTime.now(ZoneId.systemDefault())));
    }

    public VerificationToken getToken(String token) {
        List<VerificationToken> tokens = tokenRepository.findBySpecification(new VerificationTokenByToken(token));
        if (tokens.isEmpty()) {
            return null;
        }
        return tokens.get(0);
    }

    public VerificationToken getValidTokenByUserIdAndType(Long userId,
                                                          VerificationToken.TokenType tokenType) {
        List<VerificationToken> tokens = tokenRepository.findBySpecification
                (new AndSpecification<>(new AndSpecification<>(new VerificationTokenByUserId(userId)
                        , new VerificationTokenByStatus(VerificationToken.TokenStatus.VALID))
                        , new VerificationTokenByType(tokenType)));

        if (tokens.isEmpty()) {
            return null;
        }
        return tokens.get(0);
    }

    public String getEmail(String token) {
        int index = token.indexOf('|');

        if (index == -1) {
            return null;
        }

        return token.substring(index + 1);
    }

    @Transactional
    public void updateToken(VerificationToken token) {
        tokenRepository.updateToken(token);
    }

    private LocalDateTime calculateExpiryDate(int expiryTimeInMinutes) {
        LocalDateTime currentTime = LocalDateTime.now(ZoneId.systemDefault());
        return currentTime.plusMinutes(expiryTimeInMinutes);
    }
}