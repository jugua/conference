package ua.rd.cm.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import ua.rd.cm.domain.VerificationToken;

import java.util.List;

@Repository
public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Long> {
    List<VerificationToken> findByToken(String token);
    List<VerificationToken> findByUserIdAndStatusAndType(Long userId,
                                                         VerificationToken.TokenStatus status,
                                                         VerificationToken.TokenType type);
}