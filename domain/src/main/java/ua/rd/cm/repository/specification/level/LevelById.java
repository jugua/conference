package ua.rd.cm.repository.specification.level;

import ua.rd.cm.domain.Level;
import ua.rd.cm.repository.specification.Specification;

/**
 * @author Olha_Melnyk
 */
public class LevelById implements Specification<Level> {

    private Long id;

    public LevelById(Long id) {
        this.id = id;
    }

    @Override
    public String toSqlClauses() {
        return String.format(" lev.id = '%s' ", id);
    }

    @Override
    public boolean test(Level level) {
        return level.getId().equals(id);
    }
}
