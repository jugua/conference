package ua.rd.cm.repository.specification.conference;

import ua.rd.cm.domain.Conference;
import ua.rd.cm.repository.specification.Specification;

import javax.persistence.Query;
import java.time.LocalDate;

public class ConferenceEndDateIsNull implements Specification<Conference> {
    private final boolean isNull;

    public ConferenceEndDateIsNull(boolean isNull) {
        this.isNull = isNull;
    }

    @Override
    public String toSqlClauses() {
        String str = isNull ? "" : "NOT";
        return " c.endDate IS " + str + " NULL";
    }

    @Override
    public Query setParameters(Query query) {
        return query;
    }

    @Override
    public boolean test(Conference conference) {
        LocalDate endDate = conference.getEndDate();
        if (isNull) {
            return endDate == null;
        }
        return endDate != null;
    }
}
