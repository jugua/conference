package ua.rd.cm.repository.jpa;

import org.springframework.stereotype.Repository;
import ua.rd.cm.domain.Role;
import ua.rd.cm.repository.RoleRepository;

@Repository
public class RoleRepositoryImpl
        extends AbstractJpaCrudRepository<Role> implements RoleRepository {

    public RoleRepositoryImpl() {
        super("r", Role.class);
    }
}
