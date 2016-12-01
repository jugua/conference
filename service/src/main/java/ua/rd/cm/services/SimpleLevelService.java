package ua.rd.cm.services;

import ua.rd.cm.domain.Level;
import ua.rd.cm.repository.LevelRepository;
import ua.rd.cm.repository.specification.level.LevelById;
import ua.rd.cm.repository.specification.level.LevelByName;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Olha_Melnyk
 */
public class SimpleLevelService implements LevelService {

    private LevelRepository levelRepository;

    @Inject
    public SimpleLevelService(LevelRepository levelRepository) {
        this.levelRepository = levelRepository;
    }

    @Override
    public Level find(Long id) {
        return levelRepository.findBySpecification(new LevelById(id)).get(0);
    }

    @Override
    public void save(Level level) {
        levelRepository.saveLevel(level);
    }

    @Override
    public List<Level> getByName(String name) {
        return levelRepository.findBySpecification(new LevelByName(name));
    }

    @Override
    public List<Level> findAll() {
        return levelRepository.findAll();
    }
}
