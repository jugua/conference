package ua.rd.cm.repository.jpa;

import org.springframework.stereotype.Repository;
import ua.rd.cm.domain.Talk;
import ua.rd.cm.repository.TalkRepository;

@Repository
public class TalkRepositoryImpl
        extends AbstractJpaCrudRepository<Talk> implements TalkRepository {

    public TalkRepositoryImpl() {
        super("t", Talk.class);
    }
}
