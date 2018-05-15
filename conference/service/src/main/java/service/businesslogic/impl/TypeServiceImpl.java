package service.businesslogic.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.model.Type;
import domain.repository.TypeRepository;
import lombok.AllArgsConstructor;
import service.businesslogic.api.TypeService;
import service.businesslogic.dto.CreateTypeDto;
import service.businesslogic.dto.TypeDto;
import service.businesslogic.exception.TypeNotFoundException;

@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class TypeServiceImpl implements TypeService {

    private TypeRepository typeRepository;
    private ModelMapper modelMapper;

    @Override
    public Type getById(Long id) {
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
    public List<TypeDto> getAll() {
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
