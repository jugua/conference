package ua.rd.cm.repository.specification.conference;

import org.junit.Before;
import org.junit.Test;
import ua.rd.cm.domain.Conference;
import ua.rd.cm.repository.specification.OrSpecification;
import ua.rd.cm.repository.specification.Specification;

import java.time.LocalDate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UpcomingConferenceFilterTest {

    private Specification<Conference> upcomingFilter;

    @Before
    public void setUp() {
        upcomingFilter = new OrSpecification<>(
                new ConferenceEndDateIsNull(true),
                new ConferenceEndDateLaterOrEqualToNow()
        );
    }

    @Test
    public void testShouldReturnTrueIfConferenceHasNotFinishedYet() throws Exception {
        Conference conference = new Conference();
        conference.setEndDate(LocalDate.now().plusDays(10));
        assertTrue(upcomingFilter.test(conference));
    }

    @Test
    public void testShouldReturnTrueIfConferenceHasNoEndDateSpecified() throws Exception {
        Conference conference = new Conference();
        assertTrue(upcomingFilter.test(conference));
    }

    @Test
    public void testShouldReturnFalseIfConferenceHasAlreadyFinished() throws Exception {
        Conference conference = new Conference();
        conference.setEndDate(LocalDate.now().minusDays(10));
        assertFalse(upcomingFilter.test(conference));
    }
}