package ua.rd.cm.repository.specification.user;

import ua.rd.cm.domain.User;
import ua.rd.cm.repository.specification.Specification;

/**
 * Created by click on 1/30/2017.
 */
public class UserAddRoleToQuery implements Specification<User> {
    private String roleName;

    public UserAddRoleToQuery(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toSqlClauses() {
        return String.format(" r.name = '%s' ", roleName);
    }

    @Override
    public boolean test(User user) {
        return false;
    }
}
