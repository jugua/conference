package ua.rd.cm.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import ua.rd.cm.domain.Type;

@Repository
@PreAuthorize("isAuthenticated()")
@RepositoryRestResource(path = "types")
public interface TypeRepository extends CrudRepository<Type, Long> {

    Type findById(Long id);

    Type findByName(@Param("name")String name);

    List<Type> findAll();
}
