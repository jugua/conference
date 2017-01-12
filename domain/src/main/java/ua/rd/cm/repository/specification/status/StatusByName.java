package ua.rd.cm.repository.specification.status;

import ua.rd.cm.domain.TalkStatus;
import ua.rd.cm.repository.specification.Specification;
@Deprecated
public class StatusByName implements Specification<TalkStatus> {

    private String name;

    public StatusByName(String name) {
        this.name = name;
    }

    @Override
    public String toSqlClauses() {
        return String.format(" s.name = '%s' ", name);
    }

    @Override
    public boolean test(TalkStatus status) {
        return status.getName().equals(name);
    }
}
