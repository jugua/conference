package ua.rd.cm.repository.specification.type;

import ua.rd.cm.domain.Type;
import ua.rd.cm.repository.specification.Specification;

/**
 * @author Mariia Lapovska
 */
public class TypeByName implements Specification<Type> {

    private String name;

    public TypeByName(String name) {
        super();
        this.name = name;
    }

    @Override
    public boolean test(Type type) {
        return type.getName().equals(name);
    }

    @Override
    public String toSqlClauses() {
        return String.format(" t.name = ' %s '", name);
    }
}
