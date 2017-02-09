package ua.rd.cm.repository.specification.conference;

import ua.rd.cm.domain.Conference;
import ua.rd.cm.repository.specification.Specification;

import javax.persistence.Query;
import java.time.LocalDate;

public class PastConferenceFilter implements Specification<Conference> {

    @Override
    public String toSqlClauses() {
        return " c.endDate IS NOT NULL AND c.endDate < :now ";
    }

    @Override
    public Query setParameters(Query query) {
        return query.setParameter("now", LocalDate.now());
    }

    @Override
    public boolean test(Conference conference) {
        LocalDate endDate = conference.getEndDate();
        return endDate != null && LocalDate.now().isAfter(endDate);
    }
}
