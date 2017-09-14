package ua.rd.cm.repository;

import org.springframework.data.repository.CrudRepository;

import ua.rd.cm.domain.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findByName(String name);
}
