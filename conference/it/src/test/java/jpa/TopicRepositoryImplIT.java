package jpa;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import ua.rd.cm.domain.Topic;
import ua.rd.cm.repository.TopicRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {InMemoryRepositoryConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
public class TopicRepositoryImplIT {
    @Autowired
    private TopicRepository topicRepository;

    @Test
    @DatabaseSetup("/ds/conf-mgmt.xml")
    public void testFindAllShouldReturntwo() {
        int expectedSize = 2;
        Collection<Topic> topics = (Collection<Topic>) topicRepository.findAll();
        assertEquals(topics.size(), expectedSize);
    }
    @Test
    @DatabaseSetup("/ds/conf-mgmt.xml")
    public void testFindTopicByWrongName() {
        assertNull(topicRepository.findTopicByName("wrong"));
    }

    @Test
    @DatabaseSetup("/ds/conf-mgmt.xml")
    public void testFindTopicByCorrectName() {
        assertNotNull(topicRepository.findTopicByName("RNN"));
    }

    @Test
    @DatabaseSetup("/ds/conference.xml")
    public void testCreateTopic() {
        Topic newTopic = new Topic("name");
        topicRepository.save(newTopic);
        int expectedSize = 3;
        Collection<Topic> topics = (Collection<Topic>) topicRepository.findAll();
        assertEquals(topics.size(), expectedSize);
    }

    @Test
    @DatabaseSetup("/ds/insert-conference.xml")
    public void testDeleteTopic() {
        Topic newTopic = new Topic("name");
        Topic insertedTopic = topicRepository.save(newTopic);
        topicRepository.delete(insertedTopic);
        int expectedSize = 2;
        Collection<Topic> topics = (Collection<Topic>) topicRepository.findAll();
        assertEquals(topics.size(), expectedSize);
    }
}