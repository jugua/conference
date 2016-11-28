package ua.rd.cm.repository.specification.contacttype;

import ua.rd.cm.domain.ContactType;
import ua.rd.cm.repository.specification.Specification;

/**
 * @author Olha_Melnyk
 */
public class ContactTypeByName implements Specification<ContactType> {
    private String name;

    public ContactTypeByName(String name) {
        this.name = name;
    }

    @Override
    public String toSqlClauses() {
        return String.format(" c.name = '%s' ", name);
    }

    @Override
    public boolean test(ContactType contactType) {
        return contactType.getName().equals(name);
    }
}