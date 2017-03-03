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
import ua.rd.cm.repository.specification.AndSpecification;
import ua.rd.cm.repository.specification.OrSpecification;
import ua.rd.cm.repository.specification.Specification;
import ua.rd.cm.repository.specification.conference.ConferenceEndDateIsNull;
import ua.rd.cm.repository.specification.conference.ConferenceEndDateEarlierThanNow;
import ua.rd.cm.repository.specification.conference.ConferenceEndDateLaterOrEqualToNow;

import java.time.LocalDate;
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
    private static final Specification<Conference> UPCOMING = new OrSpecification<>(
            new ConferenceEndDateIsNull(true),
            new ConferenceEndDateLaterOrEqualToNow()
    );
    private static final Specification<Conference> PAST = new AndSpecification<>(
            new ConferenceEndDateIsNull(false),
            new ConferenceEndDateEarlierThanNow()
    );

    @Autowired
    private ConferenceRepository conferenceRepository;

    @Test
    public void getByIdShouldReturnNullIfDatabaseEmpty() {
        assertNull(conferenceRepository.findById(-100L));
    }

    @Test
    @DatabaseSetup("/ds/conference.xml")
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
    @DatabaseSetup("/ds/conference.xml")
    public void updateShouldUpdateCorrectly() {
        String newTitle = "test 1122";

        Conference conference = conferenceRepository.findById(-100L);
        conference.setTitle(newTitle);

        conferenceRepository.update(conference);

        assertThat(conference, is(conferenceRepository.findById(-100L)));
    }

    @Test
    @DatabaseSetup("/ds/conference.xml")
    public void removeShouldRemoveFromDatabase() {
        long id = -100;
        Conference byId = conferenceRepository.findById(id);
        conferenceRepository.remove(byId);

        assertNull(conferenceRepository.findById(id));
    }

    @Test
    @DatabaseSetup("/ds/conference.xml")
    public void findUpcomingShouldContainConferenceWithNoDateSpecified() {
        List<Conference> upcoming = conferenceRepository.findBySpecification(UPCOMING);
        assertFalse(upcoming.isEmpty());
    }

    @Test
    @DatabaseSetup("/ds/upcoming-conference.xml")
    public void findUpcomingShouldContainConferenceWhichEndsInFuture() {
        List<Conference> upcoming = conferenceRepository.findBySpecification(UPCOMING);
        assertFalse(upcoming.isEmpty());
    }

    @Test
    @DatabaseSetup("/ds/past-conference.xml")
    public void findUpcomingShouldNotContainConferenceThatHasAlreadyStarted() {
        List<Conference> upcoming = conferenceRepository.findBySpecification(UPCOMING);
        assertTrue(upcoming.isEmpty());
    }

    @Test
    public void findUpcomingShouldContainConferenceThatEndsToday() {
        Conference conference = createWithName("someName");
        conference.setEndDate(LocalDate.now());

        conferenceRepository.save(conference);

        List<Conference> upcoming = conferenceRepository.findBySpecification(UPCOMING);
        assertFalse(upcoming.isEmpty());
    }

    @Test
    public void findPastShouldNotContainConferenceThatEndsToday() {
        Conference conference = createWithName("someName");
        conference.setEndDate(LocalDate.now());

        conferenceRepository.save(conference);

        List<Conference> upcoming = conferenceRepository.findBySpecification(PAST);
        assertTrue(upcoming.isEmpty());
    }

    @Test
    @DatabaseSetup("/ds/past-conference.xml")
    public void findPastShouldContainPastConferences() {
        List<Conference> past = conferenceRepository.findBySpecification(PAST);
        assertFalse(past.isEmpty());
    }

    @Test
    @DatabaseSetup("/ds/upcoming-conference.xml")
    public void findPastShouldReturnEmptyIfThereIsNoPastConferences() {
        List<Conference> past = conferenceRepository.findBySpecification(PAST);
        assertTrue(past.isEmpty());
    }

    @Test
    @DatabaseSetup("ds/insert-conference.xml")
    public void saveShouldInsertCorrectlyLinkedEntities() {

    }

    private static Conference createWithName(String title) {
        Conference conference = new Conference();
        conference.setTitle(title);
        return conference;
    }
}
