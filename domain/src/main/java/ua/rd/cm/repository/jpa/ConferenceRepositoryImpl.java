package ua.rd.cm.repository.jpa;

import org.springframework.stereotype.Repository;
import ua.rd.cm.domain.Conference;
import ua.rd.cm.repository.ConferenceRepository;
import ua.rd.cm.repository.specification.Specification;
import ua.rd.cm.repository.specification.conference.ConferenceById;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class ConferenceRepositoryImpl implements ConferenceRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Conference findById(Long id) {
        return findBySpecification(new ConferenceById(id)).stream().findFirst().orElse(null);
    }

    @Override
    public void save(Conference conference) {
        em.persist(conference);
    }

    @Override
    public void update(Conference conference) {
        em.merge(conference);
    }

    @Override
    public void remove(Conference conference) {
        em.remove(conference);
    }

    @Override
    public List<Conference> findAll() {
        return em.createQuery("SELECT c FROM Conference c", Conference.class).
                getResultList();
    }

    @Override
    public List<Conference> findBySpecification(Specification<Conference> spec) {
        TypedQuery<Conference> query =
                em.createQuery("SELECT c FROM Conference c WHERE " + spec.toSqlClauses(), Conference.class);
        spec.setParameters(query);

        return query.getResultList();
    }
}
