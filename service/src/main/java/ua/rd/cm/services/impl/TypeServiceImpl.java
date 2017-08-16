package ua.rd.cm.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.rd.cm.domain.Type;
import ua.rd.cm.dto.CreateTypeDto;
import ua.rd.cm.dto.TypeDto;
import ua.rd.cm.repository.TypeRepository;
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
        Type type = typeRepository.findById(id);
        if (type == null) {
            throw new TypeNotFoundException();
        }
        return type;
    }

    @Override
    @Transactional
    public Long save(CreateTypeDto createTypeDto) {
        Type type = typeRepository.findByName(createTypeDto.getName());
        if (type != null) {
            return type.getId();
        }
        Type map = modelMapper.map(createTypeDto, Type.class);
        typeRepository.save(map);
        return map.getId();
    }

    @Override
    public List<TypeDto> findAll() {
        return typeRepository.findAll().stream()
                .map(t -> modelMapper.map(t, TypeDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Type getByName(String name) {
        Type type = typeRepository.findByName(name);
        if (type == null) {
            throw new TypeNotFoundException();
        }
        return type;
    }
}
