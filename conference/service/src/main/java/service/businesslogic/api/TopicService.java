package service.businesslogic.api;

import java.util.List;

import domain.model.Topic;
import service.businesslogic.dto.CreateTopicDto;
import service.businesslogic.dto.TopicDto;

/**
 * @author Mariia Lapovska
 */
public interface TopicService {

    List<TopicDto> getAll();

    Topic getById(Long id);

    Topic getByName(String name);

    Long save(CreateTopicDto topic);
}
