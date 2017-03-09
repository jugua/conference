package ua.rd.cm.services;

import ua.rd.cm.domain.Topic;
import ua.rd.cm.dto.CreateTopicDto;
import ua.rd.cm.dto.TopicDto;

import java.util.List;

/**
 * @author Mariia Lapovska
 */
public interface TopicService {

    Topic find(Long id);

    Long save(CreateTopicDto topic);

    List<TopicDto> findAll();

    Topic getByName(String name);
}
