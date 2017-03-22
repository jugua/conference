package ua.rd.cm.repository.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import ua.rd.cm.domain.Role;
import ua.rd.cm.repository.CrudRepository;
import ua.rd.cm.repository.RoleRepository;

public class RoleRepositoryImplIT extends AbstractJpaCrudRepositoryIT<Role> {
    @Autowired
    private RoleRepository repository;

    @Override
    protected CrudRepository<Role> createRepository() {
        return repository;
    }

    @Override
    protected Role createEntity() {
        Role role = new Role();
        role.setName("Admin");
        return role;
    }
}