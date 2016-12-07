package ua.rd.cm.repository;

import org.springframework.stereotype.Repository;
import ua.rd.cm.domain.Topic;
import ua.rd.cm.repository.specification.Specification;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Mariia Lapovska
 */
@Repository
public class JpaTopicRepository implements TopicRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void saveTopic(Topic topic) {
        em.persist(topic);
    }

    @Override
    public List<Topic> findAll() {
        return em.createQuery("SELECT t FROM Topic t", Topic.class)
                .getResultList();
    }

    @Override
    public List<Topic> findBySpecification(Specification<Topic> spec) {
        return em.createQuery("SELECT t FROM Topic t WHERE " + spec
                .toSqlClauses(), Topic.class).getResultList();
    }
}