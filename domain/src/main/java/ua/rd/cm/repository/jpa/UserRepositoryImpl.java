package ua.rd.cm.repository.jpa;

import org.springframework.stereotype.Repository;
import ua.rd.cm.domain.User;
import ua.rd.cm.repository.UserRepository;
import ua.rd.cm.repository.specification.Specification;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserRepositoryImpl
        extends AbstractJpaCrudRepository<User> implements UserRepository {

    public UserRepositoryImpl() {
        super("u", User.class);
    }

    @Override
    public List<User> findAllWithRoles(Specification<User> spec) {
        TypedQuery<User> query = entityManager.createQuery(
                String.format("%s JOIN u.userRoles r WHERE %s", selectJpql, spec.toSqlClauses()), type
        );
        spec.setParameters(query);
        return query.getResultList();
    }
}
