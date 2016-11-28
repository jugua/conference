package ua.rd.cm.repository;

import ua.rd.cm.domain.ContactType;
import ua.rd.cm.repository.specification.Specification;

import java.util.List;

/**
 * @author Olha_Melnyk
 */
public interface ContactTypeRepository {

    void saveContactType(ContactType contactType);

    void updateContactType(ContactType contactType);

    void removeContactType(ContactType contactType);

    List<ContactType> findAll();

    List<ContactType> findBySpecification(Specification<ContactType> spec);
}
