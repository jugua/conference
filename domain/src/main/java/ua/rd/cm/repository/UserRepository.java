package ua.rd.cm.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.rd.cm.domain.Role;
import ua.rd.cm.domain.User;

import java.util.List;
import java.util.Set;

/**
 * Repository
 *
 * @see User
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    @Override
    List<User> findAll();

    User findByEmail(String email);

    List<User> findAllByFirstName(String firstName);

    List<User> findAllByLastName(String lastName);

    List<User> findAllByUserRolesIsIn(Role name);

    List<User> findAllByUserRolesIsIn(List<Role> name);
}
