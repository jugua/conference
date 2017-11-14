package domain.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import domain.model.Type;

@Repository
public interface TypeRepository extends CrudRepository<Type, Long> {
    Type findById(Long id);

    Type findByName(String name);

    List<Type> findAll();
}
