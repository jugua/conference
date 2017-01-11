package ua.rd.cm.repository.specification.status;

import ua.rd.cm.domain.Status;
import ua.rd.cm.repository.specification.Specification;

@Deprecated
public class StatusById implements Specification<Status> {

    private Long id;

    public StatusById(Long id) {
        this.id = id;
    }

    @Override
    public String toSqlClauses() {
        return String.format(" s.id = '%s' ", id);
    }

    @Override
    public boolean test(Status status) {
        return false;
    }
}
