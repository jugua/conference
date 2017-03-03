package ua.rd.cm.repository.jpa;

import org.springframework.stereotype.Repository;
import ua.rd.cm.domain.Type;
import ua.rd.cm.repository.TypeRepository;
import ua.rd.cm.repository.specification.Specification;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Mariia Lapovska
 */
@Repository
public class JpaTypeRepository implements TypeRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void saveType(Type type) {
        em.persist(type);
    }

    @Override
    public List<Type> findAll() {
        return em.createQuery("SELECT t FROM Type t", Type.class)
                .getResultList();
    }

    @Override
    public List<Type> findBySpecification(Specification<Type> spec) {
        return em.createQuery("SELECT t FROM Type t WHERE " + spec
                .toSqlClauses(), Type.class).getResultList();
    }
}