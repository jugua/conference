package ua.rd.cm.repository;

import ua.rd.cm.domain.VerificationToken;
import ua.rd.cm.repository.specification.Specification;

import java.util.List;

public interface VerificationTokenRepository {

    void saveToken(VerificationToken token);

    void updateToken(VerificationToken token);

    void removeToken(VerificationToken token);

    List<VerificationToken> findBySpecification(Specification specification);
}
