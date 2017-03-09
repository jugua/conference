package ua.rd.cm.services;

import ua.rd.cm.domain.Topic;
import ua.rd.cm.dto.CreateTopicDto;

import java.util.List;

/**
 * @author Mariia Lapovska
 */
public interface TopicService {

    Topic find(Long id);

    void save(CreateTopicDto topic);

    List<Topic> findAll();

    Topic getByName(String name);
}
