package ua.rd.cm.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import ua.rd.cm.domain.Language;

@Repository
@PreAuthorize("isAuthenticated()")
@RepositoryRestResource(path = "languages")
public interface LanguageRepository extends CrudRepository<Language, Long> {
    @Override
    List<Language> findAll();

    Language findByName(@Param("name") String name);
}
