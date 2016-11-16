package ua.rd.cm.repository.specification.role;

import ua.rd.cm.domain.Role;
import ua.rd.cm.repository.specification.Specification;

/**
 * @author Mariia Lapovska
 */
public class RoleByName implements Specification<Role> {

    private String name;

    public RoleByName(String name) {
        super();
        this.name = name;
    }

    @Override
    public boolean test(Role role) {
        return role.getName().equals(name);
    }

    @Override
    public String toSqlClauses() {
        return String.format(" r.name = ' %s '", name);
    }
}
