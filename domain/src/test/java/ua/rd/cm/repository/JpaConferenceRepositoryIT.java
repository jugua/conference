package ua.rd.cm.repository;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import ua.rd.cm.config.InMemoRepositoryConfig;
import ua.rd.cm.domain.Conference;
import ua.rd.cm.repository.specification.conference.ConferenceById;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = InMemoRepositoryConfig.class)
@TestExecutionListeners(listeners = {
        DependencyInjectionTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class
})
public class JpaConferenceRepositoryIT {

    @Autowired
    private ConferenceRepository conferenceRepository;

    @Test
    public void getByIdShouldReturnNullIfDatabaseEmpty() {
        assertNull(conferenceRepository.findById(-100L));
    }

    @Test
    @DatabaseSetup("/ds/conference-ds.xml")
    public void getByIdShouldReturnPersisted() {
        assertNotNull(conferenceRepository.findById(-100L));
    }

    @Test
    public void saveShouldPersistInDatabase() {
        Conference expected = createWithName("expected");
        conferenceRepository.save(expected);

        Conference actual = conferenceRepository.findById(expected.getId());
        assertThat(actual, equalTo(expected));
    }

    @Test
    @DatabaseSetup("/ds/conference-ds.xml")
    public void updateShouldUpdateCorrectly() {
        String newTitle = "test 1122";

        Conference conference = conferenceRepository.findById(-100L);
        conference.setTitle(newTitle);

        conferenceRepository.update(conference);

        assertThat(conference, is(conferenceRepository.findById(-100L)));
    }

    @Test
    @DatabaseSetup("/ds/conference-ds.xml")
    public void removeShouldRemoveFromDatabase() {
        long id = -100;
        Conference byId = conferenceRepository.findById(id);
        conferenceRepository.remove(byId);

        assertNull(conferenceRepository.findById(id));
    }

    private static Conference createWithName(String title) {
        Conference conference = new Conference();
        conference.setTitle(title);
        return conference;
    }
}
