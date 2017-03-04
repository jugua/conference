package ua.rd.cm.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.rd.cm.domain.ContactType;
import ua.rd.cm.repository.ContactTypeRepository;
import ua.rd.cm.repository.specification.contacttype.ContactTypeById;
import ua.rd.cm.repository.specification.contacttype.ContactTypeByName;
import ua.rd.cm.services.ContactTypeService;

import java.util.List;

/**
 * @author Olha_Melnyk
 */
@Service
public class ContactTypeServiceImpl implements ContactTypeService {

    private ContactTypeRepository contactTypeRepository;

    @Autowired
    public ContactTypeServiceImpl(ContactTypeRepository contactTypeRepository) {
        this.contactTypeRepository = contactTypeRepository;
    }

    @Override
    public ContactType find(Long id) {
        return contactTypeRepository.findBySpecification(new ContactTypeById(id)).get(0);
    }


    @Override
    public List<ContactType> findByName(String name) {
        return contactTypeRepository.findBySpecification(new ContactTypeByName(name));
    }

    @Override
    @Transactional
    public void save(ContactType contactType) {
        contactTypeRepository.save(contactType);
    }

    @Override
    @Transactional
    public void update(ContactType contactType) {
        contactTypeRepository.update(contactType);
    }

    @Override
    public List<ContactType> findAll() {
        return contactTypeRepository.findAll();
    }

}
