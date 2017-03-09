package ua.rd.cm.repository.jpa;

import org.springframework.stereotype.Repository;
import ua.rd.cm.domain.Language;
import ua.rd.cm.repository.LanguageRepository;

@Repository
public class LanguageRepositoryImpl
        extends AbstractJpaCrudRepository<Language> implements LanguageRepository {

    public LanguageRepositoryImpl() {
        super("lan", Language.class);
    }
}