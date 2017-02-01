package ua.rd.cm.repository.specification.user;

import ua.rd.cm.domain.Role;
import ua.rd.cm.domain.User;
import ua.rd.cm.repository.specification.Specification;

public class UserByRoleJoin implements Specification<User> {

    private String roleName;

    public UserByRoleJoin(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toSqlClauses() {
        return String.format(" JOIN u.userRoles r WHERE r.name = '%s' ", roleName);
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