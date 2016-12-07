package ua.rd.cm.repository;

import ua.rd.cm.domain.Topic;
import ua.rd.cm.repository.specification.Specification;

import java.util.List;

/**
 * @author Mariia Lapovska
 */
public interface TopicRepository {

    void saveTopic(Topic topic);

    List<Topic> findAll();

    List<Topic> findBySpecification(Specification<Topic> spec);
}
