package ua.rd.cm.repository.specification.type;

import ua.rd.cm.domain.Type;
import ua.rd.cm.repository.specification.Specification;

/**
 * @author Mariia Lapovska
 */
public class TypeById implements Specification<Type> {

    private Long id;

    public TypeById(Long id) {
        this.id = id;
    }

    @Override
    public String toSqlClauses() {
        return String.format(" t.id = '%s' ", id);
    }

    @Override
    public boolean test(Type type) {
        return type.getId().equals(id);
    }
}
