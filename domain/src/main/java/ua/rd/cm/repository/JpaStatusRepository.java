package ua.rd.cm.repository;

import ua.rd.cm.domain.Status;
import ua.rd.cm.repository.specification.Specification;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Olha_Melnyk
 */
public class JpaStatusRepository implements StatusRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void saveStatus(Status status) {
        em.persist(status);
    }

    @Override
    public List<Status> findAll() {
        return em.createQuery("SELECT s FROM Status s", Status.class)
                .getResultList();
    }

    @Override
    public List<Status> findBySpecification(Specification<Status> spec) {
        return em.createQuery("SELECT s FROM Status s WHERE " + spec
                .toSqlClauses(), Status.class).getResultList();
    }
}
