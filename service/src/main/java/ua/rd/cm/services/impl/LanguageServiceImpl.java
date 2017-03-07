package ua.rd.cm.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.rd.cm.domain.Language;
import ua.rd.cm.repository.LanguageRepository;
import ua.rd.cm.repository.specification.language.LanguageById;
import ua.rd.cm.repository.specification.language.LanguageByName;
import ua.rd.cm.services.LanguageService;
import ua.rd.cm.services.exception.LanguageNotFoundException;

import java.util.List;

@Service
public class LanguageServiceImpl implements LanguageService {

    private LanguageRepository languageRepository;

    @Autowired
    public LanguageServiceImpl(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    @Override
    public Language find(Long id) {
        List<Language> languages = languageRepository.findBySpecification(new LanguageById(id));
        if (languages.isEmpty()) {
            throw new LanguageNotFoundException();
        }
        return languages.get(0);
    }

    @Override
    @Transactional
    public void save(Language language) {
        languageRepository.save(language);
    }

    @Override
    public Language getByName(String name) {
        List<Language> languages = languageRepository.findBySpecification(new LanguageByName(name));
        if (languages.isEmpty()) {
            throw new LanguageNotFoundException();
        }
        return languages.get(0);
    }

    @Override
    public List<Language> findAll() {
        return languageRepository.findAll();
    }
}
