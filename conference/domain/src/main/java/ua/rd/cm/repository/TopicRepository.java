package ua.rd.cm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.rd.cm.domain.Topic;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    Topic findTopicByName(String name);
}