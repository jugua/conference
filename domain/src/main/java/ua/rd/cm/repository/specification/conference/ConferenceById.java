package ua.rd.cm.repository.specification.conference;

import ua.rd.cm.domain.Conference;
import ua.rd.cm.repository.specification.Specification;

import java.util.Objects;

public class ConferenceById implements Specification<Conference> {
    private final Long id;

    public ConferenceById(Long id) {
        this.id = Objects.requireNonNull(id, "Id cannot be null");
    }

    @Override
    public String toSqlClauses() {
        return String.format(" c.id = '%s' ", id);
    }

    @Override
    public boolean test(Conference conference) {
        return id.equals(conference.getId());
    }
}
