package ua.rd.cm.services.business_logic.impl;

import static ua.rd.cm.services.exception.ResourceNotFoundException.CONTACT_TYPE_NOT_FOUND;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.rd.cm.domain.ContactType;
import ua.rd.cm.repository.ContactTypeRepository;
import ua.rd.cm.services.business_logic.ContactTypeService;
import ua.rd.cm.services.exception.ResourceNotFoundException;

@Service
public class ContactTypeServiceImpl implements ContactTypeService {

    private ContactTypeRepository contactTypeRepository;

    @Autowired
    public ContactTypeServiceImpl(ContactTypeRepository contactTypeRepository) {
        this.contactTypeRepository = contactTypeRepository;
    }

    @Override
    public ContactType find(Long id) {
        ContactType contactType = contactTypeRepository.findById(id);
        if (contactType == null) {
            throw new ResourceNotFoundException(CONTACT_TYPE_NOT_FOUND);
        }
        return contactType;
    }

    @Override
    public List<ContactType> findByName(String name) {
        return contactTypeRepository.findByName(name);
    }

    @Override
    @Transactional
    public void save(ContactType contactType) {
        contactTypeRepository.save(contactType);
    }

    @Override
    @Transactional
    public void update(ContactType contactType) {
        contactTypeRepository.save(contactType);
    }

    @Override
    public List<ContactType> findAll() {
        return contactTypeRepository.findAll();
    }
}
