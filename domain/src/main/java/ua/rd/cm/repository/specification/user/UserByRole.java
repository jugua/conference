package ua.rd.cm.repository.specification.user;

import ua.rd.cm.domain.Role;
import ua.rd.cm.domain.User;
import ua.rd.cm.repository.specification.Specification;

/**
 * Created by click on 1/31/2017.
 */
public class UserByRole implements Specification<User> {
    private String roleName;

    public UserByRole(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toSqlClauses() {
        return String.format(" r.name = '%s' ", roleName);
    }

    @Override
    public boolean test(User user) {
        for (Role currentRole : user.getUserRoles()) {
            if (currentRole.getName().equals(roleName)) {
                return true;
            }
        }
        return false;
    }
}
