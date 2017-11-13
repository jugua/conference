package service.businesslogic.api;

import java.util.List;

import domain.model.Topic;
import service.businesslogic.dto.CreateTopicDto;
import service.businesslogic.dto.TopicDto;

/**
 * @author Mariia Lapovska
 */
public interface TopicService {

    Topic find(Long id);

    Long save(CreateTopicDto topic);

    List<TopicDto> findAll();

    Topic getByName(String name);
}
