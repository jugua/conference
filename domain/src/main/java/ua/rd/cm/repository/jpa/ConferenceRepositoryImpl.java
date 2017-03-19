package ua.rd.cm.repository.jpa;

import org.springframework.stereotype.Repository;
import ua.rd.cm.domain.Conference;
import ua.rd.cm.repository.ConferenceRepository;
import ua.rd.cm.repository.specification.Specification;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class ConferenceRepositoryImpl
        extends AbstractJpaCrudRepository<Conference> implements ConferenceRepository {

    public ConferenceRepositoryImpl() {
        super("c", Conference.class);
    }

    @Override
    public List<Conference> getAllWithTalks(Specification<Conference> spec) {
        TypedQuery<Conference> query = entityManager.createQuery(
                "select c from Conference c left join fetch c.talks t where " + spec.toSqlClauses(), Conference.class
        );
        spec.setParameters(query);
        return query.getResultList();
    }
}
