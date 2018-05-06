package service.businesslogic.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.model.VerificationToken;
import domain.repository.VerificationTokenRepository;

@Service
public class VerificationTokenService {

    private VerificationTokenRepository tokenRepository;

    @Autowired
    public VerificationTokenService(VerificationTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Transactional
    public void saveToken(VerificationToken token) {
        tokenRepository.save(token);
    }

    @Transactional
    public void setPreviousTokensExpired(VerificationToken newToken) {
        List<VerificationToken> tokens = tokenRepository.findByUserIdAndStatusAndType(
                newToken.getUser().getId(), VerificationToken.TokenStatus.VALID, newToken.getType());

        for (VerificationToken token : tokens) {
            token.setStatus(VerificationToken.TokenStatus.EXPIRED);
            updateToken(token);
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
        return verificationToken.secondsToExpiry() <= 0;
    }

    public VerificationToken findTokenBy(String token) {
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

    @Transactional
    public void updateToken(VerificationToken token) {
        tokenRepository.save(token);
    }

    private VerificationToken loadFromDatabase(Long userId, VerificationToken.TokenType tokenType) {
        List<VerificationToken> tokens = tokenRepository.
                findByUserIdAndStatusAndType(userId,
                        VerificationToken.TokenStatus.VALID,
                        tokenType);
        return tokens.isEmpty() ? null : tokens.get(0);
    }

}