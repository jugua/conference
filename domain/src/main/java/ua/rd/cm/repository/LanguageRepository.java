package ua.rd.cm.repository;

import ua.rd.cm.domain.Language;
import ua.rd.cm.repository.specification.Specification;
import java.util.List;

/**
 * @author Olha_Melnyk
 */
public interface LanguageRepository {

    void saveLanguage(Language language);

    List<Language> findAll();

    List<Language> findBySpecification(Specification<Language> spec);

}
