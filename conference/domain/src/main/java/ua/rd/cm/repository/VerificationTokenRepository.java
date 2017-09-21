package ua.rd.cm.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import ua.rd.cm.domain.VerificationToken;

@RepositoryRestResource(exported = false)
@Repository
public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Long> {

    List<VerificationToken> findByToken(String token);

    List<VerificationToken> findByUserIdAndStatusAndType(Long userId,
                                                         VerificationToken.TokenStatus status,
                                                         VerificationToken.TokenType type);
}