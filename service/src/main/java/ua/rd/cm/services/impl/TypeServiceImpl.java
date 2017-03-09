package ua.rd.cm.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.rd.cm.domain.Type;
import ua.rd.cm.dto.TypeDto;
import ua.rd.cm.repository.TypeRepository;
import ua.rd.cm.repository.specification.type.TypeById;
import ua.rd.cm.repository.specification.type.TypeByName;
import ua.rd.cm.services.TypeService;
import ua.rd.cm.services.exception.TypeNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TypeServiceImpl implements TypeService {

    private TypeRepository typeRepository;
    private ModelMapper modelMapper;

    @Autowired
    public TypeServiceImpl(TypeRepository typeRepository, ModelMapper modelMapper) {
        this.typeRepository = typeRepository;
        this.modelMapper = modelMapper;
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
        typeRepository.save(type);
    }

    @Override
    public List<TypeDto> findAll() {
        return typeRepository.findAll().stream()
                .map(t -> modelMapper.map(t, TypeDto.class))
                .collect(Collectors.toList());
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
