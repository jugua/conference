package ua.rd.cm.repository.jpa;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ua.rd.cm.config.InMemoryRepositoryConfig;
import ua.rd.cm.domain.Topic;
import ua.rd.cm.repository.TopicRepository;

import java.util.Collection;

import static org.junit.Assert.*;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {InMemoryRepositoryConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup("/ds/conf-mgmt.xml")
public class TopicRepositoryTest{
    @Autowired
    private TopicRepository topicRepository;

    @Test
    public void testFindAllShouldReturntwo(){
        int expectedSize = 2;
        Collection<Topic> topics = (Collection<Topic>) topicRepository.findAll();
        assertEquals(topics.size(), expectedSize);
    }

    @Test
    public void testFindTopicByWrongName(){
        assertNull(topicRepository.findTopicByName("wrong"));
    }

    @Test
    public void testFindTopicByCorrectName(){
        assertNotNull(topicRepository.findTopicByName("RNN"));
    }

    @Test
    @Ignore
    public void testCreateTopic(){
        Topic newTopic = new Topic("name");
        topicRepository.save(newTopic);
        int expectedSize = 3;
        Collection<Topic> topics = (Collection<Topic>) topicRepository.findAll();
        assertEquals(topics.size(), expectedSize);
    }

    @Test
    public void testDeleteTopic(){
        Topic newTopic = new Topic("name");
        Topic insertedTopic = topicRepository.save(newTopic);
        topicRepository.delete(insertedTopic);
        int expectedSize = 2;
        Collection<Topic> topics = (Collection<Topic>) topicRepository.findAll();
        assertEquals(topics.size(), expectedSize);
    }
}