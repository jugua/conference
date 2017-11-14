package domain.repository;

import org.springframework.data.repository.CrudRepository;

import domain.model.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findByName(String name);
}
