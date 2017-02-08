package ua.rd.cm.repository.specification.conference;

import ua.rd.cm.domain.Conference;
import ua.rd.cm.repository.specification.Specification;

import java.time.LocalDate;

public class UpcomingConferenceFilter implements Specification<Conference> {

    @Override
    public String toSqlClauses() {
        return String.format(" c.endDate < %s ", LocalDate.now());
    }

    @Override
    public boolean test(Conference conference) {
        LocalDate endDate = conference.getEndDate();
        return endDate == null || LocalDate.now().isBefore(endDate);
    }
}
