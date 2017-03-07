package ua.rd.cm.repository.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import ua.rd.cm.domain.Language;
import ua.rd.cm.repository.CrudRepository;
import ua.rd.cm.repository.LanguageRepository;

public class LanguageRepositoryImplIT extends AbstractJpaCrudRepositoryIT<Language> {
    @Autowired
    private LanguageRepository repository;

    @Override
    protected CrudRepository<Language> createRepository() {
        return repository;
    }

    @Override
    protected Language createEntity() {
        Language language = new Language();
        language.setName("en");
        return language;
    }
}