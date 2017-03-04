package ua.rd.cm.repository.jpa;

import org.springframework.stereotype.Repository;
import ua.rd.cm.domain.Topic;
import ua.rd.cm.repository.TopicRepository;

@Repository
public class TopicRepositoryImpl
        extends AbstractJpaCrudRepository<Topic> implements TopicRepository {

    public TopicRepositoryImpl() {
        super("t", Topic.class);
    }
}