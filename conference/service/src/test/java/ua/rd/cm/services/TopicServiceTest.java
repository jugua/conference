package ua.rd.cm.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import ua.rd.cm.domain.Topic;
import ua.rd.cm.dto.CreateTopicDto;
import ua.rd.cm.dto.TopicDto;
import ua.rd.cm.repository.TopicRepository;
import ua.rd.cm.services.businesslogic.TopicService;
import ua.rd.cm.services.businesslogic.impl.TopicServiceImpl;
import ua.rd.cm.services.exception.TopicNotFoundException;


@RunWith(MockitoJUnitRunner.class)
public class TopicServiceTest {
    @Mock
    private ModelMapper modelMapper;

    @Mock
    private TopicRepository topicRepository;

    private TopicService topicService;

    private Topic topic;

    private CreateTopicDto createTopicDto;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        topicService = new TopicServiceImpl(modelMapper, topicRepository);

        topic = new Topic("name");
        topic.setId(1L);

        TopicDto topicDto = new TopicDto();
        topicDto.setId(1L);
        topicDto.setName("name");

        List<TopicDto> topics = new ArrayList<>();
        topics.add(topicDto);

        createTopicDto = new CreateTopicDto("name");
    }

    @After
    public void after() {
        Mockito.reset(modelMapper, topicRepository);
    }

    @Test
    public void testFindByCorrectId() {
        long correctId = 1;
        when(topicRepository.findOne(correctId)).thenReturn(topic);
        assertEquals(topicService.find(correctId), topic);
    }

    @Test(expected = TopicNotFoundException.class)
    public void testFindByWrongId() {
        long wrongId = 100500;
        when(topicRepository.findOne(wrongId)).thenReturn(null);
        topicService.find(wrongId);
    }

    @Test
    public void testSaveTopicIfExists() {
        Long id = 1L;
        when(topicRepository.findTopicByName(createTopicDto.getName())).thenReturn(topic);
        assertEquals(id, topicService.save(createTopicDto));
        verify(modelMapper, never()).map(any(Topic.class), any());
        verify(topicRepository, never()).save(any(Topic.class));
    }

    @Test
    public void testSaveTopicIfNotExists() {
        String newName = "new";
        Long expectedId = topic.getId();
        createTopicDto.setName(newName);
        when(modelMapper.map(createTopicDto, Topic.class)).thenReturn(topic);

        assertEquals(expectedId, topicService.save(createTopicDto));

        verify(modelMapper).map(any(Topic.class), any());
        verify(topicRepository).save(any(Topic.class));
    }

    @Test
    public void testGetTopicByCorrectName() {
        String correctName = "name";
        when(topicRepository.findTopicByName(correctName)).thenReturn(topic);
        assertEquals(topicService.getByName(correctName), topic);
    }

    @Test(expected = TopicNotFoundException.class)
    public void testGetTopicByWrongName() {
        String wrongName = "wrongName";
        when(topicRepository.findTopicByName(wrongName)).thenReturn(null);
        topicService.getByName(wrongName);
    }
}