package ua.rd.cm.repository.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import ua.rd.cm.domain.Level;
import ua.rd.cm.repository.CrudRepository;
import ua.rd.cm.repository.LevelRepository;

public class LevelRepositoryImplIT extends AbstractJpaCrudRepositoryIT<Level> {
    @Autowired
    private LevelRepository repository;

    @Override
    protected CrudRepository<Level> createRepository() {
        return repository;
    }

    @Override
    protected Level createEntity() {
        Level level = new Level();
        level.setName("SeniÖr");
        return level;
    }
}