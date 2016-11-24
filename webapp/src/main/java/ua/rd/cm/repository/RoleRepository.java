package ua.rd.cm.repository;

import ua.rd.cm.domain.Role;
import ua.rd.cm.repository.specification.Specification;

import java.util.List;

/**
 * @author Mariia Lapovska
 */
public interface RoleRepository {

    void saveRole(Role role);

    List<Role> findAll();

    List<Role> findBySpecification(Specification<Role> spec);
}
