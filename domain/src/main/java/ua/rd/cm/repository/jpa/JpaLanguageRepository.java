package ua.rd.cm.repository.jpa;

import org.springframework.stereotype.Repository;
import ua.rd.cm.domain.Language;
import ua.rd.cm.repository.LanguageRepository;
import ua.rd.cm.repository.specification.Specification;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Olha_Melnyk
 */
@Repository
public class JpaLanguageRepository implements LanguageRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void saveLanguage(Language language) {
        em.persist(language);
    }

    @Override
    public List<Language> findAll() {
        return em.createQuery("SELECT lan FROM Language lan", Language.class)
                .getResultList();
    }

    @Override
    public List<Language> findBySpecification(Specification<Language> spec) {
        return em.createQuery("SELECT lan FROM Language lan WHERE " + spec
                .toSqlClauses(), Language.class).getResultList();
    }

}