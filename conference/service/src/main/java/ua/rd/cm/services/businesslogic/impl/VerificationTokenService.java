package ua.rd.cm.services.businesslogic.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.rd.cm.domain.User;
import ua.rd.cm.domain.VerificationToken;
import ua.rd.cm.repository.VerificationTokenRepository;

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
        tokenRepository.save(token);
    }

    @Transactional
    public void setPreviousTokensExpired(VerificationToken newToken) {
        List<VerificationToken> tokens = tokenRepository.
                findByUserIdAndStatusAndType(newToken.getUser().getId(),
                        VerificationToken.TokenStatus.VALID,
                        newToken.getType());

        if (!tokens.isEmpty()) {
            for (VerificationToken token : tokens) {
                token.setStatus(VerificationToken.TokenStatus.EXPIRED);
                updateToken(token);
            }
        }
    }

    public boolean isTokenValid(VerificationToken verificationToken, VerificationToken.TokenType currentType) {
        return (verificationToken != null && verificationToken.getType().equals(currentType)
                && verificationToken.getStatus().equals(VerificationToken.TokenStatus.VALID));
    }

    public boolean isTokenExpired(VerificationToken verificationToken) {
        return (verificationToken.getStatus().equals(VerificationToken.TokenStatus.EXPIRED)) ||
                isExpiredByTime(verificationToken);
    }

    public boolean isExpiredByTime(VerificationToken verificationToken) {
        return verificationToken.calculateSecondsToExpiry() <= 0;
    }

    public VerificationToken getToken(String token) {
        List<VerificationToken> tokens = tokenRepository.findByToken(token);
        return tokens.isEmpty() ? null : tokens.get(0);
    }

    @Transactional
    public VerificationToken getValidTokenByUserIdAndType(Long userId, VerificationToken.TokenType tokenType) {
        VerificationToken token = loadFromDatabase(userId, tokenType);
        if (token != null && isExpiredByTime(token)) {
            token.setStatus(VerificationToken.TokenStatus.EXPIRED);
            updateToken(token);
            return null;
        }
        return token;
    }

    public String getEmail(String token) {
        int index = token.indexOf('|');
        return (index == -1) ? null : token.substring(index + 1);
    }

    @Transactional
    public void updateToken(VerificationToken token) {
        tokenRepository.save(token);
    }

    private LocalDateTime calculateExpiryDate(int expiryTimeInMinutes) {
        LocalDateTime currentTime = LocalDateTime.now();
        return currentTime.plusMinutes(expiryTimeInMinutes);
    }

    private VerificationToken loadFromDatabase(Long userId, VerificationToken.TokenType tokenType) {
        List<VerificationToken> tokens = tokenRepository.
                findByUserIdAndStatusAndType(userId,
                        VerificationToken.TokenStatus.VALID,
                        tokenType);
        return tokens.isEmpty() ? null : tokens.get(0);
    }
}