package ua.rd.cm.repository.jpa;

import org.springframework.stereotype.Repository;
import ua.rd.cm.domain.VerificationToken;
import ua.rd.cm.repository.VerificationTokenRepository;
import ua.rd.cm.repository.specification.Specification;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class VerificationTokenRepositoryImpl implements VerificationTokenRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void saveToken(VerificationToken token) {
        em.persist(token);
    }

    @Override
    public void updateToken(VerificationToken token) {
        em.merge(token);
    }

    @Override
    public void removeToken(VerificationToken token) {
        em.remove(token);
    }

    @Override
    public List<VerificationToken> findBySpecification(Specification specification) {
        return em.createQuery("SELECT v FROM VerificationToken v WHERE " + specification.toSqlClauses(), VerificationToken.class).getResultList();
    }
}
