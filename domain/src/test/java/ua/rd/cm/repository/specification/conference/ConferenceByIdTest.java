package ua.rd.cm.repository.specification.conference;

import org.junit.Test;
import ua.rd.cm.domain.Conference;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ConferenceByIdTest {

    @Test
    public void testMethodShouldReturnFalseOnMismatch() {
        final Long id = 1337L;
        ConferenceById conferenceById = new ConferenceById(id);

        assertFalse(conferenceById.test(new Conference()));
    }

    @Test
    public void testMethodShouldReturnTrueOnIdEquality() {
        final Long id = 1337L;
        ConferenceById conferenceById = new ConferenceById(id);

        Conference conference = new Conference();
        conference.setId(id);

        assertTrue(conferenceById.test(conference));
    }
}