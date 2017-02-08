package ua.rd.cm.repository.specification.conference;

import ua.rd.cm.domain.Conference;
import ua.rd.cm.repository.specification.Specification;

import java.time.LocalDate;

public class UpcomingConferenceFilter implements Specification<Conference> {

    @Override
    public String toSqlClauses() {
        LocalDate now = LocalDate.now();
        return String.format(" c.callForPaperStartDate >= %s AND c.callForPaperEndDate < %s ", now, now);
    }

    @Override
    public boolean test(Conference conference) {
        LocalDate now = LocalDate.now();

        LocalDate callForPaperStartDate = conference.getCallForPaperStartDate();
        LocalDate callForPaperEndDate = conference.getCallForPaperEndDate();

        return callForPaperStartDate == null || callForPaperEndDate == null ||
                (now.isAfter(callForPaperStartDate) && now.isBefore(callForPaperEndDate));
    }
}
