package ua.rd.cm.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.rd.cm.domain.Topic;
import ua.rd.cm.repository.TopicRepository;
import ua.rd.cm.repository.specification.topic.TopicById;
import ua.rd.cm.repository.specification.topic.TopicByName;
import ua.rd.cm.services.TopicService;

import java.util.List;

/**
 * @author Mariia Lapovska
 */
@Service
public class TopicServiceImpl implements TopicService {

    private TopicRepository topicRepository;

    @Autowired
    public TopicServiceImpl(TopicRepository topicRepository) {
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
    public Topic getByName(String name) {
        List<Topic> list = topicRepository.findBySpecification(new TopicByName(name));
        if (list.isEmpty()) return null;
        else return list.get(0);
    }
}
