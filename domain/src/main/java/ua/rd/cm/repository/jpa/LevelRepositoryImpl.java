package ua.rd.cm.repository.jpa;

import org.springframework.stereotype.Repository;
import ua.rd.cm.domain.Level;
import ua.rd.cm.repository.LevelRepository;
import ua.rd.cm.repository.specification.Specification;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Olha_Melnyk
 */
@Repository
public class LevelRepositoryImpl implements LevelRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void saveLevel(Level level) {
        em.persist(level);
    }

    @Override
    public List<Level> findAll() {
        return em.createQuery("SELECT lev FROM Level lev", Level.class)
                .getResultList();
    }

    @Override
    public List<Level> findBySpecification(Specification<Level> spec) {
        return em.createQuery("SELECT lev FROM Level lev WHERE " + spec
                .toSqlClauses(), Level.class).getResultList();
    }
}
