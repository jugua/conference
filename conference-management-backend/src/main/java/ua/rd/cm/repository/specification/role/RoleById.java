package ua.rd.cm.repository.specification.role;

import ua.rd.cm.domain.Role;
import ua.rd.cm.repository.specification.Specification;

/**
 * @author Mariia Lapovska
 */
public class RoleById implements Specification<Role> {

    private Long id;

    public RoleById(Long id) {
        this.id = id;
    }

    @Override
    public String toSqlClauses() {
        return String.format(" r.id = '%s' ", id);
    }

    @Override
    public boolean test(Role role) {
        return role.getId().equals(id);
    }
}
