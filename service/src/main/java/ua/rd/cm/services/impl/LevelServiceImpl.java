package ua.rd.cm.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.rd.cm.domain.Level;
import ua.rd.cm.dto.LevelDto;
import ua.rd.cm.repository.LevelRepository;
import ua.rd.cm.repository.specification.level.LevelById;
import ua.rd.cm.repository.specification.level.LevelByName;
import ua.rd.cm.services.LevelService;
import ua.rd.cm.services.exception.LevelNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LevelServiceImpl implements LevelService {

    private final ModelMapper modelMapper;
    private LevelRepository levelRepository;

    @Autowired
    public LevelServiceImpl(ModelMapper modelMapper, LevelRepository levelRepository) {
        this.modelMapper = modelMapper;
        this.levelRepository = levelRepository;
    }

    @Override
    public Level find(Long id) {
        List<Level> levels = levelRepository.findBySpecification(new LevelById(id));
        if (levels.isEmpty()) {
            throw new LevelNotFoundException();
        }
        return levels.get(0);
    }

    @Override
    public void save(Level level) {
        levelRepository.save(level);
    }

    @Override
    public Level getByName(String name) {
        List<Level> levels = levelRepository.findBySpecification(new LevelByName(name));
        if (levels.isEmpty()) {
            throw new LevelNotFoundException();
        }
        return levels.get(0);
    }

    @Override
    public List<LevelDto> findAll() {
        return levelRepository.findAll().stream()
                .map(l -> modelMapper.map(l, LevelDto.class))
                .collect(Collectors.toList());
    }
}
