package ua.rd.cm.repository.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import ua.rd.cm.domain.Topic;
import ua.rd.cm.repository.CrudRepository;
import ua.rd.cm.repository.TopicRepository;

public class TopicRepositoryImplIT extends AbstractJpaCrudRepositoryIT<Topic> {
    @Autowired
    private TopicRepository repository;

    @Override
    protected CrudRepository<Topic> createRepository() {
        return repository;
    }

    @Override
    protected Topic createEntity() {
        Topic topic = new Topic();
        topic.setName("SOme");
        return topic;
    }
}