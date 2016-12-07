package ua.rd.cm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.rd.cm.domain.Language;
import ua.rd.cm.repository.LanguageRepository;
import ua.rd.cm.repository.specification.language.LanguageById;
import ua.rd.cm.repository.specification.language.LanguageByName;

import java.util.List;

/**
 * @author Olha_Melnyk
 */
@Service
public class SimpleLanguageService implements LanguageService {

    private LanguageRepository languageRepository;

    @Autowired
    public SimpleLanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    @Override
    public Language find(Long id) {
        return languageRepository.findBySpecification(new LanguageById(id)).get(0);
    }

    @Override
    @Transactional
    public void save(Language language) {
        languageRepository.saveLanguage(language);
    }

    @Override
    public Language getByName(String name) {
        List<Language> list = languageRepository.findBySpecification(new LanguageByName(name));
        if (list.isEmpty()) return null;
        else return list.get(0);
    }

    @Override
    public List<Language> findAll() {
        return languageRepository.findAll();
    }
}
