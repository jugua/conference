package ua.rd.cm.repository.jpa;

import org.springframework.stereotype.Repository;
import ua.rd.cm.domain.Conference;
import ua.rd.cm.repository.ConferenceRepository;

@Repository
public class ConferenceRepositoryImpl
        extends AbstractJpaCrudRepository<Conference> implements ConferenceRepository {

    public ConferenceRepositoryImpl() {
        super("c", Conference.class);
    }
}
