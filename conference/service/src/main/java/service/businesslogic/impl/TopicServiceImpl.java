package service.businesslogic.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.model.Topic;
import domain.repository.TopicRepository;
import lombok.AllArgsConstructor;
import service.businesslogic.api.TopicService;
import service.businesslogic.dto.CreateTopicDto;
import service.businesslogic.dto.TopicDto;
import service.businesslogic.exception.TopicNotFoundException;

@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class TopicServiceImpl implements TopicService {

    private final ModelMapper modelMapper;
    private final TopicRepository topicRepository;

    @Override
    public Topic getById(Long id) {
        Topic topic = topicRepository.findOne(id);
        if (topic == null) {
            throw new TopicNotFoundException();
        }
        return topic;
    }

    @Override
    @Transactional
    public Long save(CreateTopicDto topic) {
        Topic foundedTopic = topicRepository.findTopicByName(topic.getName());
        if (foundedTopic != null) {
            return foundedTopic.getId();
        }
        Topic map = modelMapper.map(topic, Topic.class);
        topicRepository.save(map);
        return map.getId();
    }

    @Override
    public List<TopicDto> getAll() {
        return StreamSupport.stream(topicRepository.findAll().spliterator(), false)/*topicRepository.findAll().stream()*/
                .map(e -> modelMapper.map(e, TopicDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Topic getByName(String name) {
        Topic topic = topicRepository.findTopicByName(name);
        if (topic == null) {
            throw new TopicNotFoundException();
        }
        return topic;
    }
}
