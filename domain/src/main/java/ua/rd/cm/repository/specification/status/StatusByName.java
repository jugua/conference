package ua.rd.cm.repository.specification.status;

import ua.rd.cm.domain.Status;
import ua.rd.cm.repository.specification.Specification;

/**
 * @author Olha_Melnyk
 */
public class StatusByName implements Specification<Status> {

    private String name;

    public StatusByName(String name) {
        this.name = name;
    }

    @Override
    public String toSqlClauses() {
        return String.format(" s.name = '%s' ", name);
    }

    @Override
    public boolean test(Status status) {
        return status.getName().equals(name);
    }
}
