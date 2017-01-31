package ua.rd.cm.repository.specification.user;

import ua.rd.cm.domain.Role;
import ua.rd.cm.domain.User;
import ua.rd.cm.repository.specification.Specification;

public class UserByRoleJoin implements Specification<User> {

    private Specification specification;

    public UserByRoleJoin(Specification specification) {
        this.specification = specification;
    }

    @Override
    public String toSqlClauses() {
        return String.format(" JOIN u.userRoles r WHERE (%s) ", specification.toSqlClauses());
    }

    @Override
    public boolean test(User user) {
//        for (Role currentRole : user.getUserRoles()) {
//            if (currentRole.getName().equals(roleName)) {
//                return true;
//            }
//        }
        return true;
    }
}