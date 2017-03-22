package ua.rd.cm.repository;

import ua.rd.cm.repository.specification.Specification;

import java.util.List;

public interface CrudRepository<T> {

    List<T> findAll();

    List<T> findBySpecification(Specification<T> specification);

    T findById(Long id);

    void save(T entity);

    void update(T entity);

    void remove(T entity);
}
