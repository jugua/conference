package ua.rd.cm.services.businesslogic;

import java.util.List;

import ua.rd.cm.domain.Topic;
import ua.rd.cm.dto.CreateTopicDto;
import ua.rd.cm.dto.TopicDto;

/**
 * @author Mariia Lapovska
 */
public interface TopicService {

    Topic find(Long id);

    Long save(CreateTopicDto topic);

    List<TopicDto> findAll();

    Topic getByName(String name);
}
