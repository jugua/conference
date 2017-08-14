package ua.rd.cm.repository;

import org.springframework.data.repository.CrudRepository;
import ua.rd.cm.domain.Topic;

public interface TopicRepository extends CrudRepository<Topic, Long> {
    Topic findTopicByName(String name);
}