package ua.rd.cm.repository.jpa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Collection;
import java.util.List;

import org.junit.Before;
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

import ua.rd.cm.config.InMemoryRepositoryConfig;
import ua.rd.cm.domain.Topic;
import ua.rd.cm.repository.TopicRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {InMemoryRepositoryConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
public class TopicRepositoryImplIT {

    @Autowired
    private TopicRepository testing;
    private Topic newTopic;

    @Before
    public void setUp() throws Exception {
        newTopic = new Topic(1L, "name");
    }

    @Test
    @DatabaseSetup("/ds/conf-mgmt.xml")
    public void testFindAllShouldReturntwo() {
        int expectedSize = 2;
        Collection<Topic> topics = (Collection<Topic>) testing.findAll();
        assertEquals(topics.size(), expectedSize);
    }

    @Test
    @DatabaseSetup("/ds/conf-mgmt.xml")
    public void testFindTopicByWrongName() {
        assertNull(testing.findTopicByName("wrong"));
    }

    @Test
    @DatabaseSetup("/ds/conf-mgmt.xml")
    public void testFindTopicByCorrectName() {
        assertNotNull(testing.findTopicByName("RNN"));
    }

    @Test
    @DatabaseSetup("/ds/conference.xml")
    public void testCreateTopic() {
        testing.save(newTopic);
        List<Topic> topics = testing.findAll();
        assertEquals(topics.size(), 3);
    }

    @Test
    @DatabaseSetup("/ds/insert-conference.xml")
    public void testDeleteTopic() {
        Topic insertedTopic = testing.save(newTopic);
        testing.delete(insertedTopic);
        Collection<Topic> topics = testing.findAll();
        assertEquals(topics.size(), 2);
    }
}