package ua.rd.cm.repository.specification.contacttype;

import ua.rd.cm.domain.ContactType;
import ua.rd.cm.repository.specification.Specification;

/**
 * @author Olha_Melnyk
 */
public class ContactTypeById implements Specification<ContactType> {
    private Long id;

    public ContactTypeById(Long id) {
        this.id = id;
    }

    @Override
    public String toSqlClauses() {
        return String.format(" c.id = '%s' ", id);
    }

    @Override
    public boolean test(ContactType contactType) {
        return contactType.getId().equals(id);
    }
}