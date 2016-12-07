package ua.rd.cm.repository;

import ua.rd.cm.domain.Type;
import ua.rd.cm.repository.specification.Specification;

import java.util.List;

/**
 * @author Mariia Lapovska
 */
public interface TypeRepository {

    void saveType(Type type);

    List<Type> findAll();

    List<Type> findBySpecification(Specification<Type> spec);
}
