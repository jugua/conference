package service.businesslogic.impl;

import static service.businesslogic.exception.ResourceNotFoundException.CONTACT_TYPE_NOT_FOUND;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.model.ContactType;
import domain.repository.ContactTypeRepository;
import lombok.AllArgsConstructor;
import service.businesslogic.api.ContactTypeService;
import service.businesslogic.exception.ResourceNotFoundException;

@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class ContactTypeServiceImpl implements ContactTypeService {

    private ContactTypeRepository contactTypeRepository;

    @Override
    public ContactType find(Long id) {
        ContactType contactType = contactTypeRepository.findById(id);
        if (contactType == null) {
            throw new ResourceNotFoundException(CONTACT_TYPE_NOT_FOUND);
        }
        return contactType;
    }

    @Override
    public ContactType findByName(String name) {
        return contactTypeRepository.findFirstByName(name);
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
