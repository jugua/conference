package ua.rd.cm.repository.jpa;

import org.springframework.stereotype.Repository;
import ua.rd.cm.domain.ContactType;
import ua.rd.cm.repository.ContactTypeRepository;

@Repository
public class ContactTypeRepositoryImpl
        extends AbstractJpaCrudRepository<ContactType> implements ContactTypeRepository {

    public ContactTypeRepositoryImpl() {
        super("c", ContactType.class);
    }
}
