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
            token.expire();
            tokenRepository.save(token);
        }
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
        List<VerificationToken> tokens = tokenRepository.findByToken(token);
        return tokens.isEmpty() ? null : tokens.get(0);
    }

    private VerificationToken findTokenBy(Long userId, VerificationToken.TokenType tokenType) {
        List<VerificationToken> tokens = tokenRepository.
                findByUserIdAndStatusAndType(userId,
                        VerificationToken.TokenStatus.VALID,
                        tokenType);
        return tokens.isEmpty() ? null : tokens.get(0);
    }

}