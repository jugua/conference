package ua.rd.cm.repository;

import ua.rd.cm.repository.specification.Specification;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class AbstractCrudRepository<T> implements CrudRepository<T> {
    private final Class<T> type;

    @PersistenceContext
    protected EntityManager entityManager;

    public AbstractCrudRepository(Class<T> type) {
        this.type = type;
    }

    @Override
    public List<T> findAll() {
        return entityManager.createQuery(
                String.format("SELECT e FROM %s e", type.getSimpleName()), type
        ).getResultList();
    }

    @Override
    public List<T> findBySpecification(Specification<T> spec) {
        TypedQuery<T> query = entityManager.createQuery(
                String.format("SELECT e FROM %s e WHERE %s", type.getSimpleName(), spec.toSqlClauses()),
                type
        );
        spec.setParameters(query);
        return query.getResultList();
    }

    @Override
    public T findById(Long id) {
        return entityManager.find(type, id);
    }

    @Override
    public T save(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public void update(T entity) {
        entityManager.merge(entity);
    }

    @Override
    public void remove(T entity) {
        entityManager.remove(entity);
    }
}
