package ua.rd.cm.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import ua.rd.cm.domain.Level;

@Repository
@PreAuthorize("isAuthenticated()")
@RepositoryRestResource(path = "levels")
public interface LevelRepository extends CrudRepository<Level, Long> {
    @Override
    List<Level> findAll();

    Level findByName(@Param("name")String name);
}
