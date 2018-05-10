package domain.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import domain.model.VerificationToken;

@Repository
public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Long> {

    VerificationToken findFirstByToken(String token);

    List<VerificationToken> findByUserIdAndStatusAndType(Long userId,
                                                         VerificationToken.TokenStatus status,
                                                         VerificationToken.TokenType type);

    VerificationToken findFirstByUserIdAndStatusAndType(Long userId,
                                                        VerificationToken.TokenStatus valid,
                                                        VerificationToken.TokenType tokenType);
}