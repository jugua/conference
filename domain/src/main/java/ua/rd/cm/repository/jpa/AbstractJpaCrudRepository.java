package ua.rd.cm.repository.jpa;

import ua.rd.cm.repository.CrudRepository;
import ua.rd.cm.repository.specification.Specification;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class AbstractJpaCrudRepository<T> implements CrudRepository<T> {
    private static final String DEFAULT_PREFIX = "e";
    private static final String SELECT_QUERY = "SELECT %s FROM %s %s";

    protected final String prefix;
    protected final Class<T> type;
    protected final String selectJpql;

    @PersistenceContext
    protected EntityManager entityManager;

    public AbstractJpaCrudRepository(Class<T> type) {
        this(DEFAULT_PREFIX, type);
    }

    public AbstractJpaCrudRepository(String prefix, Class<T> type) {
        this.prefix = prefix;
        this.type = type;
        this.selectJpql = String.format(SELECT_QUERY, prefix, type.getSimpleName(), prefix);
    }

    @Override
    public List<T> findAll() {
        return entityManager.createQuery(selectJpql, type).getResultList();
    }

    @Override
    public List<T> findBySpecification(Specification<T> spec) {
        TypedQuery<T> query = entityManager.createQuery(
                selectJpql + " WHERE " + spec.toSqlClauses(),
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
    public void save(T entity) {
        entityManager.persist(entity);
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
