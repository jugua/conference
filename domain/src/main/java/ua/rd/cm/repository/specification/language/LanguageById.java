package ua.rd.cm.repository.specification.language;

import ua.rd.cm.domain.Language;
import ua.rd.cm.repository.specification.Specification;

/**
 * @author Olha_Melnyk
 */
public class LanguageById implements Specification<Language> {

    private Long id;

    public LanguageById(Long id) {
        this.id = id;
    }

    @Override
    public String toSqlClauses() {
        return String.format(" lan.id = '%s' ", id);
    }

    @Override
    public boolean test(Language language) {
        return language.getId().equals(id);
    }
}
