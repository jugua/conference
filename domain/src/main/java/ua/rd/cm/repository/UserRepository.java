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

    List<User> findAllByUserRoles(Role name);

    List<User> findAllByUserRoles(List<Role> name);

//    @Query("select distinct u from User u JOIN u.userRoles r WHERE r.name = ?1")
//    List<User> findAllByRoleNames(String roleNames);

    @Query("select distinct u from User u  JOIN u.userRoles r WHERE r.name= ?1 or r.name = ?2")
    List<User> findAllByRoleNames(String roleName1, String roleName2);
}
