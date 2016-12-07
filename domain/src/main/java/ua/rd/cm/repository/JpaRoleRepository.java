package ua.rd.cm.repository;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ua.rd.cm.domain.Role;
import ua.rd.cm.repository.specification.Specification;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Mariia Lapovska
 */
@Repository
public class JpaRoleRepository implements RoleRepository {

    @PersistenceContext
    private EntityManager em;

    private Logger logger = Logger.getLogger(JpaRoleRepository.class);

    @Override
    public void saveRole(Role role) {
        em.persist(role);
        logger.info(role.toString() + " is saved");
    }

    @Override
    public List<Role> findAll() {
        return em.createQuery("SELECT r FROM Role r", Role.class)
                .getResultList();
    }

    @Override
    public List<Role> findBySpecification(Specification<Role> spec) {
        return em.createQuery("SELECT r FROM Role r WHERE " + spec
                .toSqlClauses(), Role.class).getResultList();
    }
}
