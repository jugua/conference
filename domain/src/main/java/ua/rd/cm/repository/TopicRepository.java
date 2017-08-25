package ua.rd.cm.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ua.rd.cm.domain.Topic;

@Repository
public interface TopicRepository extends CrudRepository<Topic, Long> {
    Topic findTopicByName(String name);
}