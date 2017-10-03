package ua.rd.cm.services.resources.impl;

import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.rd.cm.domain.Language;
import ua.rd.cm.repository.LanguageRepository;
import ua.rd.cm.services.resources.LanguageService;

@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class LanguageServiceImpl implements LanguageService {

    private LanguageRepository languageRepository;

    @Override
    public List<Language> findAll() {
        return languageRepository.findAll();
    }
}
