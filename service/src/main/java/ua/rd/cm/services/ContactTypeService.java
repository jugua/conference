package ua.rd.cm.services;

import ua.rd.cm.domain.ContactType;

import java.util.List;

/**
 * @author Olha_Melnyk
 */
public interface ContactTypeService {

    ContactType find(Long id);

    void save(ContactType contactType);

    void update(ContactType contactType);

    List<ContactType> findAll();

    List<ContactType> findByName(String name);
}
