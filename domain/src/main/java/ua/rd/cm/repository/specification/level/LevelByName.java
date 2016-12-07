package ua.rd.cm.repository.specification.level;

import ua.rd.cm.domain.Level;
import ua.rd.cm.repository.specification.Specification;

/**
 * @author Olha_Melnyk
 */
public class LevelByName implements Specification<Level> {
    private String name;

    public LevelByName(String name) {
        this.name = name;
    }

    @Override
    public String toSqlClauses() {
        return String.format(" lev.name = '%s'", name);
    }

    @Override
    public boolean test(Level level) {
        return level.getName().equals(name);
    }
}
