package ua.rd.cm.repository.jpa;

import org.springframework.stereotype.Repository;
import ua.rd.cm.domain.VerificationToken;
import ua.rd.cm.repository.VerificationTokenRepository;

@Repository
public class VerificationTokenRepositoryImpl
        extends AbstractJpaCrudRepository<VerificationToken>
        implements VerificationTokenRepository {

    public VerificationTokenRepositoryImpl() {
        super("v", VerificationToken.class);
    }
}
