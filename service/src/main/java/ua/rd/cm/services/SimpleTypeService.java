package ua.rd.cm.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.rd.cm.domain.Type;
import ua.rd.cm.repository.TypeRepository;
import ua.rd.cm.repository.specification.type.TypeById;
import ua.rd.cm.repository.specification.type.TypeByName;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Mariia Lapovska
 */
@Service
public class SimpleTypeService implements TypeService {

    private TypeRepository typeRepository;

    @Inject
    public SimpleTypeService(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    @Override
    public Type find(Long id) {
        return typeRepository.findBySpecification(new TypeById(id)).get(0);
    }

    @Override
    @Transactional
    public void save(Type type) {
        typeRepository.saveType(type);
    }

    @Override
    public List<Type> findAll() {
        return typeRepository.findAll();
    }

    @Override
    public List<Type> getByName(String name) {
        return typeRepository.findBySpecification(new TypeByName(name));
    }
}
