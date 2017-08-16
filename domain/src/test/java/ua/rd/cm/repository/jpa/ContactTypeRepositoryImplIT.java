package ua.rd.cm.repository.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import ua.rd.cm.domain.ContactType;
import ua.rd.cm.repository.ContactTypeRepository;
import ua.rd.cm.repository.CrudRepository;

public class ContactTypeRepositoryImplIT extends AbstractJpaCrudRepositoryIT<ContactType> {
    @Autowired
    private ContactTypeRepository repository;

    @Override
    protected CrudRepository<ContactType> createRepository() {
        return (CrudRepository) repository;
    }

    @Override
    protected ContactType createEntity() {
        ContactType contactType = new ContactType();
        contactType.setName("twitter");
        return contactType;
    }
}