package service.businesslogic.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.repository.LevelRepository;
import lombok.AllArgsConstructor;
import service.businesslogic.api.LevelService;
import service.businesslogic.dto.LevelDto;

@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class LevelServiceImpl implements LevelService {

    private ModelMapper modelMapper;
    private LevelRepository levelRepository;

    @Override
    public List<LevelDto> findAll() {
        return levelRepository.findAll().stream()
                .map(l -> modelMapper.map(l, LevelDto.class))
                .collect(Collectors.toList());
    }
}
