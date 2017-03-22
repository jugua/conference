package ua.rd.cm.repository.jpa;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.rd.cm.domain.Conference;
import ua.rd.cm.repository.ConferenceRepository;
import ua.rd.cm.repository.CrudRepository;
import ua.rd.cm.repository.specification.AndSpecification;
import ua.rd.cm.repository.specification.OrSpecification;
import ua.rd.cm.repository.specification.Specification;
import ua.rd.cm.repository.specification.conference.ConferenceEndDateEarlierThanNow;
import ua.rd.cm.repository.specification.conference.ConferenceEndDateIsNull;
import ua.rd.cm.repository.specification.conference.ConferenceEndDateLaterOrEqualToNow;

import javax.persistence.FlushModeType;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ConferenceRepositoryImplIT extends AbstractJpaCrudRepositoryIT<Conference> {
    private static final Specification<Conference> UPCOMING = new OrSpecification<>(
            new ConferenceEndDateIsNull(true),
            new ConferenceEndDateLaterOrEqualToNow()
    );
    private static final Specification<Conference> PAST = new AndSpecification<>(
            new ConferenceEndDateIsNull(false),
            new ConferenceEndDateEarlierThanNow()
    );

    @Autowired
    private ConferenceRepository repository;

    @Override
    protected CrudRepository<Conference> createRepository() {
        return repository;
    }

    @Override
    protected Conference createEntity() {
        Conference conference = new Conference();
        conference.setTitle("Title");
        conference.setLocation("some location");
        return conference;
    }

    @Test
    @DatabaseSetup("/ds/conference.xml")
    public void getAllWithTalks() {
        List<Conference> allWithTalks = repository.getAllWithTalks(new Specification<Conference>() {
            @Override
            public String toSqlClauses() {
                return " c.id is not null ";
            }

            @Override
            public boolean test(Conference conference) {
                return true;
            }
        });
        assertEquals(1, allWithTalks.size());
    }

    @Test
    @DatabaseSetup("/ds/admin-upcoming-conference.xml")
    public void getAllWithTalksShouldFetchTalks() {
        List<Conference> allWithTalks = repository.getAllWithTalks(new Specification<Conference>() {
            @Override
            public String toSqlClauses() {
                return " c.id is not null ";
            }

            @Override
            public boolean test(Conference conference) {
                return true;
            }
        });
        entityManager.flush();
        entityManager.setFlushMode(FlushModeType.COMMIT);

        for (Conference allWithTalk : allWithTalks) {
            assertFalse(allWithTalk.getTalks().isEmpty());
        }
    }

    @Test
    @DatabaseSetup("/ds/conference.xml")
    public void findUpcomingShouldContainConferenceWithNoDateSpecified() {
        List<Conference> upcoming = repository.findBySpecification(UPCOMING);
        assertFalse(upcoming.isEmpty());
    }

    @Test
    @DatabaseSetup("/ds/upcoming-conference.xml")
    public void findUpcomingShouldContainConferenceWhichEndsInFuture() {
        List<Conference> upcoming = repository.findBySpecification(UPCOMING);
        assertFalse(upcoming.isEmpty());
    }

    @Test
    @DatabaseSetup("/ds/past-conference.xml")
    public void findUpcomingShouldNotContainConferenceThatHasAlreadyStarted() {
        List<Conference> upcoming = repository.findBySpecification(UPCOMING);
        assertTrue(upcoming.isEmpty());
    }

    @Test
    public void findUpcomingShouldContainConferenceThatEndsToday() {
        Conference conference = createWithTitle("someName");
        conference.setEndDate(LocalDate.now());

        repository.save(conference);
        entityManager.flush();

        List<Conference> upcoming = repository.findBySpecification(UPCOMING);
        assertFalse(upcoming.isEmpty());
    }

    @Test
    public void findPastShouldNotContainConferenceThatEndsToday() {
        Conference conference = createWithTitle("someName");
        conference.setEndDate(LocalDate.now());

        repository.save(conference);
        entityManager.flush();

        List<Conference> upcoming = repository.findBySpecification(PAST);
        assertTrue(upcoming.isEmpty());
    }

    @Test
    @DatabaseSetup("/ds/past-conference.xml")
    public void findPastShouldContainPastConferences() {
        List<Conference> past = repository.findBySpecification(PAST);
        assertFalse(past.isEmpty());
    }

    @Test
    @DatabaseSetup("/ds/upcoming-conference.xml")
    public void findPastShouldReturnEmptyIfThereIsNoPastConferences() {
        List<Conference> past = repository.findBySpecification(PAST);
        assertTrue(past.isEmpty());
    }

    private static Conference createWithTitle(String title) {
        Conference conference = new Conference();
        conference.setTitle(title);
        return conference;
    }
}