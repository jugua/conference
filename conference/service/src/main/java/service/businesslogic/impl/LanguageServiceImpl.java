package service.businesslogic.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.model.Language;
import domain.repository.LanguageRepository;
import lombok.AllArgsConstructor;
import service.businesslogic.api.LanguageService;

@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class LanguageServiceImpl implements LanguageService {

    private LanguageRepository languageRepository;

    @Override
    public List<Language> findAll() {
        return languageRepository.findAll();
    }
}
