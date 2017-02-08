package ua.rd.cm.repository.specification.conference;

import ua.rd.cm.domain.Conference;
import ua.rd.cm.repository.specification.Specification;

import java.time.LocalDate;

public class PastConferenceFilter implements Specification<Conference> {

    @Override
    public String toSqlClauses() {
        return String.format(" c.endDate > %s ", LocalDate.now());
    }

    @Override
    public boolean test(Conference conference) {
        return conference.getEndDate() != null &&
                LocalDate.now().isAfter(conference.getEndDate());
    }
}
