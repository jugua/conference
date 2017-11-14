package domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.model.Topic;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    Topic findTopicByName(String name);
}