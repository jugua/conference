package service.businesslogic.api;

import java.util.List;

import domain.model.ContactType;

public interface ContactTypeService {

    List<ContactType> getAll();

    ContactType getById(Long id);

    ContactType getByName(String name);

    void save(ContactType contactType);

    void update(ContactType contactType);
}
