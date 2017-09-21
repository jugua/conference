package ua.rd.cm.repository;

import org.springframework.data.repository.CrudRepository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import ua.rd.cm.domain.Role;

@RepositoryRestResource(exported = false)
@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findByName(String name);
}
