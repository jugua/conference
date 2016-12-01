package ua.rd.cm.services;

import ua.rd.cm.domain.Language;
import java.util.List;

/**
 * @author Olha_Melnyk
 */
public interface LanguageService {

    Language find(Long id);

    void save(Language language);

    List<Language> getByName(String name);

    List<Language> findAll();

}
