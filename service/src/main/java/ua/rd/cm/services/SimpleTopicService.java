package ua.rd.cm.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.rd.cm.domain.Topic;
import ua.rd.cm.repository.TopicRepository;
import ua.rd.cm.repository.specification.topic.TopicById;
import ua.rd.cm.repository.specification.topic.TopicByName;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Mariia Lapovska
 */
@Service
public class SimpleTopicService implements TopicService {

    private TopicRepository topicRepository;

    @Inject
    public SimpleTopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Override
    public Topic find(Long id) {
        return topicRepository.findBySpecification(new TopicById(id)).get(0);
    }

    @Override
    @Transactional
    public void save(Topic topic) {
        topicRepository.saveTopic(topic);
    }

    @Override
    public List<Topic> findAll() {
        return topicRepository.findAll();
    }

    @Override
    public List<Topic> getByName(String name) {
        return topicRepository.findBySpecification(new TopicByName(name));
    }
}
