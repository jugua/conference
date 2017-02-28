package ua.rd.cm.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.rd.cm.domain.Type;
import ua.rd.cm.repository.TypeRepository;
import ua.rd.cm.repository.specification.type.TypeById;
import ua.rd.cm.repository.specification.type.TypeByName;
import ua.rd.cm.services.TypeService;
import ua.rd.cm.services.exception.EntityNotFoundException;
import ua.rd.cm.services.exception.TypeNotFoundException;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    private TypeRepository typeRepository;

    @Autowired
    public TypeServiceImpl(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    @Override
    public Type find(Long id) {
        List<Type> types = typeRepository.findBySpecification(new TypeById(id));
        if (types.isEmpty()) {
            throw new TypeNotFoundException();
        }
        return types.get(0);
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
    public Type getByName(String name) {
        List<Type> list = typeRepository.findBySpecification(new TypeByName(name));
        if (list.isEmpty()) {
            throw new TypeNotFoundException();
        }
        return list.get(0);
    }
}
