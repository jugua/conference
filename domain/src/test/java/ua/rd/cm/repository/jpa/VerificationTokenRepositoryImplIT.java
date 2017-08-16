package ua.rd.cm.repository.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import ua.rd.cm.domain.User;
import ua.rd.cm.domain.VerificationToken;
import ua.rd.cm.repository.CrudRepository;
import ua.rd.cm.repository.VerificationTokenRepository;

import java.time.LocalDateTime;

public class VerificationTokenRepositoryImplIT
        extends AbstractJpaCrudRepositoryIT<VerificationToken> {
    @Autowired
    private VerificationTokenRepository repository;

    @Override
    protected CrudRepository<VerificationToken> createRepository() {
        return (CrudRepository)repository;
    }

    @Override
    protected VerificationToken createEntity() {
        VerificationToken token = new VerificationToken();
        token.setExpiryDate(LocalDateTime.MAX);
        token.setToken("token");
        User user = new User();
        user.setId(-1L);
        token.setUser(user);
        token.setStatus(VerificationToken.TokenStatus.VALID);
        token.setType(VerificationToken.TokenType.CONFIRMATION);
        return token;
    }
}