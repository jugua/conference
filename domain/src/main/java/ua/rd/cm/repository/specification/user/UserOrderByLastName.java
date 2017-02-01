package ua.rd.cm.repository.specification.user;

import ua.rd.cm.domain.User;
import ua.rd.cm.repository.specification.Specification;

/**
 * Created by click on 1/30/2017.
 */
public class UserOrderByLastName implements Specification<User> {
    private Specification query;

    public UserOrderByLastName(Specification query) {
        this.query = query;
    }

    @Override
    public String toSqlClauses() {
        return query.toSqlClauses() + "ORDER BY u.lastName";
    }

    @Override
    public boolean test(User user) {
        return true;
    }

}
