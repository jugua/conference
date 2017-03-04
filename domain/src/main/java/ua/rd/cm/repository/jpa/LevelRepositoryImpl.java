package ua.rd.cm.repository.jpa;

import org.springframework.stereotype.Repository;
import ua.rd.cm.domain.Level;
import ua.rd.cm.repository.LevelRepository;

@Repository
public class LevelRepositoryImpl
        extends AbstractJpaCrudRepository<Level> implements LevelRepository {

    public LevelRepositoryImpl() {
        super("lev", Level.class);
    }
}
