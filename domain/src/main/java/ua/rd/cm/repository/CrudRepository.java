package ua.rd.cm.repository;

import ua.rd.cm.repository.specification.Specification;

import java.util.List;

public interface CrudRepository<T> {

    @Deprecated
    List<T> findAll();

    List<T> findBySpecification(Specification<T> specification);

    T findById(Long id);

    T save(T entity);

    void update(T entity);

    void remove(T entity);
}
