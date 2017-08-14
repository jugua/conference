package ua.rd.cm.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.rd.cm.domain.Language;

import java.util.List;

@Repository
public interface LanguageRepository extends CrudRepository<Language, Long> {
    @Override
    List<Language> findAll();

    Language findByName(String name);
}
