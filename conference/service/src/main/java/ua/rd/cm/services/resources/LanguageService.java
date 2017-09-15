package ua.rd.cm.services.resources;

import java.util.List;

import ua.rd.cm.domain.Language;

/**
 * @author Olha_Melnyk
 */
public interface LanguageService {

    List<Language> findAll();

}
