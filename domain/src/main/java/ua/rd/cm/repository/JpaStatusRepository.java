package ua.rd.cm.repository;

import org.springframework.stereotype.Repository;
import ua.rd.cm.domain.TalkStatus;
import ua.rd.cm.repository.specification.Specification;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Olha_Melnyk
 */
@Deprecated
@Repository
public class JpaStatusRepository implements StatusRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void saveStatus(TalkStatus status) {
        em.persist(status);
    }

    @Override
    public List<TalkStatus> findAll() {
        return em.createQuery("SELECT s FROM TalkStatus s", TalkStatus.class)
                .getResultList();
    }

    @Override
    public List<TalkStatus> findBySpecification(Specification<TalkStatus> spec) {
        return em.createQuery("SELECT s FROM TalkStatus s WHERE " + spec
                .toSqlClauses(), TalkStatus.class).getResultList();
    }
}
