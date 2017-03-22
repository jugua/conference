package ua.rd.cm.repository;

import ua.rd.cm.domain.User;
import ua.rd.cm.repository.specification.Specification;

import java.util.List;

/**
 * Repository
 *
 * @see User
 */
public interface UserRepository extends CrudRepository<User> {

    List<User> findAllWithRoles(Specification<User> spec);
}
