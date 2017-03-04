package ua.rd.cm.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.rd.cm.domain.Level;
import ua.rd.cm.repository.LevelRepository;
import ua.rd.cm.repository.specification.level.LevelById;
import ua.rd.cm.repository.specification.level.LevelByName;
import ua.rd.cm.services.LevelService;

import java.util.List;

/**
 * @author Olha_Melnyk
 */
@Service
public class LevelServiceImpl implements LevelService {

    private LevelRepository levelRepository;

    @Autowired
    public LevelServiceImpl(LevelRepository levelRepository) {
        this.levelRepository = levelRepository;
    }

    @Override
    public Level find(Long id) {
        return levelRepository.findBySpecification(new LevelById(id)).get(0);
    }

    @Override
    public void save(Level level) {
        levelRepository.save(level);
    }

    @Override
    public Level getByName(String name) {
        List<Level> list = levelRepository.findBySpecification(new LevelByName(name));
        if (list.isEmpty()) return null;
        else return list.get(0);
    }

    @Override
    public List<Level> findAll() {
        return levelRepository.findAll();
    }
}
