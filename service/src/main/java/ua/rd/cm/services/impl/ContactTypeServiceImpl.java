package ua.rd.cm.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.rd.cm.domain.ContactType;
import ua.rd.cm.repository.ContactTypeRepository;
import ua.rd.cm.repository.specification.contacttype.ContactTypeById;
import ua.rd.cm.repository.specification.contacttype.ContactTypeByName;
import ua.rd.cm.services.ContactTypeService;
import ua.rd.cm.services.exception.ResourceNotFoundException;

import java.util.List;

@Service
public class ContactTypeServiceImpl implements ContactTypeService {

    private ContactTypeRepository contactTypeRepository;

    @Autowired
    public ContactTypeServiceImpl(ContactTypeRepository contactTypeRepository) {
        this.contactTypeRepository = contactTypeRepository;
    }

    @Override
    public ContactType find(Long id) {
        List<ContactType> contatcTypes = contactTypeRepository.findBySpecification(new ContactTypeById(id));
        if (contatcTypes.isEmpty()) {
            throw new ResourceNotFoundException("contact_type_not_found");
        }
        return contatcTypes.get(0);
    }

    @Override
    public List<ContactType> findByName(String name) {
        return contactTypeRepository.findBySpecification(new ContactTypeByName(name));
    }

    @Override
    @Transactional
    public void save(ContactType contactType) {
        contactTypeRepository.saveContactType(contactType);
    }

    @Override
    @Transactional
    public void update(ContactType contactType) {
        contactTypeRepository.updateContactType(contactType);
    }

    @Override
    public List<ContactType> findAll() {
        return contactTypeRepository.findAll();
    }
}
