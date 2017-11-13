package domain.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import domain.model.Language;

@Repository
public interface LanguageRepository extends CrudRepository<Language, Long> {
    @Override
    List<Language> findAll();

    Language findByName(String name);
}
