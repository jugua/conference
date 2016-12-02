package ua.rd.cm.repository;

import ua.rd.cm.domain.Level;
import ua.rd.cm.repository.specification.Specification;

import java.util.List;

/**
 * @author Olha_Melnyk
 */
public interface LevelRepository {

    void saveLevel(Level level);

    List<Level> findAll();

    List<Level> findBySpecification(Specification<Level> spec);

}
