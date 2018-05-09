package service.businesslogic.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.model.User;
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
    public void expirePreviousTokens(VerificationToken newToken) {
        List<VerificationToken> tokens = tokenRepository.findByUserIdAndStatusAndType(
                newToken.getUser().getId(), VerificationToken.TokenStatus.VALID, newToken.getType());

        if (!tokens.isEmpty()) {
            tokens.forEach(VerificationToken::expire);
            tokenRepository.save(tokens);
        }
    }

    @Transactional
    public VerificationToken generateNewToken(User user, VerificationToken.TokenType tokenType) {
        VerificationToken newToken = VerificationToken.of(user, tokenType);
        expirePreviousTokens(newToken);
        saveToken(newToken);
        return newToken;
    }

    @Transactional
    public VerificationToken getValidTokenByUserIdAndType(Long userId, VerificationToken.TokenType tokenType) {
        VerificationToken token = findTokenBy(userId, tokenType);
        if (token != null && token.isExpiredByTime()) {
            token.expire();
            tokenRepository.save(token);
            return null;
        }
        return token;
    }

    public VerificationToken findTokenBy(String token) {
        return tokenRepository.findFirstByToken(token);
    }

    public void expire(VerificationToken token) {
        token.expire();
        saveToken(token);
    }

    private VerificationToken findTokenBy(Long userId, VerificationToken.TokenType tokenType) {
        List<VerificationToken> tokens = tokenRepository.
                findByUserIdAndStatusAndType(userId, VerificationToken.TokenStatus.VALID, tokenType);
        return tokens.isEmpty() ? null : tokens.get(0);
    }

}