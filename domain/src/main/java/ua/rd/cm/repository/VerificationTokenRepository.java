package ua.rd.cm.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ua.rd.cm.domain.VerificationToken;

@Repository
public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Long> {

    List<VerificationToken> findByToken(String token);

    List<VerificationToken> findByUserIdAndStatusAndType(Long userId,
                                                         VerificationToken.TokenStatus status,
                                                         VerificationToken.TokenType type);
}