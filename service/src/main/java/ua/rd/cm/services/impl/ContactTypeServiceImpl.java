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

import static ua.rd.cm.services.exception.ResourceNotFoundException.CONTACT_TYPE_NOT_FOUND;

@Service
public class ContactTypeServiceImpl implements ContactTypeService {

@Autowired
    private ContactTypeRepository contactTypeRepository;

    @Autowired
    public ContactTypeServiceImpl(ContactTypeRepository contactTypeRepository) {
        this.contactTypeRepository = contactTypeRepository;
    }

    @Override
    public ContactType find(Long id) {
        ContactType contatcType = contactTypeRepository. findById(id);
        if (contatcType==null) {
            throw new ResourceNotFoundException(CONTACT_TYPE_NOT_FOUND);
        }
        return contatcType;
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
        contactTypeRepository.update(contactType);
    }

    @Override
    public List<ContactType> findAll() {
        return contactTypeRepository.findAll();
    }
}
