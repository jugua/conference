package ua.rd.cm.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.rd.cm.domain.Type;

import java.util.List;

@Repository
public interface TypeRepository extends CrudRepository<Type, Long> {
    Type findById(Long id);
    Type findByName(String name);
    List<Type> findAll();
}
