package ua.rd.cm.repository.specification.conference;

import ua.rd.cm.domain.Conference;
import ua.rd.cm.repository.specification.Specification;

import java.time.LocalDate;

public class OngoingConferenceFilter implements Specification<Conference> {

    @Override
    public String toSqlClauses() {
        LocalDate now = LocalDate.now();
        return String.format(" c.startDate >= %s AND c.endDate < %s ", now, now);
    }

    @Override
    public boolean test(Conference conference) {
        LocalDate now = LocalDate.now();
        return conference.getStartDate() != null && conference.getEndDate() != null
                && now.isAfter(conference.getStartDate()) && now.isBefore(conference.getEndDate());
    }
}
