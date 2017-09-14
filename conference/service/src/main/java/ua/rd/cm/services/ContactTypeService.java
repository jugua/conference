package ua.rd.cm.services;

import java.util.List;

import ua.rd.cm.domain.ContactType;


public interface ContactTypeService {

    ContactType find(Long id);

    void save(ContactType contactType);

    void update(ContactType contactType);

    List<ContactType> findAll();

    List<ContactType> findByName(String name);
}
