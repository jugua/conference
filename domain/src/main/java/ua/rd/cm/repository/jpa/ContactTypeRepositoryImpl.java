package ua.rd.cm.repository.jpa;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ua.rd.cm.domain.ContactType;
import ua.rd.cm.repository.ContactTypeRepository;
import ua.rd.cm.repository.specification.Specification;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Olha_Melnyk
 */
@Repository
public class ContactTypeRepositoryImpl implements ContactTypeRepository {

    @PersistenceContext
    private EntityManager em;

    private Logger logger = Logger.getLogger(ContactTypeRepositoryImpl.class);

    @Override
    public void saveContactType(ContactType contactType) {
        em.persist(contactType);
        logger.info(contactType + " is saved");
    }

    @Override
    public void updateContactType(ContactType contactType) {
        em.merge(contactType);
        logger.info(contactType + " is updated");
    }

    @Override
    public void removeContactType(ContactType contactType) {
        em.remove(contactType);
        logger.info(contactType + " is removed");
    }

    @Override
    public List<ContactType> findAll() {
        return em.createQuery("SELECT c FROM ContactType c", ContactType.class).getResultList();
    }

    @Override
    public List<ContactType> findBySpecification(Specification<ContactType> spec) {
        return em.createQuery("SELECT c FROM ContactType c WHERE " + spec.toSqlClauses(), ContactType.class).getResultList();
    }
}
