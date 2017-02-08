package ua.rd.cm.repository.specification.conference;

import org.junit.Test;
import org.springframework.test.context.TestPropertySource;
import ua.rd.cm.domain.Conference;

import static org.junit.Assert.*;

public class ConferenceByIdTest {

    @Test(expected = NullPointerException.class)
    public void constructorWithNullIdShouldThrow() {
        new ConferenceById(null);
    }

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