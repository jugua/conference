package service.businesslogic.api;

import java.util.List;

import domain.model.ContactType;


public interface ContactTypeService {

    ContactType find(Long id);

    void save(ContactType contactType);

    void update(ContactType contactType);

    List<ContactType> findAll();

    ContactType findByName(String name);
}
