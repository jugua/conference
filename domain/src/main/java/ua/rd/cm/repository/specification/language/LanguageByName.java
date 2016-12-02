package ua.rd.cm.repository.specification.language;

import ua.rd.cm.domain.Language;
import ua.rd.cm.repository.specification.Specification;

/**
 * @author Olha_Melnyk
 */
public class LanguageByName implements Specification<Language> {

    private String name;

    public LanguageByName(String name) {
        this.name = name;
    }

    @Override
    public String toSqlClauses() {
        return String.format(" lan.name = '%s' ", name);
    }

    @Override
    public boolean test(Language language) {
        return language.getName().equals(name);
    }
}
